package com.android.doctor.ui.patient;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.JsonUtil;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.AdjustPlanParam;
import com.android.doctor.model.DSendInviteParam;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.google.gson.Gson;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/11.
 */
public class InvitePatientActivity extends BaseActivity {
    public static final String ARG_ENTITY = "ARG_ENTITY";
    public static final String ARG_TYPE = "ARG_TYPE"; /**1=normal invite, 2=manual invite **/
    @InjectView(R.id.edt_input_name)
    protected EditText mEdtName;
    @InjectView(R.id.edt_input_phone_number)
    protected EditText mEdtPhone;
    @InjectView(R.id.edt_input_id_num)
    protected EditText mEdtCardNum;

    private HosPaitentList.HosPatientEntity patientEntity;
    private int type;

    public static void startAty(Activity activity, HosPaitentList.HosPatientEntity data, int type) {
        Intent intent = new Intent();
        intent.setClass(activity, InvitePatientActivity.class);
        intent.putExtra(ARG_ENTITY, data);
        intent.putExtra(ARG_TYPE, type);
        activity.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_patient_add_form);
    }

    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        patientEntity = intent.getParcelableExtra(ARG_ENTITY);
        type = intent.getIntExtra(ARG_TYPE, 0);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.invite_patient);
        setViewData();
    }

    private void setViewData() {
        if (patientEntity != null) {
            mEdtName.setText(patientEntity.getName());
            mEdtPhone.setText(patientEntity.getPhone());
            mEdtCardNum.setText(patientEntity.getCard());
        }
    }

    @OnClick(R.id.btn_invite)
    protected void onSendInvite() {
        String name = mEdtName.getText().toString();
        String phone = mEdtPhone.getText().toString();
        if (TextUtils.isEmpty(name)) {
            UIHelper.showToast(this, R.string.error_patient_name_can_not_be_null, 2000);
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            UIHelper.showToast(this, R.string.error_patient_phone_can_not_be_null, 2000);
            return;
        }
        DSendInviteParam param = new DSendInviteParam();
        User u = AppContext.context().getUser();
        if (u == null || u.getUser() == null) {
            return;
        }
        User.UserEntity ue = u.getUser();
        param.setDname(ue.getUsername());
        param.setDuid("" + ue.getDuid());
        param.setHosid("" + ue.getHosid());

        if (type == 1) {
            param.setPcard(patientEntity.getCard());
            param.setPhone(patientEntity.getPhone());
            param.setPname(patientEntity.getName());
            param.setPuuid(patientEntity.getPuuid());
            ApiService service = RestClient.createService(ApiService.class);
            Call<RespEntity> call = service.dSendInvite(param);
            onSendInvite(call);
        } else if (type == 2) {
            param.setPcard(mEdtCardNum.getText().toString());
            param.setPhone(mEdtPhone.getText().toString());
            param.setPname(name);
            param.setType("qt");
            ApiService service = RestClient.createService(ApiService.class);
            Call<RespEntity> call = service.dSendInviteManual(param);
            onSendInvite(call);
        }
    }

    private void onSendInvite(Call<RespEntity> call) {
        showProcessDialog();

        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                RespEntity<PlanDeta> data = response.body();
                onResult(data);
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
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
