package com.android.doctor.ui.widget;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

/**
 * tabhost
 * @author
 * @version 
 * 
 */

public class MainFragmentTabHost extends FragmentTabHost {
	
	private String mCurrentTag;
	
	private String mNoTabChangedTag;
	
	public MainFragmentTabHost(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public void onTabChanged(String tag) {
		
		if (tag.equals(mNoTabChangedTag)) {
			setCurrentTabByTag(mCurrentTag);
		} else {
			super.onTabChanged(tag);
			mCurrentTag = tag;
		}
	}
	
	public void setNoTabChangedTag(String tag) {
		this.mNoTabChangedTag = tag;
	}
}
