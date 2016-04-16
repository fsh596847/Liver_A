package com.android.doctor.ui.message;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/3/26.
 */
public class ICreatedGroupActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_icreated_group);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.group);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        FragmentGroupList fg = new FragmentGroupList();
        Bundle b = new Bundle();
        b.putInt(FragmentGroupList.EXTRA_TYPE, FragmentGroupList.LIST_GROUP_TYPE_NORMAL);
        fg.setArguments(b);
        ts.add(R.id.fl_container, fg);
        ts.commitAllowingStateLoss();
    }

    /**搜索群*/
    @OnClick(R.id.imgbtn_search)
    protected void onSearchGroup() {
        UIHelper.showtAty(this, SearchGroupActivity.class);
    }

    /**创建群**/
    @OnClick(R.id.imgbtn_add)
    protected void onCreateGroup() {
        UIHelper.showtAty(this, CreateGroupActivity.class);
    }
}
