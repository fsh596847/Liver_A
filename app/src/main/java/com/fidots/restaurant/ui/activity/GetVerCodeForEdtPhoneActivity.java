package com.fidots.restaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.fidots.restaurant.app.PrefManager;
import com.fidots.restaurant.ui.widget.SimpleTextWatcher;

import org.json.JSONObject;


public class GetVerCodeForEdtPhoneActivity extends BaseCodeActivity implements OnClickListener{
	
	private static final String TAG = GetVerCodeForEdtPhoneActivity.class.getSimpleName();
	
	private EditText userPhoneEditText;
	private EditText vCodeEditText;
	private AppCompatButton btnNext;
	private ImageView clearUserNameImg;
	private int typeIntent;
	private String userOldPhone;
	private String userOldvCode;
	
	private final TextWatcher mUserNameWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            clearUserNameImg.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
        }
    };
	
    @Override
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
    
    protected int getLayoutId() {
    	return R.layout.activity_phone_verifycode;
    }

	protected void initView() {
		vCodeEditText = (EditText)findViewById(R.id.edt_verifycode);
		btnRegain = (Button)findViewById(R.id.btn_regain_verifycode);
		btnNext = (AppCompatButton) findViewById(R.id.btn_next);
		clearUserNameImg = (ImageView) findViewById(R.id.iv_clear_username);
	}
	
	protected void initData() {
		Intent intent = getIntent();
		typeIntent = intent.getIntExtra(INTENT_TYPE_PARAM, INTENT_RESET_PASSWORD);
		userOldPhone = intent.getStringExtra(GetVerCodeActivity.EXTRA_USERNAME);
		userOldvCode = intent.getStringExtra(GetVerCodeActivity.EXTRA_VERIFY_CODE);
		setupListener();
	}
	
	private void setupListener() {
		btnRegain.setOnClickListener(this);
		clearUserNameImg.setOnClickListener(this);
		userPhoneEditText.addTextChangedListener(mUserNameWatcher);
		btnNext.setOnClickListener(this);
	}
	
	private void onNext() {
		btnNext.setEnabled(false);
		String userPhone = userPhoneEditText.getText().toString();
		String vCode = vCodeEditText.getText().toString();
		if (!preCheck(userPhone, vCode)) {
			btnNext.setEnabled(true);
			return;
		}
		onCheckVCode(vCode, userPhone);
	}
	
	protected NetworkResponseHandler getRespHandler(final String userPhone, final String vCode) {
		return new NetworkResponseHandler() {			
			@Override
			public void onSuccess(JSONObject jsonObject) {
				/*if (INTENT_RESET_PASSWORD == typeIntent) {
					//UIHelper.showResetPassword(GetVerCodeForEdtPhoneActivity.this, userPhone, vCode);
				} else if (INTENT_RESET_PHONE == typeIntent) {
					requestUpdatePhone(userPhone, vCode);
				}*/
			}

			@Override
			public void onFail(VolleyError error) {
				//UIHelper.showToast(GetVerCodeForEdtPhoneActivity.this, R.string.error_verify_code_is_wrong);
				btnNext.setEnabled(true);
			}
		};
	}
	
	
	private void requestUpdatePhone(String userNewPhone, String userNewCode) {
		String userID = PrefManager.get(AppContext.context(), "user.uid");
		String url = "";//ApiService.post_update_user_phone(userID);
		/*JSONObject jsonObject = new JSONObject();
		JSONObject jsonObjectWrapper = new JSONObject();
		try {
			jsonObject.put("old_mobilephone_num", userOldPhone);
			jsonObject.put("old_verification_code", userOldvCode);
			jsonObject.put("fresh_mobilephone_num", userNewPhone);
			jsonObject.put("fresh_verification_code", userNewCode);
			jsonObjectWrapper.put("user", jsonObject);
		} catch (JSONException e) {
			e.printStackTrace();
		}*/
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		//apiClient.post(url, jsonObjectWrapper, TAG, getRespHandler());
	}
	
	
	protected NetworkResponseHandler getRespHandler() {
		return new NetworkResponseHandler() {

			@Override
			public void onSuccess(JSONObject jsonObject) {
				//UIHelper.showToast(GetVerCodeForEdtPhoneActivity.this, R.string.error_verify_code_is_wrong);
				//UIHelper.reLogin(GetVerCodeForEdtPhoneActivity.this);
				//AppManager.getAppManager().finishAllActivityExclude(MainActivity.class);
			}

			@Override
			public void onFail(VolleyError error) {
				//UIHelper.showToast(GetVerCodeForEdtPhoneActivity.this, R.string.error_verify_code_is_wrong);
				btnNext.setEnabled(true);
			}
		};
	}
	
	
	@Override
	public void onClick(View v) {
		if (isFastDoubleClick()) {
			return;
		}
		
		switch (v.getId()) {
		case R.id.btn_regain_verifycode:
			String userName = userPhoneEditText.getText().toString();
			if (typeIntent == INTENT_RESET_PASSWORD) {
				obtainVerifyCode(userName, true);
			} else {
				obtainVerifyCode(userName, false);
			}
			break;		
		case R.id.btn_next:
			onNext();
			break;
		case R.id.iv_clear_username:
            userPhoneEditText.getText().clear();
            userPhoneEditText.requestFocus();
            break;
		default:
			break;
		}
	}
}
