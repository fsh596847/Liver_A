package com.android.doctor.ui.message;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.adapter.GroupMemberListAdapter;
import com.android.doctor.ui.base.BaseActivity;
import com.yuntongxun.kitsdk.utils.LogUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/3/26.
 */
public class GroupMemberActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;
    @InjectView(R.id.tv_edit)
    protected TextView mTvEdit;

    FragmentGroupMemberList fg;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.fragment_group_member);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.search_group);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        fg = new FragmentGroupMemberList();

        ts.add(R.id.fl_container, fg);
        ts.commitAllowingStateLoss();
    }

    @OnClick(R.id.tv_add)
    protected void onAddMember() {
        UIHelper.showtAty(this, ContactActivity.class);
    }

    @OnClick(R.id.tv_edit)
    protected void onEditListMember() {
        String text = mTvEdit.getText().toString();
        mTvEdit.setText("编辑".equals(text) ? "完成" : "编辑");
        GroupMemberListAdapter adapter =  ((GroupMemberListAdapter)fg.getAdapter());
        if ("编辑".equals(text)) {
            adapter.notifySlideEvent(1);
        } else {
            adapter.notifySlideEvent(0);
        }
        adapter.notifyDataSetChanged();
    }

    public TextView getmTvEdit() {
        return mTvEdit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(LogUtil.getLogUtilsTag(GroupMemberActivity.class), "onDestory.");
    }
}
