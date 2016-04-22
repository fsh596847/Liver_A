package com.android.doctor.ui.plan;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.chat.FindPatientActivity;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;
import com.yuntongxun.kitsdk.utils.LogUtil;

import java.util.Date;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/3/30.
 */
public class AddPlanActivity extends BaseActivity  {

    @InjectView(R.id.tv_patient)
    protected TextView mTvPatient;
    @InjectView(R.id.tv_start_date)
    protected TextView mTvStartDate;

    private SlideDateTimePicker mDateTimePicker;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_add_plan);
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(R.string.visit_plan);
        mDateTimePicker = new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        mTvStartDate.setText(DateUtils.formatDate(date));
                    }
                }).setInitialDate(new Date()).build();
    }

    @OnClick(R.id.rl_select_patient)
    protected void onSelectPatient() {
        Intent intent = new Intent();
        intent.setClass(this, FindPatientActivity.class);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == RESULT_OK) {
            LogUtil.d("AddPlanActivity: ", data.getDataString());
        }
    }

    @OnClick(R.id.btn_create)
    protected void onCreateProject() {
        String p = mTvPatient.getText().toString();
        if (!TextUtils.isEmpty(p)) {
            UIHelper.showToast(this, "请选择患者");
            return;
        }
    }

    @OnClick(R.id.rl_start_date)
    protected void onSetStartDate() {
        mDateTimePicker.show();
    }

}
