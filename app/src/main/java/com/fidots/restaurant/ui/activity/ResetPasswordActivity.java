package com.fidots.restaurant.ui.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.android.volley.VolleyError;
import com.fidots.restaurant.R;
import com.fidots.restaurant.api.ApiClient;
import com.fidots.restaurant.api.NetworkResponseHandler;
import com.fidots.restaurant.app.AppContext;
import com.fidots.restaurant.helper.StringHelper;

public class ResetPasswordActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = ResetPasswordActivity.class.getSimpleName();
	public static final String EXTRA_USERNAME = "username";
	public static final String EXTRA_VERIFY_CODE = "verifycode";
	
	private EditText userEditText;
	private EditText passwdEdtText;
	private Button btnNext;
	private String userName;
	private String verifyCode;
	
	@Override
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
	    
	protected int getLayoutId() {
		return R.layout.activity_register_password;
	}
	
	final private void getExtras() {
		Intent intent = getIntent();
		userName = intent.getStringExtra(EXTRA_USERNAME);
		verifyCode = intent.getStringExtra(EXTRA_VERIFY_CODE);	
	}
	
	protected void initView() {
		userEditText = (EditText) findViewById(R.id.edttxt_input_username);
		userEditText.setVisibility(View.GONE);
		passwdEdtText = (EditText)findViewById(R.id.edttxt_input_pwd);
		btnNext = (Button) findViewById(R.id.btn_next);
		btnNext.setOnClickListener(this);
	}

	@Override
	protected void initData() {
		super.initData();
		getExtras();
	}
	
	private void resetPasswd() {
		btnNext.setEnabled(false);
		String passwd = passwdEdtText.getText().toString();
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
	
	private NetworkResponseHandler getResponseHandler() {
		return new NetworkResponseHandler() {

			@Override
			public void onSuccess(JSONObject jsonObject) {
				//UIHelper.showMainActivity(ResetPasswordActivity.this);
				//AppManager.getAppManager().finishActivity(ResetPasswordActivity.this);
			}

			@Override
			public void onFail(VolleyError error) {
				//UIHelper.showToast(ResetPasswordActivity.this, R.string.failed_reset_passwd);
				btnNext.setEnabled(true);
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next:
			resetPasswd();
			//UIHelper.showMainActivity(ResetPasswordActivity.this);
			break;

		default:
			break;
		}
	}
}
