package com.fidots.restaurant.ui.activity;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.VolleyError;
import com.fidots.restaurant.R;
import com.fidots.restaurant.api.ApiClient;
import com.fidots.restaurant.api.NetworkResponseHandler;
import com.fidots.restaurant.app.AppContext;
import com.fidots.restaurant.app.PrefManager;
import com.fidots.restaurant.helper.DeviceHelper;
import com.fidots.restaurant.helper.StringHelper;
import com.fidots.restaurant.helper.VolleyErrorHelper;
import com.fidots.restaurant.models.User;
import com.fidots.restaurant.ui.widget.SimpleTextWatcher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
	private static final String TAG = LoginActivity.class.getSimpleName();	
	private Button loginButton;
	private EditText userNameEditText;
	private EditText passwdEditText;
	private ImageView clearUserNameImg;
	private ImageView clearPasswdImg;
	//private TextInputLayout txInputLayout1;
	//private TextInputLayout txInputLayout2;
	private TextView txQuickRegist;
	private TextView txForgetPasswd;
	private String passwd;
	
	private final TextWatcher mUserNameWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            clearUserNameImg.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    };
    
    private final TextWatcher mPassswordWatcher = new SimpleTextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count) {
            clearPasswdImg.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE
                            : View.VISIBLE);
        }
    };
    
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentLayout();
    	setActionBar();
    	initView();
    	initData();
    	setupListener();
    };
    
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
    
	protected int getLayoutId() {
		return R.layout.activity_login;
	}
	
	protected void setActionBar() {
		ActionBar mActionBar = getSupportActionBar();
		if (mActionBar != null) {
			mActionBar.setDisplayHomeAsUpEnabled(true);
		}
	}
	
	protected void initView() {
		loginButton = (Button)findViewById(R.id.login_button);
		userNameEditText = (EditText) findViewById(R.id.login_username);
		passwdEditText = (EditText) findViewById(R.id.login_passwd);
		txQuickRegist = (TextView)findViewById(R.id.tx_quick_regist);
		txForgetPasswd = (TextView)findViewById(R.id.tx_forget_passwd);
		clearUserNameImg = (ImageView) findViewById(R.id.iv_clear_username);
		clearPasswdImg = (ImageView) findViewById(R.id.iv_clear_passwd);
		//txInputLayout1 = (TextInputLayout) view.findViewById(R.id.text_inputlayout1);
		//txInputLayout2 = (TextInputLayout) view.findViewById(R.id.text_inputlayout2);
				
		//txInputLayout1.setEnabled(true);
		//txInputLayout2.setEnabled(true);
		//txInputLayout1.setHint(getResources().getString(R.string.phone_number));
		//txInputLayout2.setHint(getResources().getString(R.string.passwd));
		//Typeface typeFace =Typeface.createFromAsset(mActivity.getAssets(),"fonts/roboto_bold.ttf");
		//mLoginButton.setTypeface(typeFace);
	}
	
	protected void initData() {
		String userPhone = PrefManager.getUserPhone(this);
		if (userPhone != null) {
			userNameEditText.setText(userPhone);
		}		
	}
	
	private void setupListener() {
		txQuickRegist.setOnClickListener(this);
		txForgetPasswd.setOnClickListener(this);
		clearUserNameImg.setOnClickListener(this);
		clearPasswdImg.setOnClickListener(this);
		userNameEditText.addTextChangedListener(mUserNameWatcher);
		passwdEditText.addTextChangedListener(mPassswordWatcher);
		loginButton.setOnClickListener(this);
	}
	
	
	private boolean preLogin(String userName, String passwd) {	
		if (!DeviceHelper.isNetworkConnected(this)) {
			//UIHelper.showToast(this, R.string.no_network_connection);
			return false;
		}
		
		if (StringHelper.isEmpty(userName)) {
			//UIHelper.showToast(this, R.string.error_username_is_empty);
			return false;
		}
		
		return true; //StringHelper.isValidatePasswd(this, passwd);
	}
	
	private void isUserExist() {		
		
		loginButton.setEnabled(false);
		
		//UIHelper.hideKeyboard(this, getCurrentFocus());
		
		String strUserName = userNameEditText.getText().toString();
		passwd = passwdEditText.getText().toString();
		if (!preLogin(strUserName, passwd)) {
			loginButton.setEnabled(true);
			return;
		}
		
		final String userName = strUserName.trim();
		String url = ""; //ApiService.get_user_exist(userName);
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		apiClient.get(url, TAG, new NetworkResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				onLogin(userName, passwd);
			}

			@Override
			public void onFail(VolleyError error) {
				//UIHelper.showToast(LoginActivity.this, R.string.error_username_is_wrong);
				loginButton.setEnabled(true);
			}
		});	
	}	
	
	
	private void onLogin(String userName, String passwd) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("username", userName);
		params.put("password", passwd);
		params.put("role", "CONSUMER");
		
		JSONObject jsonObject = new JSONObject();
		JSONObject internal = new JSONObject(params);
		try {
			jsonObject.put("auth", internal);
		} catch (JSONException e) {
			e.printStackTrace();
			return;
		}
		
		String url = "";//ApiService.post_login();
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		apiClient.post(url, jsonObject, TAG, getResponseHandler());	
	}
	
	private NetworkResponseHandler getResponseHandler() {
		return new NetworkResponseHandler() {

			@Override
			public void onSuccess(JSONObject jsonObject) {
				/*Gson gson = new Gson();
				User user = null;
				try {
					System.out.println("" + jsonObject.toString());
					user = gson.fromJson(jsonObject.getString("token_info"), User.class);
					PrefManager.putUserInfo(LoginActivity.this, user);	
					AppContext.context().initLogin();
				} catch (JsonSyntaxException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				onLoginFinished(true);	*/
			}

			@Override
			public void onFail(VolleyError error) {
				loginButton.setEnabled(true);
				int res = VolleyErrorHelper.getErrorResID(error, LoginActivity.this);
				//UIHelper.showToast(LoginActivity.this, res);
			}
		};
	}
	
	private void onLoginFinished(boolean isSucceed) {
		
		/*Activity activity = AppManager.getAppManager().currentActivity();
		if (activity != null) {
			if (activity.getClass().equals(MainActivity.class)) {
				((MainActivity)activity).onLoginFinished(isSucceed);	
			} 
		}*/
		finish();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		onLoginFinished(false);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			isUserExist();
			break;
		case R.id.tx_quick_regist:
			//UIHelper.showRegistPhone(this);
			break;
		case R.id.tx_forget_passwd:
			//UIHelper.showForgetPasswd(this);
			break;
		case R.id.iv_clear_username:
            userNameEditText.getText().clear();
            userNameEditText.requestFocus();
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
