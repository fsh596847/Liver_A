package com.android.doctor.ui.patient;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by Yong on 2016/4/6.
 */
public class ArchiveActivity extends BaseActivity {

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_archive);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.archive);
    }


    @OnClick(R.id.rl_laboratory_archive)
    protected void onLabArchive() {
        UIHelper.showtAty(this, ArchiveDetailActivity.class);
    }

    @OnClick(R.id.rl_medicine_archive)
    protected void onMedinceArchive() {
        UIHelper.showtAty(this, ArchiveDetailActivity.class);
    }

    @OnClick(R.id.rl_out_hospital)
    protected void onOutHospital() {
        UIHelper.showtAty(this, ArchiveDetailActivity.class);
    }
}
