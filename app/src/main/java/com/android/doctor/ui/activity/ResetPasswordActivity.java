package com.android.doctor.ui.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.widget.EditText;

import com.android.doctor.app.AppContext;
import com.android.doctor.rest.ApiClient;
import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

public class ResetPasswordActivity extends BaseActivity {
	private static final String TAG = ResetPasswordActivity.class.getSimpleName();
	public static final String EXTRA_USERNAME = "username";
	public static final String EXTRA_VERIFY_CODE = "verifycode";
	@InjectView(R.id.edt_input_new_password)
	protected EditText mEdtNewPassword1;
    @InjectView(R.id.edt_input_new_password2)
	protected EditText mEdtNewPassword2;

	private String userName;
	private String verifyCode;
	
	@Override
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
	    
	protected int getLayoutId() {
		return R.layout.activity_reset_password;
	}
	
	final private void getExtras() {
		Intent intent = getIntent();
		userName = intent.getStringExtra(EXTRA_USERNAME);
		verifyCode = intent.getStringExtra(EXTRA_VERIFY_CODE);	
	}

	@Override
	protected void initData() {
		super.initData();
		getExtras();
	}
	
	private void resetPasswd() {
		String passwd = mEdtNewPassword2.getText().toString();
		/*if (!StringHelper.isValidatePasswd(this, passwd)) {
			btnNext.setEnabled(true);
			return;
		}*/
		
		HashMap<String, String> map = new HashMap<String, String>();		
		map.put("password", passwd);
		map.put("verification_code", verifyCode);
		map.put("mobilephone_num", userName);
		JSONObject jsonObject = new JSONObject(map);
		JSONObject wrapper = new JSONObject();
		try {
			wrapper.put("user", jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		
		String url = "";//ApiService.post_forget_passwd();
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		//apiClient.post(url, wrapper, TAG, getResponseHandler());
	}

	@OnClick(R.id.btn_next)
	protected void onBtnClick() {
		resetPasswd();
	}
}
