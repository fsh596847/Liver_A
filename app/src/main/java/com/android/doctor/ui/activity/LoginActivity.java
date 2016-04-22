package com.android.doctor.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.doctor.app.AppContext;
import com.android.doctor.app.AppManager;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.StringHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.R;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.yuntongxun.kitsdk.utils.LogUtil;

import java.util.HashMap;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {
	private static final String TAG = LogUtil.getLogUtilsTag(LoginActivity.class);

    @InjectView(R.id.edt_input_phone_number)
    protected EditText mEdtPhoneNum;
    @InjectView(R.id.edt_input_password)
    protected EditText mEdtPassword;
    @InjectView(R.id.iv_clear_username)
    protected ImageView mImgClearUser;
    @InjectView(R.id.iv_clear_password)
    protected ImageView mImgClearPassword;

	private String password;
    
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
    
	protected int getLayoutId() {
		return R.layout.activity_login;
	}

	
	protected void initData() {
		String userPhone = AppContext.context().getProperty("user.user_phone");
		if (userPhone != null) {
			mEdtPhoneNum.setText(userPhone);
		}		
	}

    /**
     * 忘记密码
     */
    @OnClick(R.id.tv_forget_passwd)
    protected void onForgetPassword() {
        UIHelper.showtAty(this, ForgetPasswordActivity.class);
    }

    /**
     * 开始登陆
     */
    @OnClick(R.id.btn_login)
    protected void onLogin() {
        String strUserName = mEdtPhoneNum.getText().toString();
        password = mEdtPassword.getText().toString();
        onLogin(strUserName, password);
    }

    @OnClick(R.id.iv_clear_username)
    protected void onClearUser() {
        mEdtPhoneNum.getText().clear();
        mEdtPassword.requestFocus();
    }

    @OnClick(R.id.iv_clear_password)
    protected void onClearPassword() {
        mEdtPassword.getText().clear();
        mEdtPassword.requestFocus();
    }

    @OnTextChanged(R.id.edt_input_phone_number)
	public void onEdtPhoneNum(CharSequence s, int start, int before, int count) {
        mImgClearUser.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
    }

    @OnTextChanged(R.id.edt_input_password)
    protected void onEdtPassword(CharSequence s, int start, int before, int count) {
        mImgClearPassword.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
    }

    @OnClick(R.id.tv_cross)
    protected void onClose() {
        finish();
        overridePendingTransition(0, R.anim.slide_out_bottom);
    }


	private boolean preLogin(String userName, String passwd) {	
		if (!DeviceHelper.isNetworkConnected(this)) {
			UIHelper.showToast(this, R.string.no_network_connection, 3000);
			return false;
		}
		
		if (StringHelper.isEmpty(userName)) {
			UIHelper.showToast(this, R.string.error_username_is_empty, 3000);
			return false;
		}

        if (StringHelper.isEmpty(passwd)) {
            UIHelper.showToast(this, R.string.error_password_is_empty, 3000);
            return false;
        }
		return true;
	}

	private void onLogin(String userName, String passwd) {
		if (!preLogin(userName, passwd)) {
			return;
		}

		HashMap<String, String> map = new HashMap<String, String>();
        map.put("source", "android");
        map.put("uuid", DeviceHelper.getDeviceId(this));
        map.put("version", "1.0");
        map.put("device_type", "3");
        map.put("password", passwd);
        map.put("username", userName);

        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<User>> call = service.login(map);
        call.enqueue(new Callback<RespEntity<User>>() {
            @Override
            public void onResponse(Call<RespEntity<User>> call, Response<RespEntity<User>> response) {
                //GitUser user = gson.fromJson(response.body().charStream(), GitUser.class);
                RespEntity<User> entity = response.body();
                if (entity != null) {
                    if (entity.getError_code() == 0) {
                        onSuccess(entity.getResponse_params());
                    } else {
                        onFail(entity.getError_msg());
                    }
                } else {
                    onFail("登录失败");
                }
            }

            @Override
            public void onFailure(Call<RespEntity<User>> call, Throwable t) {
                onFail("登录失败, 请检查网络连接");
            }
        });
	}

    private void onSuccess(User u) {
        AppContext.context().saveUser(u);
        AppManager.getAppManager().finishActivity(LoginEntryActivity.class);
        AppManager.getAppManager().finishActivity(this);
        UIHelper.showtAty(this, MainActivity.class);
    }

    private void onFail(String t) {
        UIHelper.showToast(this, t, 3000);
    }

}
