package com.android.doctor.ui.plan;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.DiagList;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.RunPlanDeta;
import com.android.doctor.model.TreatPlanList;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.patient.VisitPatientListActivity;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Yong on 2016/3/30.
 */
public class AddPlanActivity extends BaseActivity  {

    @InjectView(R.id.tv_patient)
    protected TextView mTvPatient;
    @InjectView(R.id.tv_start_date)
    protected TextView mTvStartDate;

    @InjectView(R.id.edt_main_diagnose)
    protected EditText mEdtMainDiag;
    @InjectView(R.id.edt_other_diagnose)
    protected EditText mEdtOtherDiag;
    @InjectView(R.id.edt_treatment)
    protected EditText mEdtTreat;
    @InjectView(R.id.btn_select)
    protected AppCompatButton mBtn;
    private SlideDateTimePicker mDateTimePicker;

    private PlanList.PlanBaseEntity mPlanBase;

    public static void startAty(Context context, PlanList.PlanBaseEntity pItem) {
        Intent intent = new Intent(context, PlanDetaActivity.class);
        intent.putExtra("data", pItem);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_add_plan);
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(R.string.visit_plan);
        mDateTimePicker = new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        mTvStartDate.setText(DateUtils.formatDate(date));
                    }
                }).setInitialDate(new Date()).build();
    }

    @Override
    protected void initData() {
        super.initData();
        mPlanBase = getIntent().getParcelableExtra("data");
        setViewData(mPlanBase, mPlanBase == null ? 0 : 1);
    }

    private void setViewData(PlanList.PlanBaseEntity planBase, int plancnt) {
        if (planBase != null ) {
            //mTvPatient.setText(planBase.getPname());
            mTvStartDate.setText(planBase.getTime());
            mEdtMainDiag.setText(planBase.getMaindiagnose());
            mEdtOtherDiag.setText(planBase.getOtherdiagnose());
            mEdtTreat.setText(planBase.getTreatmentmodality());
            int stat = planBase.getStatus();
        }
        if (plancnt == 0) {
            mPlanBase = null;
            mBtn.setText(R.string.select_plan);
        } else {
            mBtn.setText(R.string.view_plan);
        }
    }


    private void onLoadRunPlan(String puuid) {
        showProcessDialog();
        User.UserEntity userEntity = AppContext.context().getUser();
        Map<String,String> map = new HashMap<>();
        map.put("puuid", puuid);
        map.put("duid", userEntity.getDuid());
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<RunPlanDeta>> call = service.getRunPlanDeta(map);
        call.enqueue(new RespHandler<RunPlanDeta>() {
            @Override
            public void onSucceed(RespEntity<RunPlanDeta> resp) {
                dismissProcessDialog();
                if (resp.getResponse_params() != null) {
                    mPlanBase = resp.getResponse_params().getPlan();
                    setViewData(mPlanBase, resp.getResponse_params().getPlancount());
                }
            }

            @Override
            public void onFailed(RespEntity<RunPlanDeta> resp) {
                onProResult(resp);
            }
        });
    }

    @OnClick(R.id.rl_select_patient)
    protected void onSelectPatient() {
        Intent intent = new Intent();
        intent.setClass(this, VisitPatientListActivity.class);
        startActivityForResult(intent,VisitPatientListActivity.REQUEST_CODE_SELECT_PATIENT);
    }

    @OnClick(R.id.rl_main_diag)
    protected void onSelectMainDiag() {
        SelectTxItemActivity.startAty(this,FragmentTxItemList.REQUEST_LOAD_TYPE_MAIN_DIAG);
    }

    @OnClick(R.id.rl_other_diag)
    protected void onSelectOtherDiag() {
        SelectTxItemActivity.startAty(this, FragmentTxItemList.REQUEST_LOAD_TYPE_OTHER_DIAG);
    }
    @OnClick(R.id.rl_treatment)
    protected void onSelectTreatment() {
        SelectTxItemActivity.startAty(this,FragmentTxItemList.REQUEST_LOAD_TYPE_TREAT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case VisitPatientListActivity.REQUEST_CODE_SELECT_PATIENT:
                clearData();
                HosPaitentList.HosPatientEntity hosEntity = data.getParcelableExtra("data");
                onLoadRunPlan(hosEntity.getPuuid());
                mTvPatient.setText(hosEntity.getName());
                break;
            case FragmentTxItemList.REQUEST_LOAD_TYPE_MAIN_DIAG:
                DiagList.DiagEntity mdiag = data.getParcelableExtra("data");
                mEdtMainDiag.setText(mdiag.getName());
                break;
            case FragmentTxItemList.REQUEST_LOAD_TYPE_OTHER_DIAG:
                DiagList.DiagEntity odiag = data.getParcelableExtra("data");
                mEdtOtherDiag.setText(odiag.getName());
                break;
            case FragmentTxItemList.REQUEST_LOAD_TYPE_TREAT:
                TreatPlanList.TreatPlanEntity treat = data.getParcelableExtra("data");
                mEdtTreat.setText(treat.getName());
                break;
        }
    }

    private boolean checkHasNull() {
        String p = mTvPatient.getText().toString();
        if (TextUtils.isEmpty(p)) {
            UIHelper.showToast("请选择患者");
            return true;
        }
        if (TextUtils.isEmpty(mTvStartDate.getText())) {
            UIHelper.showToast("请选择开始日期");
            return true;
        }
        if (TextUtils.isEmpty(mEdtMainDiag.getText())) {
            UIHelper.showToast("请输入主要诊断");
            return true;
        }
        if (TextUtils.isEmpty(mEdtOtherDiag.getText())) {
            UIHelper.showToast("请输入其他诊断");
            return true;
        }
        if (TextUtils.isEmpty(mEdtTreat.getText())) {
            UIHelper.showToast("请输入治疗方式");
            return true;
        }
        return false;
    }

    private void clearData() {
        mTvStartDate.setText("");
        mEdtMainDiag.setText("");
        mEdtOtherDiag.setText(null);
        mEdtTreat.setText("");
        mBtn.setText(R.string.select_plan);
        mPlanBase = null;
    }

    @OnClick(R.id.btn_select)
    protected void onSelectPlan() {
        if (checkHasNull()) return;

        if (mPlanBase == null ||mPlanBase.getStatus() == -200) {
            TPlanListActivity.startAty(this, 0);
        } else {
            if (mPlanBase != null) {
                PlanDetaActivity.startAty(this, "" + mPlanBase.getPid(), mPlanBase.getStatus(), mPlanBase);
            }
        }
    }

    @OnClick(R.id.rl_start_date)
    protected void onSetStartDate() {
        mDateTimePicker.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDateTimePicker = null;
    }


}
