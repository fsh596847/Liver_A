package com.android.doctor.ui.activity;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.android.doctor.helper.ECSDKCoreHelper;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.tabs.MainTab;
import com.android.doctor.ui.widget.MainFragmentTabHost;
import com.android.doctor.R;
import com.yuntongxun.kitsdk.ECDeviceKit;
import com.yuntongxun.kitsdk.fragment.ConversationListFragment;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
		OnTabChangeListener,
		OnTouchListener,
		OnClickListener,
        ConversationListFragment.OnUpdateMsgUnreadCountsListener {
	
	private static final String TAG = MainActivity.class.getSimpleName();
	private MainFragmentTabHost mTabHost;
    private TextView mTvUnReadMsgCount;

	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
	}

	protected void initView(){			
        mTabHost = (MainFragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        mTabHost.setOnTabChangedListener(this);
		initTabs();
	}

    @Override
    protected void initData() {
        ECDeviceKit.init("", getApplicationContext(), ECSDKCoreHelper.getInstance());
    }

    @Override
	protected boolean hasActionBar() {
		return false;
	}

	private void initTabs() {
        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];
            TabSpec tab = mTabHost.newTabSpec(getString(mainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.tab_indicator, null);
            
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            ImageView img = (ImageView) indicator.findViewById(R.id.img_tab);
            img.setImageResource(mainTab.getResIcon());
            title.setText(getString(mainTab.getResName()));
            if (i == 1) {
                mTvUnReadMsgCount = (TextView) indicator.findViewById(R.id.tv_unread_count);
                mTvUnReadMsgCount.setVisibility(View.VISIBLE);
            }
            tab.setIndicator(indicator);
            tab.setContent(new TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            
            mTabHost.addTab(tab, mainTab.getClz(), null);
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
	}
	
	protected void setActionBar() {}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	

	@Override
	public void onClick(View v) {
		if (isFastDoubleClick()) {
			return;
		}
		/*switch (v.getId()) {
		case R.id.tx_city:
			Intent intent = new Intent(this, CitySwitchActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.anim_bottom_in, R.anim.stay);
			break;
		default:
			break;
		}*/
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		super.onTouchEvent(event);
        boolean consumed = false;
        // use getTabHost().getCurrentTabView to decide if the current tab is
        // touched again
        if (event.getAction() == MotionEvent.ACTION_DOWN
                && v.equals(mTabHost.getCurrentTabView())) {

            Fragment currentFragment = getCurrentFragment();
            /*if (currentFragment != null
                    && currentFragment instanceof OnTabReselectListener) {
                OnTabReselectListener listener = (OnTabReselectListener) currentFragment;
                listener.onTabReselect();
                consumed = true;
            }*/
        }
        return consumed;
	}
	
	private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

	@Override
	public void onTabChanged(String tabId) {
		final int size = mTabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = mTabHost.getTabWidget().getChildAt(i);
            if (i == mTabHost.getCurrentTab()) {
                v.setSelected(true);                
            } else {
                v.setSelected(false);
            }
        }
        invalidateOptionsMenu();
        System.out.println("MainActivity.onTabChanged()");
        supportInvalidateOptionsMenu();
	}

    @Override
    public void OnUpdateMsgUnreadCounts() {

    }
}
