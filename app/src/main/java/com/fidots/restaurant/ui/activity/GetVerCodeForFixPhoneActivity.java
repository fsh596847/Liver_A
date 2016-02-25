package com.fidots.restaurant.ui.activity;

import org.json.JSONObject;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.fidots.restaurant.R;
import com.fidots.restaurant.api.NetworkResponseHandler;
import com.fidots.restaurant.app.PrefManager;
import com.fidots.restaurant.helper.StringHelper;


public class GetVerCodeForFixPhoneActivity extends BaseCodeActivity implements OnClickListener{
	
	@SuppressWarnings("unused")
	private static final String TAG = GetVerCodeForFixPhoneActivity.class.getSimpleName();
	private TextView userPhoneTextView;
	
	private EditText vCodeEditText;
	
	private AppCompatButton btnNext;
	
	private String userPhone;	
	
	private int typeIntent;
	
	@Override
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
	    
	protected int getLayoutId() {
		return R.layout.activity_change_password;
	}	

	protected void initView() {
		userPhoneTextView = (TextView) findViewById(R.id.tv_userphone);
		vCodeEditText = (EditText)findViewById(R.id.edt_verifycode);
		btnRegain = (Button)findViewById(R.id.btn_regain_verifycode);
		btnNext = (AppCompatButton) findViewById(R.id.btn_next);		
	}
	
	protected void initData() {
		userPhone = PrefManager.getUserPhone(this);
		userPhoneTextView.setText(StringHelper.replace(userPhone, 3, 4, '*'));
		Intent intent = getIntent();
		typeIntent = intent.getIntExtra(INTENT_TYPE_PARAM, INTENT_RESET_PASSWORD);
		setupListener();
	}
	
	private void setupListener() {
		btnRegain.setOnClickListener(this);	
		btnNext.setOnClickListener(this);
	}
	
	private void onNext() {
		btnNext.setEnabled(false);
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
				if (INTENT_RESET_PASSWORD == typeIntent) {
					//UIHelper.showResetPassword(GetVerCodeForFixPhoneActivity.this, userPhone, vCode);
				} else if (INTENT_RESET_PHONE == typeIntent) {
					//UIHelper.showResetPhone(GetVerCodeForFixPhoneActivity.this, userPhone, vCode);
				}
			}

			@Override
			public void onFail(VolleyError error) {
				//UIHelper.showToast(GetVerCodeForFixPhoneActivity.this, R.string.error_verify_code_is_wrong);
				btnNext.setEnabled(true);
			}
		};
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (isFastDoubleClick()) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		if (isFastDoubleClick()) {
			return;
		}
		
		switch (v.getId()) {
		case R.id.btn_regain_verifycode:			
			obtainVerifyCode(userPhone, false);
			break;		
		case R.id.btn_next:
			onNext();
			break;		
		default:
			break;
		}
	}
}
