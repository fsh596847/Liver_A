package com.android.doctor.ui.activity;

import java.util.HashMap;

import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.android.doctor.R;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.StringHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.RespError;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.yuntongxun.kitsdk.utils.LogUtil;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPasswordActivity extends BaseActivity {
	
	private static final String TAG = ForgetPasswordActivity.class.getSimpleName();

    @InjectView(R.id.edt_input_phone_number)
    protected EditText mEdtPhone;
    @InjectView(R.id.edt_input_new_password)
    protected EditText mEdtPassword;
    @InjectView(R.id.edt_input_v_code)
    protected EditText mEdtVCode;
	@InjectView(R.id.btn_regain)
	protected Button btnRegain;
	@InjectView(R.id.btn_reset)
	protected Button btnReset;
	
	protected CountDownTimer timer = new CountDownTimer(60000, 1000) {
		
		@Override
		public void onTick(long millisUntilFinished) {
			btnRegain.setClickable(false);
			btnRegain.setText((millisUntilFinished / 1000) + "秒后重新获取");
		}
		
		@Override
		public void onFinish() {
			btnRegain.setClickable(true);
			btnRegain.setText("重新获取");
		}
	};


	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_forget_password);
	}


	/**
	 * 获取验证码
	 **/
	@OnClick(R.id.btn_regain)
	protected void onGenVCode() {
		String phoneNumber = mEdtPhone.getText().toString();
		if (TextUtils.isEmpty(phoneNumber)) {
			UIHelper.showToast(this, "请输入手机号");
			return;
		}
		timer.start();
		ApiService api = RestClient.createService(ApiService.class);
		String did = DeviceHelper.getDeviceId(this);
		HashMap<String, String> map = new HashMap();
		map.put("username", phoneNumber);
		map.put("uuid", did);

		LogUtil.d(LogUtil.getLogUtilsTag(ForgetPasswordActivity.class), map.toString());
		Call<RespError> call = api.getUpwdVCode(map);
		call.enqueue(new Callback<RespError>() {
			@Override
			public void onResponse(Call<RespError> call, Response<RespError> response) {
				UIHelper.showToast(ForgetPasswordActivity.this, response.body().getError_msg());
			}
			@Override
			public void onFailure(Call<RespError> call, Throwable t) {
				UIHelper.showToast(ForgetPasswordActivity.this, "验证码发送失败");
				LogUtil.e(LogUtil.getLogUtilsTag(RegisterPhoneActivity.class), t.getMessage());
			}
		});
	}

    @OnClick(R.id.btn_reset)
    protected void onReset() {
        btnReset.setEnabled(false);
        DeviceHelper.hideSoftKeyboard(getCurrentFocus());

        final String phoneNumber = mEdtPhone.getText().toString().trim();
        final String password = mEdtPassword.getText().toString().trim();
        if (!StringHelper.isValidPhoneNumber(this, phoneNumber)) {
            btnReset.setEnabled(true);
            return ;
        }

        if (!StringHelper.isValidPassword(this, password)) {
            btnReset.setEnabled(true);
            return;
        }

        String vcode = mEdtVCode.getText().toString();
        if (TextUtils.isEmpty(vcode)) {
            btnReset.setEnabled(true);
            return;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("username", phoneNumber);
        map.put("password", password);
        map.put("validcode", vcode);
        map.put("uuid", DeviceHelper.getDeviceId(this));
        map.put("source", "android");
        map.put("version", "1.0");
        map.put("device_type", "3");

        ApiService api = RestClient.createService(ApiService.class);
        Call<RespEntity<Object>> call = api.updatePassword(map);
        call.enqueue(new Callback<RespEntity<Object>>() {
            @Override
            public void onResponse(Call<RespEntity<Object>> call, retrofit2.Response<RespEntity<Object>> response) {
                btnReset.setEnabled(true);
                UIHelper.showToast(ForgetPasswordActivity.this, "密码修改成功");
                LogUtil.d(TAG, response.body().toString());
            }

            @Override
            public void onFailure(Call<RespEntity<Object>> call, Throwable t) {
                btnReset.setEnabled(true);
                UIHelper.showToast(ForgetPasswordActivity.this, "密码修改失败");
                LogUtil.e(LogUtil.getLogUtilsTag(RegisterPhoneActivity.class), t.getMessage());
            }
        });
    }

    @OnClick(R.id.tv_cross)
    protected void onClose() {
        finish();
        overridePendingTransition(0, R.anim.slide_out_bottom);
    }
}
