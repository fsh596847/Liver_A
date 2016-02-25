package com.fidots.restaurant.ui.fragment.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.widget.EmptyLayout;

import butterknife.ButterKnife;

/**
 */
public abstract class BaseFragment extends Fragment
        implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
	
	private static final String TAG = BaseFragment.class.getSimpleName();
	
    public static final int PAGE_STATE_NONE = 0;
    
    public static final int PAGE_STATE_REFRESH = 1;
    
    public static final int PAGE_STATE_LOADMORE = 2;
        
    public static final long MIN_CLICK_DELAY_TIME = 1000;
    
    public int mState = PAGE_STATE_NONE;
    
    protected LayoutInflater mInflater;
    
    protected SwipeRefreshLayout swipeLayout;
    
    protected EmptyLayout emptyLayout;
        
    //protected Cache cache;
    
	private long lastClickTime;

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        int resId = getLayoutId();
        if (resId != 0) {
            View view = inflater.inflate(resId, container, false);
            ButterKnife.inject(this, view);
            return view;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {   	
    	super.onViewCreated(view, savedInstanceState);
        initView(view);
    }      
	
	
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
        System.out.println("BaseFragment.onDestroy()");
    }

    protected int getLayoutId() {
        return 0;
    }

    public boolean onBackPressed() {
        return false;
    }
    
	/***
	*/
    protected long getAutoRefreshTime() {
        return 3 * 60 * 60;
    }
	 
    protected void setRefreshingState(final boolean bRefresh) {
        if (swipeLayout != null) {
        	swipeLayout.post(new Runnable() {				
				@Override
				public void run() {
					swipeLayout.setRefreshing(bRefresh);
	        		swipeLayout.setEnabled(!bRefresh); 
				}
			});
        }
    }
    	
    protected void setLoadingFinished() {
    	if (emptyLayout != null) {
			emptyLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
    	}
    	mState = PAGE_STATE_NONE;
    }
    
    
    protected void setupSwipeRefresh(View view) {
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
	}
	
	
	protected void setEmptyLayout(int visible) {
    	if (emptyLayout != null) {
			emptyLayout.setErrorType(visible);
    	}
    }
	
    @Override
    public void onClick(View v) {}
	
    @Override
    public void onRefresh() {}
}
