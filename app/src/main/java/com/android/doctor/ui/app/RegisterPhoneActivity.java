package com.android.doctor.ui.app;

import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.doctor.helper.JsonUtil;
import com.android.doctor.helper.StringHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.RespError;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.R;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.ui.base.BaseActivity;
import com.yuntongxun.kitsdk.utils.LogUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPhoneActivity extends BaseActivity {
	private static final String TAG = RegisterPhoneActivity.class.getSimpleName();
	@InjectView(R.id.btn_register)
	protected AppCompatButton mBtnRegister;
    @InjectView(R.id.btn_regain)
    protected AppCompatButton mBtnRegain;
    @InjectView(R.id.edt_input_phone_number)
	protected EditText mEdtUser;
    @InjectView(R.id.edt_input_password)
	protected EditText mEdtPassword;
    @InjectView(R.id.edt_input_v_code)
    protected EditText mEdtvCode;
	@InjectView(R.id.iv_clear_phone)
	protected ImageView mClearUserImg;
    @InjectView(R.id.iv_clear_password)
    protected ImageView mClearPasswordImg;

	public RegisterPhoneActivity() {}

    protected CountDownTimer timer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mBtnRegain.setClickable(false);
            mBtnRegain.setText((millisUntilFinished / 1000) + "秒后重新获取");
        }

        @Override
        public void onFinish() {
            mBtnRegain.setClickable(true);
            mBtnRegain.setText("重新获取");
        }
    };
    @Override
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
    
	protected int getLayoutId() {
		return R.layout.activity_register_phone;
	}

	protected void initView() {
        ButterKnife.inject(this);
	}

    @OnTextChanged(R.id.edt_input_phone_number)
    protected void onClearUser(CharSequence s, int start, int before, int count) {
        mClearUserImg.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
    }

    @OnTextChanged(R.id.edt_input_password)
    protected void onClearPassword(CharSequence s, int start, int before, int count) {
        mClearPasswordImg.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
    }

    @OnClick(R.id.iv_clear_phone)
    protected void onClearUser(){
        mEdtUser.getText().clear();
        mEdtUser.requestFocus();
    }

    @OnClick(R.id.iv_clear_password)
    protected void onClearPassword(){
        mEdtPassword.getText().clear();
        mEdtPassword.requestFocus();
    }

    /**
     * 开始注册
     **/
    @OnClick(R.id.btn_register)
    protected void onRegister() {

        DeviceHelper.hideSoftKeyboard(getCurrentFocus());
		final String phoneNumber = mEdtUser.getText().toString().trim();
        final String password = mEdtPassword.getText().toString().trim();
        String vcode = mEdtvCode.getText().toString();
        if (!checkPhNum() || !StringHelper.isValidPassword(this, password) || TextUtils.isEmpty(vcode)) {
            return;
        }

        mBtnRegister.setClickable(false);
        Map<String, String> map = new HashMap<>();
        map.put("username", phoneNumber);
        map.put("password", password);
        map.put("validcode", vcode);
        map.put("uuid", DeviceHelper.getDeviceId(this));
        map.put("source", "android");
        map.put("version", "1.0");
        map.put("device_type", "3");

        showProcessDialog();
		ApiService api = RestClient.createService(ApiService.class);
		Call<ResponseBody> call = api.signup(map);
		call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                mBtnRegister.setClickable(true);
                UIHelper.showToast("注册成功");
                dismissProcessDialog();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mBtnRegister.setClickable(true);
                UIHelper.showToast("注册失败");
                dismissProcessDialog();
            }
        });
	}

    private boolean checkPhNum() {
        String phoneNumber = mEdtUser.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            UIHelper.showToast("请输入手机号");
            return false;
        }
        if (!StringHelper.isPhoneNumber(phoneNumber)) {
            UIHelper.showToast("请输入正确的手机号");
            return false;
        }
        return true;
    }


    /**
     * 获取验证码
     **/
    @OnClick(R.id.btn_regain)
    protected void onGenVCode() {
        if (!checkPhNum()) return;

        timer.start();
        ApiService api = RestClient.createService(ApiService.class);
        String did = DeviceHelper.getDeviceId(this);
        String phoneNumber = mEdtUser.getText().toString();
        String jstr = "{username:" + phoneNumber + ",uuid:" + did + ",source:android}";
        LogUtil.d(LogUtil.getLogUtilsTag(RegisterPhoneActivity.class), jstr);
        Call<RespError> call = api.getURegVCode(JsonUtil.toJson(jstr));
        call.enqueue(new Callback<RespError>() {
            @Override
            public void onResponse(Call<RespError> call, Response<RespError> response) {
                UIHelper.showToast(response.body().getError_msg());
            }
            @Override
            public void onFailure(Call<RespError> call, Throwable t) {
                UIHelper.showToast("验证码发送失败");
            }
        });
    }

    @OnClick(R.id.tv_cross)
    protected void onClose() {
        finish();
        overridePendingTransition(0, R.anim.slide_out_bottom);
    }

}
