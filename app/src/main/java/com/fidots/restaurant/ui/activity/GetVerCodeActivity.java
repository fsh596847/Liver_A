package com.fidots.restaurant.ui.activity;

import org.json.JSONObject;
import com.android.volley.VolleyError;
import com.fidots.restaurant.R;
import com.fidots.restaurant.api.NetworkResponseHandler;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GetVerCodeActivity extends BaseCodeActivity implements View.OnClickListener{
	private static final String TAG = GetVerCodeActivity.class.getSimpleName();
	public static final String EXTRA_USERNAME = "username";
	public static final String EXTRA_VERIFY_CODE = "verifycode";
	public static final String EXTRA_PASSWORD = "password";
		
	private Button mNextButton;
	private String mUserName;
	private EditText mCodeEditText;
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mUserName = getIntent().getStringExtra(EXTRA_USERNAME);
		setContentView(R.layout.activity_register_verifycode);
		onGenVCode(mUserName);
		initViews();
		setActionBar();
	}

	
	private void initViews() {
		mCodeEditText = (EditText)findViewById(R.id.edtvt_input_verifycode);
		btnRegain = (Button)findViewById(R.id.btn_regain_verifycode);
		mNextButton = (Button)findViewById(R.id.btn_next_inputstore);
		btnRegain.setOnClickListener(this);
		mNextButton.setOnClickListener(this);
	}
	

	private void onNext() {
		mNextButton.setEnabled(false);
		//UIHelper.hideKeyboard(this, getCurrentFocus());
		final String vCode = mCodeEditText.getText().toString();
		if (!preCheck(mUserName, vCode)) {	
			mNextButton.setEnabled(true);
			return;
		}	
		onCheckVCode(vCode, mUserName);
	}
	
	@Override
	protected NetworkResponseHandler getRespHandler(final String userPhone, final String vCode) {
		return new NetworkResponseHandler() {			
			@Override
			public void onSuccess(JSONObject jsonObject) {
				//UIHelper.showSetPassword(GetVerCodeActivity.this, userPhone, vCode);
			}

			@Override
			public void onFail(VolleyError error) {
				//UIHelper.showToast(GetVerCodeActivity.this, R.string.error_verify_code_is_wrong);
				mNextButton.setEnabled(true);
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
			onGenVCode(mUserName);
			break;
		case R.id.btn_next_inputstore:
			onNext();			
			break;
		default:
			break;
		}
	}
	
}
