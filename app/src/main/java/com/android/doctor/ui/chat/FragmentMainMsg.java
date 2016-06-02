package com.android.doctor.ui.chat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.ECSDKCoreHelper;
import com.android.doctor.helper.MenuHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.base.BaseFragment;
import com.android.doctor.ui.chat.custome.ConversationDataPool;
import com.android.doctor.ui.chat.custome.CustomeConversationListFragment;
import com.android.doctor.ui.widget.PageEnableViewPager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.kitsdk.db.IMessageSqlManager;
import com.yuntongxun.kitsdk.utils.ECNotificationManager;
import com.yuntongxun.kitsdk.view.NetWarnBannerView;

import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016-02-14.
 */
public class FragmentMainMsg extends BaseFragment {
    public static final String TAG = FragmentMainMsg.class.getSimpleName();

    private static final String tabs[] = {"患者", "群", "医生", "通知"};
    private static final TextView tabsView[] = new TextView[tabs.length];

    private FragmentTabHost tabHost;
    @InjectView(R.id.viewPager)
    protected PageEnableViewPager mViewPager;
    @InjectView(R.id.tabLayout)
    protected TabLayout mTabLayout;
    @InjectView(R.id.flayout)
    protected FrameLayout mFlBarView;

    private NetWarnBannerView mBannerView;
    private ChatFragmentPagerAdapter mFragmentAdapter;

    private BroadcastReceiver mDoLoad = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            OnUpdateMsgUnreadCounts();
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    protected void initView(View view) {
        mViewPager.setPagingEnabled(false);
        initNetWarnBanner();
        initTabs();
    }

    private void initNetWarnBanner() {
        if (mBannerView != null) {
            mFlBarView.removeView(mBannerView);
        }
        mBannerView = new NetWarnBannerView(getActivity());
        mBannerView.setVisibility(View.GONE);
        mFlBarView.addView(mBannerView);
        mBannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reTryConnect();
            }
        });
    }

    private void reTryConnect() {
        ECDevice.ECConnectState connectState = ECSDKCoreHelper.getConnectState();
        if(connectState == null || connectState == ECDevice.ECConnectState.CONNECT_FAILED) {
            ECSDKCoreHelper.getInstance().init(getActivity());
        }
    }

    public void updateConnectState(ECDevice.ECConnectState connect) {
        if (!isAdded()) {
            return;
        }
        if (connect == ECDevice.ECConnectState.CONNECT_FAILED) {
            mBannerView
                    .setNetWarnText(getString(R.string.connect_server_error));
            mBannerView.reconnect(false);
        } else if (connect == ECDevice.ECConnectState.CONNECT_SUCCESS) {
            mBannerView.hideWarnBannerView();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ConversationDataPool.getInstance().notifyChange();
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IMessageSqlManager.registerMsgObserver(ConversationDataPool.getInstance());
        ConversationDataPool.getInstance().notifyChange();
        //AppAsyncTask.execute(runnable);
        //
        OnUpdateMsgUnreadCounts();
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            IMessageSqlManager.unregisterMsgObserver(ConversationDataPool.getInstance());
        } catch (Exception e) {
            Log.e(AppConfig.TAG, "Exception" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void initTabs() {
        mFragmentAdapter = new ChatFragmentPagerAdapter(getChildFragmentManager(), getActivity());
        mFragmentAdapter.setmData(Arrays.asList(tabs));
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(tabs.length);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
        //mTabLayout.removeAllTabs();
        for (int i = 0; i < tabs.length; ++i) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            View indicator = LayoutInflater.from(getActivity()).inflate(R.layout.chat_tab_item, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            title.setText(tabs[i]);
            if (i == 0) {
                indicator.setSelected(true);
            }

            tab.setCustomView(indicator);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        OnUpdateMsgUnreadCounts();
    }

    @OnClick(R.id.imgbtn_selec_contact)
    protected void onSelectContact() {
        ContactActivity.startAty(getActivity(), ContactActivity.REQUEST_CODE_FOR_NEW_CONVERSATION);
    }

    @OnClick(R.id.imgbtn_popmenu)
    protected void onPopMenu(View view) {
        MenuHelper.showPopupMenu(getActivity(), R.menu.msg_menu, view,
                new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_new_conversation:
                        ContactActivity.startAty(getActivity(), ContactActivity.REQUEST_CODE_FOR_NEW_CONVERSATION);
                        break;
                    case R.id.action_send_group_msg:
                        UIHelper.showtAty(getActivity(), MineGroupActivity.class);
                        break;
                    case R.id.action_add_contact:
                        UIHelper.showtAty(getActivity(), AddContactActivity.class);
                        break;
                }
                return false;
            }
        });
    }


    public void OnUpdateMsgUnreadCounts() {
        if (mFragmentAdapter == null) return;
        for (int i = 0; i < mFragmentAdapter.getCount(); ++i) {
            int cnt = ConversationDataPool.getConversationUnreadCount(i);
            updateUnreadCountTips(i, cnt);
        }
    }

    private void updateUnreadCountTips(int tabIndex, final int unread) {
        if (0 <= tabIndex && tabIndex < tabs.length) {
            TabLayout.Tab tab = mTabLayout.getTabAt(tabIndex);
            if (tab != null) {
                View view = tab.getCustomView();
                if (view != null) {
                    RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.rl_root);
                    final TextView tv = (TextView) rl.findViewById(R.id.tv_unread_msg_number);
                    tv.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(String.valueOf(unread));
                            tv.setVisibility(unread == 0 ? View.GONE : View.VISIBLE);
                        }
                    });
                } else {
                    Log.d(TAG, "tabview is null");
                }
            }
        }
    }

    class ChatFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<String> mData;
        private Context context;

        public ChatFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            if (mData == null) return null;
            return CustomeConversationListFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mData == null ? "" : mData.get(position) == null ? null : mData.get(position);
        }

        public void setmData(List<String> mData) {
            this.mData = mData;
        }
    }
}
