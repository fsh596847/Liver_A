package com.fidots.restaurant.ui.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.fidots.restaurant.R;
import com.fidots.restaurant.api.ApiClient;
import com.fidots.restaurant.api.NetworkResponseHandler;
import com.fidots.restaurant.app.AppContext;
import com.fidots.restaurant.ui.widget.SimpleTextWatcher;

public class SetPasswordActivity extends BaseActivity implements OnClickListener{
	private static final String TAG = "RegisterPasswdActivity";
	public static final String EXTRA_USERNAME = "username";
	public static final String EXTRA_VERIFY_CODE = "verifycode";
	
	private EditText passwdEditText;
	private EditText userNamEditText;
	private Button doneButton;
	private String strPhoneNumber;
	private String strVerifyCode;
	private String strPasswd;
	private ImageView clearUserNameImg;
	private ImageView clearPasswdImg;
	
	private final TextWatcher mUserNameWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            clearUserNameImg
                    .setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    };
    
    private final TextWatcher mPassswordWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            clearPasswdImg
                    .setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    };

    @Override
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
    
	protected int getLayoutId() {		
		return R.layout.activity_register_password;
	}
	
	protected void initView() {	
		passwdEditText = (EditText)findViewById(R.id.edttxt_input_pwd);
		userNamEditText = (EditText)findViewById(R.id.edttxt_input_username);
		clearUserNameImg = (ImageView) findViewById(R.id.iv_clear_username);
		clearPasswdImg = (ImageView) findViewById(R.id.iv_clear_passwd);
		doneButton = (Button) findViewById(R.id.btn_next);
		clearUserNameImg.setOnClickListener(this);
		clearPasswdImg.setOnClickListener(this);
		userNamEditText.addTextChangedListener(mUserNameWatcher);
		passwdEditText.addTextChangedListener(mPassswordWatcher);
		doneButton.setOnClickListener(this);
	}
	
	@Override
	protected void initData() {
		super.initData();
		getExtras();
	}
	
	private void getExtras() {
		Intent intent = getIntent();
		strPhoneNumber = intent.getStringExtra(EXTRA_USERNAME);
		strVerifyCode = intent.getStringExtra(GetVerCodeActivity.EXTRA_VERIFY_CODE);	
	}

	private boolean preRegister(String userName, String passwd) {		
		return true ;//StringHelper.isValidatePasswd(this, passwd);
	}
	
	
	private void onRegister() {
		doneButton.setEnabled(false);

		strPasswd = passwdEditText.getText().toString();
		String userName = userNamEditText.getText().toString();
		
		if (!preRegister(userName, strPasswd)) {
			doneButton.setEnabled(true);
			return;
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("username", userName);
		map.put("password", strPasswd);
		map.put("mobilephone_num", strPhoneNumber);
		map.put("verification_code", strVerifyCode);
		map.put("role", "STORE");
		
		JSONObject internal = new JSONObject(map);
		JSONObject wrapper = new JSONObject();
		try {
			wrapper.put("user", internal);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		
		String url = "";//ApiService.post_register_user();
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		//apiClient.post(url, wrapper, TAG, getResponseHandler());
	}
	
	private NetworkResponseHandler getResponseHandler() {
		return new NetworkResponseHandler() {			

			@Override
			public void onSuccess(JSONObject jsonObject) {
				/*Gson gson = new Gson();
				String str = null;
				try {
					str = jsonObject.getString("token_info");
				} catch (JSONException e) {					
					e.printStackTrace();
				}
				User user = gson.fromJson(str, User.class);
				PrefManager.putUserInfo(SetPasswordActivity.this, user);	
				AppContext.context().initLogin();
				UIHelper.showMainActivity(SetPasswordActivity.this);*/
			}

			@Override
			public void onFail(VolleyError error) {
				//UIHelper.showToast(SetPasswordActivity.this, R.string.network_error);
				doneButton.setEnabled(true);
			}
		};
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next:
			onRegister();
			//UIHelper.showMainActivity(SetPasswordActivity.this);
			break;
		case R.id.iv_clear_username:
            userNamEditText.getText().clear();
            userNamEditText.requestFocus();
            break;
        case R.id.iv_clear_passwd:
            passwdEditText.getText().clear();
            passwdEditText.requestFocus();
            break;
		default:
			break;
		}
	}
}
