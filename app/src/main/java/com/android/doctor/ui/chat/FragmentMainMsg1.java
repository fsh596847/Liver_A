package com.android.doctor.ui.chat;

import android.os.Bundle;
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
import com.android.doctor.ui.base.BaseFragment;
import com.android.doctor.ui.tabs.MsgTab;

import butterknife.OnClick;

/**
 * Created by Yong on 2016-02-14.
 */
public class FragmentMainMsg1 extends BaseFragment
        implements TabHost.OnTabChangeListener {

    private FragmentTabHost tabHost;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message;
    }

    protected void initView(View view) {
        tabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        tabHost.setup(getActivity(), getChildFragmentManager(), android.R.id.tabcontent);
        tabHost.getTabWidget().setShowDividers(0);
        tabHost.setOnTabChangedListener(this);
        initTabs();
    }

    private void initTabs() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        MsgTab[] tabs = MsgTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MsgTab tab0 = tabs[i];
            TabHost.TabSpec tab = tabHost.newTabSpec(getString(tab0.getResName()));
            View indicator = inflater.inflate(R.layout.chat_tab_item, null);

            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            //Drawable drawable = this.getResources().getDrawable(mallTab.getResIcon());
            //title.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
            String resName = getString(tab0.getResName());
            title.setText(resName);

            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(getActivity());
                }
            });
            Bundle b = new Bundle();
            b.putInt("type", i);

            tabHost.addTab(tab, tab0.getClz(), b);
            //tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.color.white);
            if (i == 0) {
                //title.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.line);
                //tabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.line);
            }
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        final int size = tabHost.getTabWidget().getTabCount();
        for (int i = 0; i < size; i++) {
            View v = tabHost.getTabWidget().getChildAt(i);
            if (i == tabHost.getCurrentTab()) {
                v.setSelected(true);
                //TextView tv = (TextView)v.findViewById(R.id.tab_title);
                //tv.setCompoundDrawablesWithIntrinsicBounds(0,0,0,R.drawable.line);
                //v.setBackgroundResource(R.drawable.line);
            } else {
                v.setSelected(false);
                //v.setBackgroundResource(R.color.white);
            }
        }
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
}
