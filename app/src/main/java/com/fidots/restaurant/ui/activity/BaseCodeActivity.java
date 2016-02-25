package com.fidots.restaurant.ui.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.CountDownTimer;
import android.widget.Button;
import com.android.volley.VolleyError;
import com.fidots.restaurant.R;
import com.fidots.restaurant.api.ApiClient;
import com.fidots.restaurant.api.NetworkResponseHandler;
import com.fidots.restaurant.app.AppContext;
import com.fidots.restaurant.helper.StringHelper;

public abstract class BaseCodeActivity extends BaseActivity{
	
	private static final String TAG = BaseCodeActivity.class.getSimpleName();
	public static int INTENT_RESET_PASSWORD = 1;
	public static int INTENT_RESET_PHONE = 2;
	public static String INTENT_TYPE_PARAM = "intent_type_param";	
	public static final String USER_NAME = "username";
	public static final String VERIFY_CODE = "verify_code";

	protected Button btnRegain;	
	protected Button btnNext;
	
	protected CountDownTimer timer = new CountDownTimer(60000, 1000) {
		
		@Override
		public void onTick(long millisUntilFinished) {
			btnRegain.setClickable(false);
			btnRegain.setText((millisUntilFinished / 1000) + "����ط�");
		}
		
		@Override
		public void onFinish() {
			btnRegain.setClickable(true);
			btnRegain.setText("���·���");
		}
	};
	
	protected void obtainVerifyCode(final String userPhone, boolean isCheckUser) {
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		//if (!StringHelper.isValidatePhoneNumber(this, userPhone)) {
		//	return;
		//}
		
		if (isCheckUser) {
			//String url = ApiService.get_user_exist(userPhone);
			String url = "";
			apiClient.get(url, TAG, new NetworkResponseHandler() {
				@Override
				public void onSuccess(JSONObject jsonObject) {
					onGenVCode(userPhone);
				}

				@Override
				public void onFail(VolleyError error) {
					//UIHelper.showToast(BaseCodeActivity.this, R.string.error_phone_not_registed);
				}
			});
		} else {
			onGenVCode(userPhone);
		}
	}
	
	protected void onGenVCode(String userPhone) {
		timer.start();
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		//String url = ApiService.get_gen_verify_code(userPhone);
		String url = "";
        apiClient.get(url, TAG, new NetworkResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonObject) {}

			@Override
			public void onFail(VolleyError error) {
				//UIHelper.showToast(BaseCodeActivity.this, R.string.failed_gen_verify_code);
			}
		});
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
		
		String url = "";// ApiService.post_check_verify_code();
		apiClient.post(url, wrapper, TAG, getRespHandler(userPhone, vCode));
	}
	
	
	protected abstract NetworkResponseHandler getRespHandler(final String userPhone, final String vCode);
	
	protected void setBtnEnable(boolean enable) {
		if (btnNext != null) {
			btnNext.setEnabled(enable);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
	
}
