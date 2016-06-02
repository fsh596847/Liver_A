package com.android.doctor.ui.plan;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.android.doctor.R;
import com.android.doctor.model.Constants;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/3/26.
 */
public class TPlanListActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;

    public static void startAty(Activity aty, int req) {
        Intent intent = new Intent();
        intent.putExtra("rcode", req);
        intent.setClass(aty, TPlanListActivity.class);
        aty.startActivityForResult(intent, req);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_tpls);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.visit_scheme);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        Intent intent = getIntent();
        FragmentTPlanList fg = FragmentTPlanList.newInstance(intent.getIntExtra("rcode", 0)/*, intent.getStringExtra("pid")*/);

        ts.add(R.id.fl_container, fg);
        ts.commitAllowingStateLoss();
    }

    @OnClick(R.id.img_add)
    protected void onCreateNewPlan() {
        NewPlanActivity.startAty(this, null);
    }
}
