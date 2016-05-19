package com.android.doctor.ui.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.doctor.R;
import com.android.doctor.ui.widget.EmptyLayout;
import com.yuntongxun.kitsdk.utils.LogUtil;

import butterknife.ButterKnife;

/**
 */
public abstract class BaseFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener{
	
	private static final String TAG = BaseFragment.class.getSimpleName();
    public static final int PAGE_STATE_NONE = 0;
    public static final int PAGE_STATE_REFRESH = 1;
    public static final int PAGE_STATE_LOADMORE = 2;
    public static final long MIN_CLICK_DELAY_TIME = 1000;
    protected int mState = PAGE_STATE_NONE;
    protected LayoutInflater mInflater;
    protected SwipeRefreshLayout swipeLayout;
    protected EmptyLayout emptyLayout;
	private long lastClickTime;
    protected View mRootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (mRootView == null) {
            int resId = getLayoutId();
            if (resId != 0) {
                mRootView = inflater.inflate(resId, container, false);
                ButterKnife.inject(this, mRootView);
                init();
                initView(mRootView);
                initData();
            }
        }
        return mRootView;
    }
	

    protected void init() {}

	protected void initData() {
	}
	
    public void onStart() {
    	super.onStart();   	
    };
    
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRootView = null;
        System.gc();
        System.out.println("BaseFragment.onDestroy()");
    }

    protected int getLayoutId() {
        return 0;
    }


    protected void setRefreshState(final boolean bRefresh) {
        if (swipeLayout != null) {
            swipeLayout.setRefreshing(bRefresh);
            swipeLayout.setEnabled(!bRefresh);
        }
    }

    protected void setLoaded() {
    	if (emptyLayout != null) {
			emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
    	}
    	mState = PAGE_STATE_NONE;
    }
    
    
    protected void setupSwipeRefresh() {
    	//swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
    	if (swipeLayout != null) {
	    	//swipeLayout.setColorSchemeColors(android.R.color.holo_green_light,
	    	//		android.R.color.holo_orange_light,
	        //        android.R.color.holo_red_light);
	    	/*swipeLayout.setProgressViewOffset(false,
	    			0,
	                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
	                24, getResources().getDisplayMetrics()));*/
    		swipeLayout.setColorSchemeResources(android.R.color.holo_green_light,
	    			android.R.color.holo_orange_light,
	                android.R.color.holo_red_light);
	    	swipeLayout.setOnRefreshListener(this);
    	}
    }
    
	protected boolean isFastDoubleClick() {
		long curMillis = System.currentTimeMillis();
		if (curMillis - lastClickTime <= MIN_CLICK_DELAY_TIME) {
			return true;
		}
		lastClickTime = curMillis;
		return false;
	}
	
	protected void initView(View view) {
		emptyLayout = (EmptyLayout) view.findViewById(R.id.empty_layout);
        //swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        setupSwipeRefresh();
	}
	
	
	protected void setMaskLayout(int visibility, int type, String msg) {
    	if (emptyLayout != null) {
            emptyLayout.setVisibility(visibility);
            emptyLayout.setErrorType(type);
            emptyLayout.setErrorMessage(msg);
    	}
    }

    public void setActionBar(String text) {
        Activity aty = getActivity();
        if (aty == null) {
            LogUtil.d(LogUtil.getLogUtilsTag(BaseFragment.class), "aty == null");
            return;
        }
        if (aty.isFinishing()) {
            LogUtil.d(LogUtil.getLogUtilsTag(BaseFragment.class), "aty.isFinishing");
            return;
        }
        if (aty instanceof BaseActivity) {
            ((BaseActivity)aty).setActionBar(text);
        }
    }

    @Override
    public void onRefresh() {}
}
