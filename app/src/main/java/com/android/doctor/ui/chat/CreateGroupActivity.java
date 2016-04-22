package com.android.doctor.ui.chat;

import android.widget.EditText;
import android.widget.RadioGroup;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.base.BaseActivity;
import com.yuntongxun.kitsdk.utils.TextUtil;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/3/26.
 */
public class CreateGroupActivity extends BaseActivity {

    @InjectView(R.id.edt_group_name)
    protected EditText mEdtGroupName;
    @InjectView(R.id.edt_group_classify)
    protected EditText mEdtGroupClassify;
    @InjectView(R.id.rg_join_condition)
    protected RadioGroup mRgJoinCondition;
    @InjectView(R.id.edt_group_announce)
    protected EditText mEdtGroupAnnounce;

    private int mJoinCondtion = 0;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_create_group);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.create_group);
    }

    private void onCheckNull() {
        String gname = mEdtGroupName.getText().toString();
        if (TextUtil.isEmpty(gname)) {
            UIHelper.showToast(this, "组名不能为空");
            return;
        }
        String gclass = mEdtGroupClassify.getText().toString();
        if (TextUtil.isEmpty(gclass)) {
            UIHelper.showToast(this, "分组名不能为空");
            return;
        }
    }


    /**保存*/
    @OnClick(R.id.tv_save)
    protected void onSearchGroup() {
        //UIHelper.showtAty(this, SearchGroupActivity.class);
    }


}
