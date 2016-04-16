package com.android.doctor.ui.message;

import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/3/26.
 */
public class GroupCardActivity extends BaseActivity {

    @InjectView(R.id.tv_group_nickname)
    protected TextView mTvNickName;
    @InjectView(R.id.tv_phone_member)
    protected TextView mTvPhoneNum;
    @InjectView(R.id.tv_my_email)
    protected TextView mTvEmail;
    @InjectView(R.id.tv_note)
    protected TextView mTvNote;

    private int mJoinCondtion = 0;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.fragment_group_card);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.create_group);
    }

    private void onCheckNull() {

    }


    /**保存*/
    @OnClick(R.id.btn_save_change)
    protected void onSaveChange() {
        //UIHelper.showtAty(this, SearchGroupActivity.class);
    }


}
