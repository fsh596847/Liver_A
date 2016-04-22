package com.android.doctor.ui.plan;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.doctor.R;
import com.android.doctor.model.NewPlanRecord;
import com.android.doctor.model.PlanRecordList;
import com.android.doctor.ui.base.BaseActivity;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by Yong on 2016/4/2.
 */
public class PlanRecordListActivity extends BaseActivity {
    public static final String ARG_PARAM = "ARG_ID";
    public static final int REQUEST_CODE_ADD = 100;

    private String mPid;
    private FragmentPlanRecordList mFrag;

    public static void startAty(Context context, String pid) {
        Intent intent = new Intent(context, PlanRecordListActivity.class);
        intent.putExtra(ARG_PARAM, pid);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_record_list);
    }

    @Override
    protected void init() {
        super.init();
        mPid = getIntent().getStringExtra(ARG_PARAM);
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(R.string.plan_record);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        mFrag = FragmentPlanRecordList.newInstance(mPid);
        ts.add(R.id.fl_container, mFrag);
        ts.commitAllowingStateLoss();
    }


    @OnClick(R.id.img_add)
    protected void onAdd() {
        NewPlanRecord record = new NewPlanRecord();
        if (mFrag != null && mFrag.getAdapter() != null) {
            List<PlanRecordList.RecordEntity> list = mFrag.getAdapter().getData();
            if (list != null && !list.isEmpty()) {
                PlanRecordList.RecordEntity e = list.get(0);
                record.setNewPlanRecord(e);
            }
            Intent intent = new Intent(this, PlanRecordInfoActivity.class);
            intent.putExtra(PlanRecordInfoActivity.ARG_PARAM, record);
            startActivityForResult(intent, REQUEST_CODE_ADD);
        }
    }
}
