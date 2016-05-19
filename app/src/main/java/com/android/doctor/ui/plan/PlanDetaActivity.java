package com.android.doctor.ui.plan;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppManager;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.AdjustPlanParam;
import com.android.doctor.model.AsMyPlParam;
import com.android.doctor.model.AsPlDraftParam;
import com.android.doctor.model.Constants;
import com.android.doctor.model.DataText;
import com.android.doctor.model.NewPlanRecord;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.PubPlanParam;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.TPlanDeta;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.PlanDetaAdapter;
import com.android.doctor.ui.base.BaseActivity;
import com.google.gson.Gson;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Yong on 2016/3/31.
 */
public class PlanDetaActivity extends BaseActivity implements OnListItemClickListener{
    public static final int REQUEST_DOCTOR_ADVICE_CODE = 1;
    public static final int REQUEST_DOCTOR_REVIEW_CODE = 2;
    public static final int REQUEST_DOCTOR_TEST_CODE = 3;
    public static final int REQUEST_DOCTOR_CHECKOUT_CODE = 4;
    public static final int REQUEST_DOCTOR_MEDICINE_CODE = 7;
    public static final int REQUEST_DOCTOR_REMIND_CODE = 9;
    public static final int REQUEST_DOCTOR_FOLLOW_CODE = 10;

    @InjectView(R.id.listView)
    protected ListView mListView;
    @InjectView(R.id.tv_complete)
    protected TextView mTvComplete;
    @InjectView(R.id.img_more)
    protected ImageButton mImgMore;

    private int mPlanStatus = 20;
    private PlanDetaAdapter mAdapter;
    private PlanList.PlanBaseEntity mPlItem;
    private String mPid;

    public static void startAty(Context context,String pid, int status, PlanList.PlanBaseEntity pItem) {
        Intent intent = new Intent(context, PlanDetaActivity.class);
        intent.putExtra("data", pItem);
        intent.putExtra("pid", pid);
        intent.putExtra("stat", status);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_plan_deta);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        if (intent != null) {
            mPlItem = intent.getParcelableExtra("data");
            mPid = intent.getStringExtra("pid");
            mPlanStatus = intent.getIntExtra("stat", 0);
            if (mPlanStatus == -200)
                mPlanStatus = Constants.PLAN_STATUS_NOT_CREATE;
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(R.string.visit_plan);
        mAdapter = new PlanDetaAdapter(this);
        mAdapter.setItemClickListener(this);
        mListView.setAdapter(mAdapter);
        //setBaseViewData();
        initMenuOption();
    }

