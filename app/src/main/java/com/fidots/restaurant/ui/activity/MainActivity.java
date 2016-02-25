package com.fidots.restaurant.ui.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.fidots.restaurant.ui.fragment.FragmentHome;
import com.fidots.restaurant.ui.tabs.MainTab;
import com.fidots.restaurant.ui.widget.MainFragmentTabHost;
import com.fidots.restaurant.R;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
		OnTabChangeListener,
		OnTouchListener,
		OnClickListener {
	
	private static final String TAG = MainActivity.class.getSimpleName();
	private MainFragmentTabHost mTabHost;

	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
	}
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
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
            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);            
            title.setText(getString(mainTab.getResName()));
            
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
}
