package com.android.doctor.ui.app;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.patient.FragmentPatientStats;
import com.android.doctor.ui.plan.FragmentPlanList;

import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-18.
 */
public class CommonFragmentActivity extends BaseActivity {
    public static String EXTRA_FRAGMENT_TYPE = "FRAGMENT_TYPE";
    public static String FRAGMENT_TYPE_COMMENT_LIST = "FRAGMENT_COMMENT_LIST";
    public static String FRAGMENT_TYPE_STAT_CHART = "FRAGMENT_TYPE_STAT_CHART";
    public static String FRAGMENT_TYPE_PATIENT_PLAN = "FRAGMENT_PATIENT_PLAN";

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;
    private String mType;

    public CommonFragmentActivity() {

    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_fragment_container);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            mType = intent.getStringExtra(EXTRA_FRAGMENT_TYPE);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        if (FRAGMENT_TYPE_STAT_CHART.equals(mType)) {
            FragmentPatientStats f = new FragmentPatientStats();
            trans.replace(R.id.fl_container, f);
            setActionBar(R.string.patient_stat);
        } else if (FRAGMENT_TYPE_PATIENT_PLAN.equals(mType)) {
            FragmentPlanList f = FragmentPlanList.newInstance(intent.getStringExtra("id"));
            trans.replace(R.id.fl_container, f);
            setActionBar(R.string.visit_plan);
        }
        /*FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();

        trans.replace(R.id.fl_container, f);*/
        trans.commit();
    }

}
