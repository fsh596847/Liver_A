package com.android.doctor.ui.plan;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;
import com.p_v.flexiblecalendar.FlexibleCalendarView;

import java.util.Calendar;
import java.util.Locale;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/5/6.
 */
public class PlanRemindActivity extends BaseActivity {

    @InjectView(R.id.tv_month)
    protected TextView mTvMonth;
    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;
    protected FlexibleCalendarView mCalendarView;
    private FragmentRemindList fragmentRemind;
    private Calendar mCalendar = Calendar.getInstance();

    public static void startAty(Context context) {
        Intent intent = new Intent(context, PlanRemindActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_plan_schedule);
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(R.string.remind_event);
        //setCalListener();
        setFragment();
    }

    @Override
    protected void initData() {
        super.initData();
        updateViewData();
        //Calendar cal = Calendar.getInstance();
        //updateViewData(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH));
    }

    private void setCalListener() {
        mCalendarView.setOnMonthChangeListener(new FlexibleCalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month, @FlexibleCalendarView.Direction int direction) {
                //updateViewData(year, month);
            }
        });
    }

    private void setFragment() {
        fragmentRemind = new FragmentRemindList();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fl_container, fragmentRemind);
        trans.commitAllowingStateLoss();
    }

    private void updateViewData() {
        int y = mCalendar.get(Calendar.YEAR);
        int w = mCalendar.get(Calendar.MONTH);
        mTvMonth.setText(mCalendar.get(Calendar.YEAR) + " " + mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.CHINA));
        fragmentRemind.onLoadRemindList(y ,w + 1);
    }

    @OnClick(R.id.tv_pre_month)
    protected void preMonth() {
        //mCalendarView.moveToPreviousMonth();
        mCalendar.roll(Calendar.MONTH, false);
        updateViewData();
    }

    @OnClick(R.id.tv_next_month)
    protected void nextMonth() {
        //mCalendarView.moveToNextMonth();
        mCalendar.roll(Calendar.MONTH, true);
        updateViewData();
    }
}
