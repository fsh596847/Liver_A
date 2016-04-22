package com.android.doctor.ui.chat;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.MenuHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.MedicClassify;
import com.android.doctor.ui.base.BaseFragment;
import com.android.doctor.ui.plan.FragmentMedicInfoList;
import com.android.doctor.ui.tabs.MsgTab;
import com.android.doctor.ui.widget.PageEnableViewPager;
import com.yuntongxun.kitsdk.fragment.ConversationListFragment;

import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016-02-14.
 */
public class FragmentMainMsg extends BaseFragment {

    private static final String tabs[] = {"患者", "群", "医生", "通知"};
    private FragmentTabHost tabHost;
    @InjectView(R.id.viewPager)
    protected PageEnableViewPager mViewPager;
    @InjectView(R.id.tabLayout)
    protected TabLayout mTabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    protected void initView(View view) {
        mViewPager.setPagingEnabled(false);
        initTabs();
    }


    private void initTabs() {
        for (int i = 0; i < tabs.length; ++i) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tabs[i]));
        }
        ChatFragmentPagerAdapter adapter = new ChatFragmentPagerAdapter(getChildFragmentManager(), getActivity());
        adapter.setmData(Arrays.asList(tabs));
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }



    @OnClick(R.id.imgbtn_selec_contact)
    protected void onSelectContact() {
        UIHelper.showtAty(getActivity(), ContactActivity.class);
    }

    @OnClick(R.id.imgbtn_popmenu)
    protected void onPopMenu(View view) {
        MenuHelper.displayPopupMenu(getActivity(), R.menu.msg_menu, view,
                new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_new_conversation:
                        UIHelper.showtAty(getActivity(), ContactActivity.class);
                        break;
                    case R.id.action_send_group_msg:
                        UIHelper.showtAty(getActivity(), ICreatedGroupActivity.class);
                        break;
                    case R.id.action_add_contact:
                        UIHelper.showtAty(getActivity(), AddContactActivity.class);
                        break;
                }
                return false;
            }
        });
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
            String e = mData.get(position);
            return ConversationListFragment.newInstance(position);
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
