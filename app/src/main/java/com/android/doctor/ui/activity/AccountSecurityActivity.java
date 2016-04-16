package com.android.doctor.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.android.doctor.app.AppContext;
import com.android.doctor.R;

public class AccountSecurityActivity extends AppCompatActivity implements OnClickListener{

	private RelativeLayout changePhoneLayout;
	private RelativeLayout changePasswdLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account_security);
		setActionBar();
		initView();
	}

	private void initView() {
		changePasswdLayout = (RelativeLayout) findViewById(R.id.rl_change_passwd);
		changePhoneLayout.setOnClickListener(this);
		changePasswdLayout.setOnClickListener(this);
	}

	private void setActionBar() {
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View v) {
		if (!AppContext.context().isLogin())	{
			//UIHelper.showLogin(this);
			return;
		}
		switch (v.getId()) {
		case R.id.rl_change_passwd:
			//UIHelper.showModifyPasswd(this);
			break;
		default:
			break;
		}
	}
	
	@Override
    public void onBackPressed() {
        super.onBackPressed();
        //overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
}
