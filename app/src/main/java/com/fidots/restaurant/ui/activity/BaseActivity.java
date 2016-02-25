package com.fidots.restaurant.ui.activity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.fidots.restaurant.R;
import com.fidots.restaurant.helper.StringHelper;
import com.fidots.restaurant.ui.widget.EmptyLayout;


public class BaseActivity extends AppCompatActivity {
	public static final long MIN_CLICK_DELAY_TIME = 1500;
	
	private long lastClickTime = 0;
	protected EmptyLayout emptyLayout;
	protected ActionBar mActionBar;
	
	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentLayout();
		
		if (hasActionBar()) {
			setActionBar();
		}
		init();
	}

	protected void setContentLayout() {}

	protected void init() {
		initView();
		initData();
	}

	protected void initView() {}

	protected void initData() {}

	protected boolean hasActionBar() {
		return false;
	}

	protected void setActionBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mActionBar = getSupportActionBar();
		if (mActionBar != null) {
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setDisplayShowCustomEnabled(true);
		}
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

	protected void setActionBar(String title) {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mActionBar = getSupportActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(false);
		((TextView)findViewById(R.id.toolbar_title)).setText(getResources().getString(R.string.app_name));
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
	
	
	protected boolean isFastDoubleClick() {
		long curMillis = System.currentTimeMillis();
		if (curMillis - lastClickTime <= MIN_CLICK_DELAY_TIME) {
			return true;
		}
		lastClickTime = curMillis;
		return false;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}

}
