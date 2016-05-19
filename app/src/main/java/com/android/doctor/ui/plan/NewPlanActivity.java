package com.android.doctor.ui.plan;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.app.AppManager;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.CrtPlanParam;
import com.android.doctor.model.DiagList;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.TPlanList;
import com.android.doctor.model.TempPlanList;
import com.android.doctor.model.TreatPlanList;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.PlanDetaAdapter;
import com.android.doctor.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Yong on 2016/3/31.
 */
public class NewPlanActivity extends BaseActivity implements OnListItemClickListener{
    public static final int REQUEST_DOCTOR_ADVICE_CODE = 1;
    public static final int REQUEST_DOCTOR_REVIEW_CODE = 2;
    public static final int REQUEST_DOCTOR_CHECK_CODE = 3;
    public static final int REQUEST_DOCTOR_CHECKOUT_CODE = 4;
    public static final int REQUEST_DOCTOR_MEDICINE_CODE = 7;
    public static final int REQUEST_DOCTOR_REMIND_CODE = 9;
    public static final int REQUEST_DOCTOR_FOLLOW_CODE = 10;

    @InjectView(R.id.listView)
    protected ListView mListView;
    @InjectView(R.id.tv_complete)
    protected TextView mTvComplete;
    @InjectView(R.id.edt_plan_name)
    protected EditText mEdtPlanName;
    @InjectView(R.id.edt_dis_diag)
    protected EditText mEdtDisDiag;
    @InjectView(R.id.edt_treatment)
    protected EditText mEdtTreat;

    private PlanDetaAdapter mAdapter;

    private TempPlanList.TplsEntity tpls;

    public static void startAty(Context context, TempPlanList.TplsEntity tpls) {
        Intent intent = new Intent(context, NewPlanActivity.class);
        intent.putExtra("entity", tpls);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_new_plan);
    }

    @Override
    protected void init() {
        Intent intent = getIntent();
        if (intent != null) {
            tpls = intent.getParcelableExtra("entity");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(R.string.create_new_plan);
        mAdapter = new PlanDetaAdapter(this);
        mAdapter.setItemClickListener(this);
        mListView.setAdapter(mAdapter);
    }


    @Override
    protected void initData() {
        super.initData();
        onLoadDefaultTPlDeta();
        setBaseViewData();
    }

    private void setBaseViewData() {
        if (tpls == null) return;
        mEdtPlanName.setText(tpls.getName());
        mEdtDisDiag.setText(tpls.getDiag());
        mEdtTreat.setText(tpls.getTreat());
    }

    private void setPlanViewData(List data) {
        if (data == null) return;
        mAdapter.setmData(data);
    }

    @OnClick(R.id.tv_diag)
    protected void onSetDiag() {
        SelectTxItemActivity.startAty(this,FragmentTxItemList.REQUEST_LOAD_TYPE_MAIN_DIAG);
    }

    @OnClick(R.id.tv_treat)
    protected void onSetTreat() {
        SelectTxItemActivity.startAty(this,FragmentTxItemList.REQUEST_LOAD_TYPE_TREAT);
    }

    @OnClick(R.id.tv_complete)
    protected void onComplete() {
        if (isFastDoubleClick()) return;
        onCrtNewPlan();
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
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case FragmentTxItemList.REQUEST_LOAD_TYPE_MAIN_DIAG:
                DiagList.DiagEntity mdiag = data.getParcelableExtra("data");
                mEdtDisDiag.setText(mdiag.getName());
                break;
            case FragmentTxItemList.REQUEST_LOAD_TYPE_TREAT:
                TreatPlanList.TreatPlanEntity treat = data.getParcelableExtra("data");
                mEdtTreat.setText(treat.getName());
                break;
            default:
                PlanDeta.PlanDetaEntity d = data.getParcelableExtra("data");
                mAdapter.updateData(d);
                break;
        }
    }


    @Override
    public void onItemClick(int position, View view) {
        PlanDeta.PlanDetaEntity entity = (PlanDeta.PlanDetaEntity) mAdapter.getItem(position);
        if (entity != null) {
            onStartEditAtyForResult(entity.getCode(), entity);
        }
    }


    private void onLoadDefaultTPlDeta() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<TPlanList>> call = service.getDefaultTPl();
        call.enqueue(new RespHandler<TPlanList>() {
            @Override
            public void onSucceed(RespEntity<TPlanList> resp) {
                if (resp.getResponse_params() != null) {
                    setPlanViewData(resp.getResponse_params().getTpls());
                }
            }

            @Override
            public void onFailed(RespEntity<TPlanList> resp) {

            }
        });
    }

    public CrtPlanParam getCrtPlanParam() {
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity == null) return null;
        String plName = mEdtPlanName.getText().toString();
        String diag = mEdtDisDiag.getText().toString();
        String treat = mEdtTreat.getText().toString();
        if (TextUtils.isEmpty(plName)) {
            UIHelper.showToast("请输入方案名称");
            return null;
        }
        if (TextUtils.isEmpty(diag)) {
            UIHelper.showToast("请输入疾病诊断");
            return null;
        }
        if (TextUtils.isEmpty(treat)) {
            UIHelper.showToast("请输入治疗方案");
            return null;
        }
        CrtPlanParam planParam = new CrtPlanParam();
        planParam.setUsername(userEntity.getUsername());
        planParam.setUid(userEntity.getDuid());
        planParam.setHosid(userEntity.getHosid());
        planParam.setHosname(userEntity.getHosname());
        planParam.setName(plName);
        planParam.setDiag(diag);
        planParam.setTreat(treat);
        List<PlanDeta.PlanDetaEntity> list = new ArrayList<>();
        List<PlanDeta.PlanDetaEntity> data = mAdapter.getmData();
        for (int i = 0; i < data.size(); ++i) {
            list.add(data.get(i));
        }
        planParam.setPlan(list);
        return planParam;
    }



    private void onCrtNewPlan() {
        showProcessDialog();
        CrtPlanParam param = getCrtPlanParam();
        if (param == null) {
            dismissProcessDialog();
            return;
        }
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<Object>> call = service.crtNewPlan(param);
        call.enqueue(new RespHandler<Object>() {
            @Override
            public void onSucceed(RespEntity<Object> resp) {
                onResult(resp);
            }

            @Override
            public void onFailed(RespEntity<Object> resp) {
                dismissProcessDialog();
            }
        });
    }


    private void onResult(RespEntity resp) {
        dismissProcessDialog();
        if (resp != null) {
            String text = resp.getError_msg();
            UIHelper.showToast(text);
            if (resp.getError_code() == 0) {
                AppManager.getAppManager().finishActivity();
            }
        }
    }

}
