package com.android.doctor.ui.activity;

import com.android.doctor.R;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class GetVerCodeActivity extends ForgetPasswordActivity {
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
		setContentView(R.layout.activity_forget_password);
		//onGenVCode(mUserName);
		//setActionBar();
	}

	

	private void onNext() {
		mNextButton.setEnabled(false);
		//UIHelper.hideKeyboard(this, getCurrentFocus());
		final String vCode = mCodeEditText.getText().toString();

	}

	
}
