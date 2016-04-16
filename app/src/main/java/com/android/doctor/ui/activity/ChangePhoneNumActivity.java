package com.android.doctor.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.doctor.app.AppContext;
import com.android.doctor.rest.ApiClient;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.widget.SimpleTextWatcher;
import com.android.doctor.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class ChangePhoneNumActivity extends BaseActivity {
	
	private static final String TAG = ChangePhoneNumActivity.class.getSimpleName();
	
	private EditText userPhoneEditText;
	private EditText vCodeEditText;
	private AppCompatButton btnNext;
    protected AppCompatButton mBtnRegain;
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
    	return R.layout.activity_phone_verifycode;
    }

	protected void initView() {

	}
	
	protected void initData() {
		Intent intent = getIntent();
		//typeIntent = intent.getIntExtra(INTENT_TYPE_PARAM, INTENT_RESET_PASSWORD);
		userOldPhone = intent.getStringExtra(GetVerCodeActivity.EXTRA_USERNAME);
		userOldvCode = intent.getStringExtra(GetVerCodeActivity.EXTRA_VERIFY_CODE);
		setupListener();
	}
	
	private void setupListener() {
		//clearUserNameImg.setOnClickListener(this);
		userPhoneEditText.addTextChangedListener(mUserNameWatcher);
		//btnNext.setOnClickListener(this);
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


	private void requestUpdatePhone(String userNewPhone, String userNewCode) {
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

	protected void obtainVerifyCode(final String userPhone, boolean isCheckUser) {
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		//if (!StringHelper.isValidatePhoneNumber(this, userPhone)) {
		//	return;
		//}
	}

	protected void onGenVCode(String userPhone) {
		timer.start();
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		//String url = ApiService.get_gen_verify_code(userPhone);
	}


	protected boolean preCheck(String strPhone, String vCode) {
		//return StringHelper.isValidatePhoneNumber(this, strPhone) &&
		//		StringHelper.isVerifyCode(vCode);
		return false;
	}

	protected void onCheckVCode(final String vCode, final String userPhone) {
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("mobilephone_num", userPhone);
		map.put("verification_code", vCode);

		JSONObject internal = new JSONObject(map);
		JSONObject wrapper = new JSONObject();
		try {
			wrapper.put("user", internal);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
	}
}
