package com.android.doctor.ui.plan;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DialogHelper;
import com.android.doctor.helper.JsonUtil;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.AdjustPlanParam;
import com.android.doctor.model.DataText;
import com.android.doctor.model.DiagList;
import com.android.doctor.model.NewPlanRecord;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.TreatPlanList;
import com.android.doctor.model.UpdatePlanDetaParam;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.PlanDetaAdapter;
import com.android.doctor.ui.base.BaseActivity;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/31.
 */
public class PlanSchemeActivity extends BaseActivity implements OnListItemClickListener{
    public static final int REQUEST_DOCTOR_ADVICE_CODE = 1;
    public static final int REQUEST_DOCTOR_REVIEW_CODE = 2;
    public static final int REQUEST_DOCTOR_CHECK_CODE = 3;
    public static final int REQUEST_DOCTOR_CHECKOUT_CODE = 4;
    public static final int REQUEST_DOCTOR_MEDICINE_CODE = 7;
    public static final int REQUEST_DOCTOR_REMIND_CODE = 9;
    public static final int REQUEST_DOCTOR_FOLLOW_CODE = 10;

    @InjectView(R.id.listView)
    protected ListView mListView;

    private PlanDetaAdapter mAdapter;
    private PlanList.DataEntity mPlItem;
    private PlanDeta mPlanDeta;
    private DiagList mDiagList;
    private TreatPlanList mTreatPlanList;

    public static void startAty(Context context, PlanList.DataEntity pItem) {
        Intent intent = new Intent(context, PlanSchemeActivity.class);
        intent.putExtra("data", pItem);
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
    }

    @Override
    protected void initData() {
        super.initData();
        onLoadPlanDeta();
        //onLoadDiagList();
        //onLoadTreatPlan();
    }

    private void setPlanViewData(List data) {
        Log.d("PlanSchemeActivity", "setPlanViewData");
        if (data == null) return;
        data.add(0, mPlItem);
        mAdapter.setmData(data);
    }

    @OnClick(R.id.img_complete)
    protected void onComplete() {
        if (isFastDoubleClick()) return;
        AdjustPlanParam planParam = new AdjustPlanParam();
        planParam.setPid(mPlItem.getPid());
        planParam.setRef_tplid(mPlItem.getTplid());

        List<PlanDeta.DataEntity> list = new ArrayList<>();
        List<PlanDeta.DataEntity> data = mAdapter.getmData();
        for (int i = 1; i < data.size(); ++i) {
            list.add(data.get(i));
        }
        planParam.setTpldetails(list);
        Log.d("onComplete: " , "pid, Ref_tplid " + planParam.getPid() + ", " + planParam.getRef_tplid());
        onUpdatePlanDeta(planParam);

        //JsonObject jo = new JsonObject();
        //JsonParser parser = new JsonParser();
        //JsonObject obj = parser.parse(planDeta).getAsJsonObject();

        /*UpdatePlanDetaParam param = new UpdatePlanDetaParam();
        param.setUid(mPlItem.getDuid());
        param.setUsername(mPlItem.getDname());

        User u = AppContext.context().getUser();
        User.UserEntity userEntity = u.getUser();
        param.setHisid(userEntity.getHosid());
        param.setHisname(userEntity.getHosname());
        param.setName(mPlItem.getPlanname());
        Gson g = new Gson();
        if (mDiagList != null)
        param.setTreat(g.toJson(mDiagList));
        if (mTreatPlanList != null)
        param.setDiag(g.toJson(mTreatPlanList));
        param.setPlan(mPlanDeta);*/
        //JsonObject jso = (JsonObject)new JsonParser().parse(g.toJson(planParam));
    }

    private void setBaseViewData() {
        /*mTvTitle.setText(mPlItem.getPlanname());
        mTvPatient.setText(mPlItem.getPname());
        mTvDoctor.setText(mPlItem.getDname());
        mTvDate.setText(DateUtils.getDateString2(mPlItem.getCreateTime()));
        int stat = mPlItem.getStatus();
        mTvState.setText(stat == 1 ? "执行中" : stat == 2 ? "已完成" : "未创建");*/
    }


