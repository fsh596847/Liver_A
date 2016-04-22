package com.android.doctor.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.ui.adapter.ViewPagerFragmentAdapter;
import com.android.doctor.ui.widget.EmptyLayout;
import com.android.doctor.ui.widget.PagerSlidingTabStrip;

/**
 *
 * 
 */
public abstract class BaseViewPagerActivity extends BaseActivity {
	
	private static final String TAG = BaseViewPagerActivity.class.getSimpleName();
	
	public static final String VIEW_TAG = "view_tag";
	
    protected PagerSlidingTabStrip mTabStrip;
    
    protected ViewPager mViewPager;
    
    protected ViewPagerFragmentAdapter mTabsAdapter;
    
    protected EmptyLayout mErrorLayout;
    
    private BroadcastReceiver updateView = new BroadcastReceiver() {		
		@Override
		public void onReceive(Context context, Intent intent) {
			String tag = intent.getStringExtra(VIEW_TAG);
			updateView(tag);
			Log.i(TAG, "Receive() updateView. tag: " + tag);
		}
	};
	
	/*protected void registerReceivers() {
		 LocalBroadcastManager.getInstance(this)
		 					.registerReceiver(updateView, new IntentFilter(Constants.SYNC()));
	}
	
	protected void unregisterReceivers() {
		LocalBroadcastManager.getInstance(this).unregisterReceiver(updateView);
	}*/
    
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}
    
	@Override
	protected void setContentLayout() {
		setContentView(getLayoutId());
	}
	
    protected int getLayoutId() {
    	return R.layout.base_viewpage_fragment;
    }
    
    protected void initView() {
        mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.pager_tabstrip);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        mErrorLayout = (EmptyLayout) findViewById(R.id.error_layout);

        mTabsAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(),
                mTabStrip, mViewPager);
        setScreenPageLimit();
        
        onSetupTabAdapter(mTabsAdapter);
    }
    
    protected void updateView(String tag) {
    	mTabsAdapter.notifyDataSetChanged();
		View view = mViewPager.findViewWithTag(tag);
		if (view != null) {
			Log.i(TAG, "findViewWithTag. ");
			view.invalidate();
		}
	}
    
    protected void setScreenPageLimit() {
    }

    protected abstract void onSetupTabAdapter(ViewPagerFragmentAdapter adapter);
        
}