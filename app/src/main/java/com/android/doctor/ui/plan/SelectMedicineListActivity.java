package com.android.doctor.ui.plan;

import android.annotation.SuppressLint;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;
import com.yuntongxun.kitsdk.utils.LogUtil;

public class SelectMedicineListActivity extends BaseActivity implements
		OnTabChangeListener,
		OnTouchListener,
		OnClickListener {

	private static final String TAG = SelectMedicineListActivity.class.getSimpleName();
	private FragmentTabHost mTabHost;
    private String tabs[] = new String[4];

	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_main_medicine_list);
	}

	protected void initView(){
        setActionBar("");
        mTabHost = (FragmentTabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        if (Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        mTabHost.setOnTabChangedListener(this);
        tabs[0] = "抗病毒";
        tabs[1] = "调节";
        tabs[2] = "保肝";
        tabs[3] = "抗纤维化";
		initTabs();
	}


	private void initTabs() {
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            String t = tabs[i];
            TabSpec tab = mTabHost.newTabSpec(t);
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.textview, null);
            
            TextView title = (TextView) indicator.findViewById(R.id.tv_text);
            title.setText(t);

            tab.setIndicator(indicator);
            tab.setContent(new TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(SelectMedicineListActivity.this);
                }
            });

            if (tab == null) {
                LogUtil.e(LogUtil.getLogUtilsTag(SelectMedicineListActivity.class), "tab is null");
            }
            mTabHost.addTab(tab, FragmentTextList.class, null);
            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
	}


	@Override
	public void onClick(View v) {
		if (isFastDoubleClick()) {
			return;
		}
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
