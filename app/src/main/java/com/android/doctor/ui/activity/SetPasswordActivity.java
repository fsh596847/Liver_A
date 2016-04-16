package com.android.doctor.ui.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.doctor.app.AppContext;
import com.android.doctor.rest.ApiClient;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.widget.SimpleTextWatcher;
import com.android.doctor.R;

public class SetPasswordActivity extends BaseActivity {
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
		return R.layout.activity_reset_password;
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

}
