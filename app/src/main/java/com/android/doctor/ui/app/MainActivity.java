package com.android.doctor.ui.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.android.doctor.model.Constants;
import com.android.doctor.model.MessageEvent;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.chat.FragmentMainMsg;
import com.android.doctor.ui.chat.custome.CustomeConversationListFragment;
import com.android.doctor.ui.chat.provider.CustomUIAndActionManager;
import com.android.doctor.ui.tabs.MainTab;
import com.android.doctor.app.DataCacheManager;
import com.android.doctor.ui.widget.MainFragmentTabHost;
import com.android.doctor.R;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.kitsdk.db.IMessageSqlManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
		OnTabChangeListener,
		OnTouchListener,
		OnClickListener,
        CustomeConversationListFragment.OnUpdateMsgUnreadCountsListener {
	
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
        DataCacheManager.getInstance().onLoadContact(Constants.USER_TYPE_DOCTOR);
        DataCacheManager.getInstance().onLoadContact(Constants.USER_TYPE_PATIENT);
        DataCacheManager.getInstance().onLoadGroupContact();
        DataCacheManager.getInstance().onLoadCollectArticles();
        DataCacheManager.getInstance().onLoadSuggClassList();
        //if (ECSDKCoreHelper.getConnectState() != ECDevice.ECConnectState.CONNECT_SUCCESS) {
            ECSDKCoreHelper.getInstance().init(this);
            CustomUIAndActionManager.initCustomUI();
        //}
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event){
        if (event.message == Constants.EVENT_MSG_UPDATE_CONTACT_GROUP) {
            DataCacheManager.getInstance().onLoadGroupContact();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
        mTabHost.setCurrentTab(1);
        mTabHost.setCurrentTab(0);
	}
	
	protected void setActionBar() {}

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
	protected void onResume() {
		super.onResume();
	}


	@Override
	public void onClick(View v) {
		if (isFastDoubleClick()) {
			return;
		}
	}

    /**
     * 网络注册状态改变
     *
     * @param connect
     */
    public void onNetWorkNotify(ECDevice.ECConnectState connect) {
        Fragment tabView = getCurrentFragment();
        if (tabView instanceof FragmentMainMsg && tabView.isAdded()) {
            ((FragmentMainMsg) tabView).updateConnectState(connect);
        }
    }

    public void handlerKickOff(String kickoffText) {
        if (isFinishing()) {
            return;
        }
        Fragment tabView = getCurrentFragment();
        if (tabView instanceof FragmentMainMsg && tabView.isAdded()) {
            AlertDialogActivity.startAty(this, kickoffText);
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
        //System.out.println("MainActivity.onTabChanged()");
        supportInvalidateOptionsMenu();
	}

    @Override
    public void OnUpdateMsgUnreadCounts() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(getString(R.string.main_tab_info));
        if (fragment != null && fragment instanceof FragmentMainMsg) {
            FragmentMainMsg fmsg = (FragmentMainMsg) fragment;
            fmsg.OnUpdateMsgUnreadCounts();
        }
        updateMainUnreadCountTips(1);
    }

    public void updateMainUnreadCountTips(int tabIndex) {
        final int unread = IMessageSqlManager.qureyAllSessionUnreadCount();
        MainTab[] tabs = MainTab.values();
        if (0 <= tabIndex && tabIndex < tabs.length) {
            View tab = mTabHost.getTabWidget().getChildAt(tabIndex);
            final TextView tv = (TextView) tab.findViewById(R.id.tv_unread_count);
            tab.post(new Runnable() {
                @Override
                public void run() {
                    tv.setText("" + unread);
                    tv.setVisibility(unread == 0 ? View.GONE : View.VISIBLE);
                }
            });
        }
    }
}
