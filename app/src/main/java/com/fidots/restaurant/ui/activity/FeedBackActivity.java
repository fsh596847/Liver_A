package com.fidots.restaurant.ui.activity;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.VolleyError;
import com.fidots.restaurant.R;
import com.fidots.restaurant.api.ApiClient;
import com.fidots.restaurant.api.NetworkResponseHandler;
import com.fidots.restaurant.app.AppContext;
import com.fidots.restaurant.app.PrefManager;
import com.fidots.restaurant.helper.StringHelper;
import com.fidots.restaurant.helper.VolleyErrorHelper;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

public class FeedBackActivity extends BaseActivity {
	private static final String TAG = FeedBackActivity.class.getSimpleName();
	
	private EditText contentEditText;
	private EditText contactEditText;
	private MenuItem doneMenuItem;
	private String userPhone;

    @Override
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
    
    protected int getLayoutId() {
    	return R.layout.activity_feedback;
    }
    
	protected void initView() {
		contentEditText = (EditText) findViewById(R.id.edtTxt_feedback);
		contactEditText = (EditText) findViewById(R.id.edtTxt_contact_info);
		userPhone = PrefManager.getUserPhone(this);
		String str = StringHelper.replace(userPhone, 3, 4, '*');
		contactEditText.setText(str);
		
		contentEditText.addTextChangedListener(new TextWatcher() {			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}		
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
			
			@Override
			public void afterTextChanged(Editable s) {
				updateMenuItem(s.length() > 0);
				//invalidateOptionsMenu();				
			}
		});
	}
	
	private void updateMenuItem(boolean enable) {
		if (enable) {
			//doneMenuItem.setIcon(R.drawable.ic_done_white_24dp);
			//doneMenuItem.setEnabled(true);
		} else {
			//doneMenuItem.setIcon(R.drawable.ic_done_red32dp);
			//doneMenuItem.setEnabled(false);
		}
	}
	
	 @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.menu_action_done, menu);
		//doneMenuItem = menu.findItem(R.id.action_done);
		updateMenuItem(false);
		return super.onCreateOptionsMenu(menu);
	}   
	 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		//case R.id.action_done:
		//	commitFeedback();
		//	break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void commitFeedback() {
		updateMenuItem(false);
		String url = "";//ApiService.post_user_feedback();
		JSONObject jsonWrapper = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("content", contentEditText.getText().toString());		
			jsonObject.put("contact", userPhone);
			jsonObject.put("role", "STORE");			
			jsonWrapper.put("feedback", jsonObject);
		} catch (JSONException e) {			
			e.printStackTrace();
		}
		ApiClient apiClient = ApiClient.getClient((AppContext) getApplication());
		//apiClient.post(url, jsonWrapper, TAG, getRespHandler());
	}

	private NetworkResponseHandler getRespHandler() {
		
		return new NetworkResponseHandler() {				
			@Override
			public void onSuccess(JSONObject jsonObject) {
				//UIHelper.finishAfterMsgDismiss(FeedBackActivity.this, R.string.success_to_feedback);
			}

			@Override
			public void onFail(VolleyError error) {
				updateMenuItem(true);			
				if (VolleyErrorHelper.isAuthFailure(error)) {
					//UIHelper.reLogin(FeedBackActivity.this);
					return;
				}
			}
		};
	}
}