    private void initMenuOption() {
        if (mPlanStatus == Constants.PLAN_STATUS_FINISHED){
            mTvComplete.setVisibility(View.GONE);
            mImgMore.setImageResource(R.drawable.ic_list);
        } else if (mPlanStatus == Constants.PLAN_STATUS_INIT) {//发布
            mTvComplete.setText(R.string.public_topic);
        } else if (mPlanStatus == Constants.PLAN_STATUS_IN_EXEC) {//调整
            mTvComplete.setText(R.string.adjust);
        } else if (mPlanStatus == Constants.PLAN_STATUS_NOT_CREATE) {//发布
            mTvComplete.setText(R.string.public_topic);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (mPlanStatus == Constants.PLAN_STATUS_INIT) {//模板
            onLoadTPlanDeta();
            return;
        }
        onLoadPlanDeta();
    }

    private int getPlanStatus(int stat) {
        switch (stat) {
            case -200:
                return Constants.PLAN_STATUS_NOT_CREATE;
            case 1:
                return Constants.PLAN_STATUS_IN_EXEC;
            case 2:
                return Constants.PLAN_STATUS_FINISHED;
        }
        return 0;
    }

    private void setPlanViewData(List data) {
        if (data == null) return;
        if (mPlItem != null) {
            data.add(0, mPlItem);
        }
        mAdapter.setmData(data);
    }


    @OnClick(R.id.tv_complete)
    protected void onComplete() {
        if (isFastDoubleClick()) return;

        if (mPlanStatus == Constants.PLAN_STATUS_IN_EXEC) {
            onAdjustPlan();
        } else if (mPlanStatus == Constants.PLAN_STATUS_INIT ||
                mPlanStatus == Constants.PLAN_STATUS_NOT_CREATE) {
            onPubPlan();
        }
    }


    @OnClick(R.id.img_more)
    protected void onPlanSchemeOper() {
        if (mPlanStatus == Constants.PLAN_STATUS_INIT) {//发布
            showInitPlanMenu();
        } else if (mPlanStatus == Constants.PLAN_STATUS_NOT_CREATE) {//发布
            showUnCreatedPlanMenu();
        } else if (mPlanStatus == Constants.PLAN_STATUS_IN_EXEC) {//调整
            showInExecPlanMenu();
        } else if (mPlanStatus == Constants.PLAN_STATUS_FINISHED){
            PlanRecordListActivity.startAty(PlanDetaActivity.this, mPid);
        }
    }


    private void showInExecPlanMenu() {
        DialogUtils.showBottomListDialog(this, Arrays.asList(getResources().getStringArray(R.array.plan_ing_status_menu)),
                new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                dialog.dismiss();
                switch (position) {
                    case 0 ://终止当前计划
                        onStopPlan();
                        break;
                    case 1://添加随访记录
                        NewPlanRecord record =new NewPlanRecord();
                        record.setNewPlanRecord(mPlItem);
                        PlanRecordInfoActivity.startAty(PlanDetaActivity.this, record);
                        break;
                    case 2://查看随访记录
                        PlanRecordListActivity.startAty(PlanDetaActivity.this, mPid);
                        break;
                    case 3://查看提醒事件
                        PlanRemindActivity.startAty(PlanDetaActivity.this);
                        break;
                    default:break;
                }
            }
        }, null);
    }

    private void showInitPlanMenu() {
        DialogUtils.showBottomListDialog(this, Arrays.asList(getResources().getStringArray(R.array.plan_init_status_menu)),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        switch (position) {
                            case 0 : //保存为草稿
                                onSavePlAsDraft();
                                break;
                            case 1: //保存为我的方案
                                showInputNewNameDialog();
                                break;
                            default:break;
                        }
                    }
                }, null);
    }

    private void showUnCreatedPlanMenu() {
        DialogUtils.showBottomListDialog(this, Arrays.asList(getResources().getStringArray(R.array.plan_uncreated_status_menu)),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        switch (position) {
                            case 0 ://保存
                                onSavePlAsDraft();
                                break;
                            case 1: //删除
                                onDelPlDraft();
                                break;
                            case 2://存为我的方案
                                showInputNewNameDialog();
                                break;
                            default:break;
                        }
                    }
                }, null);
    }

    private void onStartEditAtyForResult(int code, PlanDeta.PlanDetaEntity data) {
        Intent intent = new Intent();
        intent.setClass(this, EditPlanItemActivity.class);
        intent.putExtra("requestCode", code);
        intent.putExtra("data", data);
        startActivityForResult(intent, code);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            PlanDeta.PlanDetaEntity d = data.getParcelableExtra("data");
            Log.d(AppConfig.TAG, "PlanDetaActivity-> onActivityResult" + new Gson().toJson(d));
            mAdapter.updateData(d);
        }
    }

    private boolean compareList(List<DataText> newList, List<DataText> oldList) {
        if (newList.size() != oldList.size()) return false;
        for (int i = 0; i < oldList.size(); ++i) {
            DataText o1 = newList.get(i);
            DataText o2 = oldList.get(i);
            if (!o1.equals(o2)) return false;
        }
        return true;
    }

    @Override
    public void onItemClick(int position, View view) {
        PlanDeta.PlanDetaEntity entity = (PlanDeta.PlanDetaEntity) mAdapter.getItem(position);
        if (entity != null) {
            onStartEditAtyForResult(entity.getCode(), entity);
        }
    }

    private void onLoadPlanDeta() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanDeta>> call = service.getPlanDeta(mPid);
        call.enqueue(new RespHandler<PlanDeta>() {
            @Override
            public void onSucceed(RespEntity<PlanDeta> resp) {
                if (resp.getResponse_params() != null) {
                    setPlanViewData(resp.getResponse_params().getData());
                }
            }

            @Override
            public void onFailed(RespEntity<PlanDeta> resp) {
                onLoadResult(resp);
            }
        });
    }

    private void onLoadTPlanDeta() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<TPlanDeta>> call = service.getTPlanDeta(mPid);
        call.enqueue(new RespHandler<TPlanDeta>() {
            @Override
            public void onSucceed(RespEntity<TPlanDeta> resp) {
                if (resp.getResponse_params() != null) {
                    setPlanViewData(resp.getResponse_params().getTpls());
                }
            }

            @Override
            public void onFailed(RespEntity<TPlanDeta> resp) {
                onLoadResult(resp);
            }
        });
    }

    public PubPlanParam getPubPlanParam() {
        int i = 0;
        PubPlanParam planParam = new PubPlanParam();
        if (mPlItem != null) {
            planParam.setPid(mPlItem.getPid());
            planParam.setRef_tplid(mPlItem.getRef_tplid());
            i = 1;
        } else {
            planParam.setRef_tplid(mPid);
        }//模板基础上创建: 取tplid;    计划基础上:去ref_tplid

        List<PlanDeta.PlanDetaEntity> list = new ArrayList<>();
        List<PlanDeta.PlanDetaEntity> data = mAdapter.getmData();
        if (data == null) return null;
        for (; i < data.size(); ++i) {
            list.add(data.get(i));
        }
        planParam.setTpldetails(list);

        return planParam;
    }

    private AdjustPlanParam getAdjustPlanParam() {
        AdjustPlanParam planParam = new AdjustPlanParam();
        if (mPlItem == null) return null;
        planParam.setPid(mPlItem.getPid());
        planParam.setRef_tplid(mPlItem.getRef_tplid());

        List<PlanDeta.PlanDetaEntity> list = new ArrayList<>();
        List<PlanDeta.PlanDetaEntity> data = mAdapter.getmData();
        if (data == null) return null;
        for (int i = 1; i < data.size(); ++i) {
            list.add(data.get(i));
        }
        planParam.setTpldetails(list);
        //FileUtils.writeToSDFile(new Gson().toJson(planParam), "adjustPlJson.txt");
        return planParam;
    }

    public AsMyPlParam getSaveAsMyPlParam(String newName) {
        int i = 0;
        AsMyPlParam planParam = new AsMyPlParam();
        if (mPlItem != null) {
            planParam.setPid(mPlItem.getPid());
            planParam.setRef_tplid(mPlItem.getRef_tplid());
            i = 1;
        } else {
            planParam.setRef_tplid(mPid);
        }//模板基础上创建: 取tplid;    计划基础上:去ref_tplid
        planParam.setNewtplname(newName);
        List<PlanDeta.PlanDetaEntity> list = new ArrayList<>();
        List<PlanDeta.PlanDetaEntity> data = mAdapter.getmData();
        if (data == null) return null;
        for (; i < data.size(); ++i) {
            list.add(data.get(i));
        }
        planParam.setTpldetails(list);
        return planParam;
    }

    public AsPlDraftParam getSavePlAsDraftParam() {
        int i = 0;
        AsPlDraftParam planParam = new AsPlDraftParam();
        if (mPlItem != null) {
            planParam.setPid(mPlItem.getPid());
            planParam.setRef_tplid(mPlItem.getRef_tplid());
            i = 1;
        } else {
            planParam.setRef_tplid(mPid);
        }//模板基础上创建: 取tplid;    计划基础上:去ref_tplid

        List<PlanDeta.PlanDetaEntity> list = new ArrayList<>();
        List<PlanDeta.PlanDetaEntity> data = mAdapter.getmData();
        if (data == null) return null;
        for (; i < data.size(); ++i) {
            list.add(data.get(i));
        }
        planParam.setTpldetails(list);
        return planParam;
    }

    private void onAdjustPlan() {
        AdjustPlanParam param = getAdjustPlanParam();
        if (param == null) return;
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanDeta>> call = service.adjustPlan(param);
        call.enqueue(new RespHandler<PlanDeta>() {
            @Override
            public void onSucceed(RespEntity<PlanDeta> resp) {
                onProResult(resp);
            }

            @Override
            public void onFailed(RespEntity<PlanDeta> resp) {
                onProResult(resp);
            }
        });
    }

    private void onPubPlan() {
        PubPlanParam param = getPubPlanParam();
        if (param == null) return;
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanDeta>> call = service.pubPlan(param);
        call.enqueue(new RespHandler<PlanDeta>() {
            @Override
            public void onSucceed(RespEntity<PlanDeta> resp) {
                onProResult(resp);
            }

            @Override
            public void onFailed(RespEntity<PlanDeta> resp) {
                onProResult(resp);
            }
        });
    }

    private void onStopPlan() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<Object>> call = service.terminatePlan(mPid);
        call.enqueue(new RespHandler<Object>() {
            @Override
            public void onSucceed(RespEntity<Object> resp) {
                onProResult(resp);
            }

            @Override
            public void onFailed(RespEntity<Object> resp) {
                onProResult(resp);
            }
        });
    }

    private void onSavePlAsDraft() {
        AsPlDraftParam param = getSavePlAsDraftParam();
        if (param == null) return;
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<Object>> call = service.savePlAsDraft(param);
        call.enqueue(new RespHandler<Object>() {
            @Override
            public void onSucceed(RespEntity<Object> resp) {
                onProResult(resp);
            }

            @Override
            public void onFailed(RespEntity<Object> resp) {
                onProResult(resp);
            }
        });
    }

    private void showInputNewNameDialog() {
        final View v = View.inflate(this, R.layout.edit_input, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("填写支持数量");
        builder.setView(v);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DeviceHelper.hideSoftKeyboard(v);
                String tx = ((EditText)v.findViewById(R.id.edt_content)).getText().toString();
                onSavePlAsMyPl(tx);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.setCancelable(false);
        builder.create().show();
    }

    private void onSavePlAsMyPl(String newPlName) {
        AsMyPlParam param = getSaveAsMyPlParam(newPlName);
        if (param == null) return;
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<Object>> call = service.savePlAsMyPl(param);
        call.enqueue(new RespHandler<Object>() {
            @Override
            public void onSucceed(RespEntity<Object> resp) {
                resp.setError_code(-1);
                onProResult(resp);
            }

            @Override
            public void onFailed(RespEntity<Object> resp) {
                onProResult(resp);
            }
        });
    }

    private void onDelPlDraft() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<Object>> call = service.delPlDraft(mPid);
        call.enqueue(new RespHandler<Object>() {
            @Override
            public void onSucceed(RespEntity<Object> resp) {
                onProResult(resp);
            }

            @Override
            public void onFailed(RespEntity<Object> resp) {
                onProResult(resp);
            }
        });
    }

}
