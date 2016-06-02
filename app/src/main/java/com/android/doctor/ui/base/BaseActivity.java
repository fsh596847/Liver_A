package com.android.doctor.ui.base;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.doctor.app.AppManager;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.StringHelper;
import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.RespEntity;
import com.android.doctor.ui.widget.EmptyLayout;
import com.android.doctor.ui.widget.progress_dialog.ProcessDialog;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;


public class BaseActivity extends AppCompatActivity {
	public static final long MIN_CLICK_DELAY_TIME = 1500;
	
	private long lastClickTime = 0;
	protected EmptyLayout emptyLayout;
	protected ActionBar mActionBar;
    protected ProcessDialog mProgressDialog;

	@Override
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
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

	protected void initView() {
        mProgressDialog = DialogUtils.getProgressDialog(this, ProcessDialog.Style.SPIN_INDETERMINATE, "请稍后");
		emptyLayout = (EmptyLayout) findViewById(R.id.empty_layout);
    }

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

    protected void setMaskLayout(int visibility, int type, String msg) {
        if (emptyLayout != null) {
            emptyLayout.setVisibility(visibility);
            emptyLayout.setErrorType(type);
            emptyLayout.setErrorMessage(msg);
        }
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
	public void onBackPressed() {
		AppManager.getAppManager().finishActivity();
		super.onBackPressed();
	}

	protected boolean isFastDoubleClick() {
		long curMillis = System.currentTimeMillis();
		if (curMillis - lastClickTime <= MIN_CLICK_DELAY_TIME) {
			return true;
		}
		lastClickTime = curMillis;
		return false;
	}

    public void dismissProcessDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void showProcessDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.show();
        }
    }

	protected void onProResult(RespEntity resp) {
		dismissProcessDialog();
		if (resp != null) {
			String text = resp.getError_msg();
			UIHelper.showToast(text);
			if (resp.getError_code() == 0) {
				AppManager.getAppManager().finishActivity();
			}
		}
	}

    protected void onLoadResult(RespEntity resp) {
        String text = resp.getError_msg();
        if (resp.getError_code() == 0) {
            setMaskLayout(View.GONE, EmptyLayout.HIDE_LAYOUT, "");
            return;
        }
        setMaskLayout(View.VISIBLE, EmptyLayout.SHOW_LAYOUT, text);
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
