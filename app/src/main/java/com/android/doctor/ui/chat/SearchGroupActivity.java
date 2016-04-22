package com.android.doctor.ui.chat;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;

/**
 * Created by Yong on 2016/3/26.
 */
public class SearchGroupActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.fragment_search_group);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.search_group);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        FragmentGroupList fg = new FragmentGroupList();
        Bundle b = new Bundle();
        b.putInt(FragmentGroupList.EXTRA_TYPE, FragmentGroupList.LIST_GROUP_TYPE_SEARCH);
        fg.setArguments(b);
        ts.add(R.id.fl_container, fg);
        ts.commitAllowingStateLoss();
    }
}
