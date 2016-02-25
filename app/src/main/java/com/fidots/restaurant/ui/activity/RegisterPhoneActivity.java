package com.fidots.restaurant.ui.activity;


import org.json.JSONObject;
import com.android.volley.VolleyError;
import com.fidots.restaurant.R;
import com.fidots.restaurant.api.ApiClient;
import com.fidots.restaurant.api.NetworkResponseHandler;
import com.fidots.restaurant.app.AppContext;
import com.fidots.restaurant.ui.widget.SimpleTextWatcher;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class RegisterPhoneActivity extends BaseActivity implements OnClickListener {
	private static final String TAG = RegisterPhoneActivity.class.getSimpleName();
	
	private Button mButton;
	private EditText mEditText;
	//private TextInputLayout txInputLayout;
	private ImageView clearUserNameImg;
	public RegisterPhoneActivity() {}
	
	private final TextWatcher mUserNameWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            clearUserNameImg
                    .setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    };
    
    @Override
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
    
	protected int getLayoutId() {
		return R.layout.activity_register_phone;
	}
	
	@SuppressLint("NewApi")
	protected void initView() {		
		mEditText = (EditText)findViewById(R.id.input_username_edittext);	
		clearUserNameImg = (ImageView) findViewById(R.id.iv_clear_username);
		clearUserNameImg.setOnClickListener(this);
		mButton = (Button) findViewById(R.id.btn_next);
		mButton.setOnClickListener(this);
		mEditText.addTextChangedListener(mUserNameWatcher);
	}

	private void toNext() {
		
		mButton.setEnabled(false);
		
		//UIHelper.hideKeyboard(this, getCurrentFocus());
			
		String phoneNumber = mEditText.getText().toString().trim();
		/*if (!StringHelper.isValidatePhoneNumber(this, phoneNumber)) {
			mButton.setEnabled(true);
			return ;
		}*/
		
		String url = "";//ApiService.get_user_exist(phoneNumber);
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		//apiClient.get(url, TAG, getResponseHandler(phoneNumber));
	}
	
	private NetworkResponseHandler getResponseHandler(final String phoneNumber) {
		return new NetworkResponseHandler() {

			@Override
			public void onSuccess(JSONObject jsonObject) {
				mButton.setEnabled(true);
				//
			}

			@Override
			public void onFail(VolleyError error) {
				//
				mButton.setEnabled(true);
			}
		};
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next:
			toNext();
			//
			break;
		case R.id.iv_clear_username:
            mEditText.getText().clear();
            mEditText.requestFocus();
            break;
		}
	}
}
