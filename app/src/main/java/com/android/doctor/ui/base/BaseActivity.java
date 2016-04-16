package com.android.doctor.ui.base;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.doctor.app.AppManager;
import com.android.doctor.helper.StringHelper;
import com.android.doctor.R;
import com.android.doctor.ui.widget.EmptyLayout;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;


public class BaseActivity extends AppCompatActivity {
	public static final long MIN_CLICK_DELAY_TIME = 1500;
	
	private long lastClickTime = 0;
	protected EmptyLayout emptyLayout;
	protected ActionBar mActionBar;
	
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//AppManager.getAppManager().addActivity(this);
		setContentLayout();
        ButterKnife.inject(this);
		if (hasActionBar()) {
			setActionBar(R.string.app_name);
		}
		init();
		initView();
		initData();
	}

	protected void setContentLayout() {}

	protected void init() {
	}

	protected void initView() {}

	protected void initData() {}

	protected boolean hasActionBar() {
		return false;
	}

	public void setActionBar(int resId) {
		initActionBar();
		if (resId != 0) {
			((TextView) findViewById(R.id.toolbar_title)).setText(resId);
		}
	}

	public void setActionBar(String t) {
		initActionBar();
		((TextView) findViewById(R.id.toolbar_title)).setText(t);
	}
	private void initActionBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar == null) return;
		setSupportActionBar(toolbar);
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(false);
	}

	public void setActionBarTitle(int resId) {
        if (resId != 0) {
			setActionBarTitle(getString(resId));
        }
    }

    public void setActionBarTitle(String title) {
        if (StringHelper.isEmpty(title)) {
            title = getString(R.string.app_name);
        }
        if (hasActionBar() && mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			//AppManager.getAppManager().finishActivity();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	protected boolean isFastDoubleClick() {
		long curMillis = System.currentTimeMillis();
		if (curMillis - lastClickTime <= MIN_CLICK_DELAY_TIME) {
			return true;
		}
		lastClickTime = curMillis;
		return false;
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
}