    @OnClick(R.id.img_more)
    protected void onPlanSchemeOper() {
         DialogHelper.showBottomListDialog(this,
                Arrays.asList(getResources().getStringArray(R.array.plan_scheme_operation)),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        switch (position) {
                            case 0 ://终止当前计划
                                break;
                            case 1://添加随访记录
                                NewPlanRecord record =new NewPlanRecord();
                                record.setNewPlanRecord(mPlItem);
                                PlanRecordInfoActivity.startAty(PlanSchemeActivity.this, record);
                                break;
                            case 2://查看随访记录
                                PlanRecordListActivity.startAty(PlanSchemeActivity.this, "" + mPlItem.getPid());
                                break;
                            case 3://查看提醒事件
                                break;
                            default:break;
                        }
                    }
                }, null);
    }


    private void onStartEditAtyForResult(int code, PlanDeta.DataEntity data) {
        Intent intent = new Intent();
        intent.setClass(this, EditPlanItemActivity.class);
        intent.putExtra("requestCode", code);
        intent.putExtra("data", data);
        startActivityForResult(intent, code);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            PlanDeta.DataEntity d = data.getParcelableExtra("data");
            Log.d("onActivityResult", new Gson().toJson(d));
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
        PlanDeta.DataEntity entity = (PlanDeta.DataEntity) mAdapter.getItem(position);
        if (entity != null) {
            onStartEditAtyForResult(entity.getCode(), entity);
        }
    }

    private void onLoadPlanDeta() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanDeta>> call = service.getPlanDeta("" + mPlItem.getPid());
        call.enqueue(new Callback<RespEntity<PlanDeta>>() {
            @Override
            public void onResponse(Call<RespEntity<PlanDeta>> call, Response<RespEntity<PlanDeta>> response) {
                RespEntity<PlanDeta> data = response.body();
                if (response.isSuccessful()) {
                    if (data == null) {
                        return;
                    } else if (data.getResponse_params() != null) {
                        PlanDeta planDeta = data.getResponse_params();
                        setPlanViewData(planDeta.getData());
                    }
                } else {
                    String errMsg = "";
                    if (data != null) {
                        errMsg = data.getError_msg();
                    }
                }
            }

            @Override
            public void onFailure(Call<RespEntity<PlanDeta>> call, Throwable t) {
                Log.d("PlanSchemeActivity",t.toString());
            }
        });
    }


    private void onLoadDiagList() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<DiagList>> call = service.getDiagList();
        call.enqueue(new Callback<RespEntity<DiagList>>() {
            @Override
            public void onResponse(Call<RespEntity<DiagList>> call, Response<RespEntity<DiagList>> response) {
                RespEntity<DiagList> data = response.body();
                if (response.isSuccessful()) {
                    if (data == null) {
                        return;
                    } else if (data.getResponse_params() != null) {
                        mDiagList = data.getResponse_params();
                    }
                } else {
                    String errMsg = "";
                    if (data != null) {
                        errMsg = data.getError_msg();
                    }
                }
            }

            @Override
            public void onFailure(Call<RespEntity<DiagList>> call, Throwable t) {
                Log.d("PlanSchemeActivity",t.toString());
            }
        });
    }


    private void onLoadTreatPlan() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<TreatPlanList>> call = service.getTreatPlanList();
        call.enqueue(new Callback<RespEntity<TreatPlanList>>() {
            @Override
            public void onResponse(Call<RespEntity<TreatPlanList>> call, Response<RespEntity<TreatPlanList>> response) {
                RespEntity<TreatPlanList> data = response.body();
                if (response.isSuccessful()) {
                    if (data == null) {
                        return;
                    } else if (data.getResponse_params() != null) {
                        mTreatPlanList = data.getResponse_params();
                    }
                } else {
                    String errMsg = "";
                    if (data != null) {
                        errMsg = data.getError_msg();
                    }
                }
            }

            @Override
            public void onFailure(Call<RespEntity<TreatPlanList>> call, Throwable t) {
                Log.d("PlanSchemeActivity",t.toString());
            }
        });
    }

    private void onUpdatePlanDeta(final AdjustPlanParam param) {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanDeta>> call = service.adjustPlan(JsonUtil.toJson(new Gson().toJson(param)));
        call.enqueue(new Callback<RespEntity<PlanDeta>>() {
            @Override
            public void onResponse(Call<RespEntity<PlanDeta>> call, Response<RespEntity<PlanDeta>> response) {
                RespEntity<PlanDeta> data = response.body();
                onResult(data);
                if (response.isSuccessful()) {
                    if (data == null) {
                        return;
                    } else if (data.getResponse_params() != null) {
                        PlanDeta planDeta = data.getResponse_params();
                        setPlanViewData(planDeta.getData());
                        //mTreatPlanList = data.getResponse_params();
                    }
                }
            }

            @Override
            public void onFailure(Call<RespEntity<PlanDeta>> call, Throwable t) {
                Log.d("PlanSchemeActivity.", t.toString());
            }
        });
    }

    private void onResult(RespEntity resp) {
        dismissProcessDialog();
        if (resp != null) {
            String text = resp.getError_msg();
            UIHelper.showToast(this, text);
            if (resp.getError_code() == 0) {
                onBackPressed();
            }
        }
    }
}
