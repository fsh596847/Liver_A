package com.android.doctor.ui.message;

import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/3/26.
 */
public class GroupProfileActivity extends BaseActivity {

    @InjectView(R.id.tv_group_master)
    protected TextView mTvGroupMaster;
    @InjectView(R.id.tv_group_member)
    protected TextView mTvGroupMember;
    @InjectView(R.id.tv_join_condition)
    protected TextView mTvJoinCondtion;
    @InjectView(R.id.tv_group_divide)
    protected TextView mGroupDivide;
    @InjectView(R.id.tv_group_announce)
    protected TextView mGroupAnnounce;

    private int mJoinCondtion = 0;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.fragment_group_detail);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.create_group);
    }



    @OnClick(R.id.rl_group_member)
    protected void onClickMember(){
        UIHelper.showtAty(this, GroupMemberActivity.class);
    }

    @OnClick(R.id.rl_my_group_card)
    protected void onClickMyGroupCard() {
        UIHelper.showtAty(this, GroupCardActivity.class);
    }

}
