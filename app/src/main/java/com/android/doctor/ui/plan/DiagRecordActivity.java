package com.android.doctor.ui.plan;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.ui.base.BaseActivity;
import com.github.jjobes.slidedatetimepicker.SlideDateTimeListener;
import com.github.jjobes.slidedatetimepicker.SlideDateTimePicker;

import java.util.Date;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/4/2.
 */
public class DiagRecordActivity extends BaseActivity {

    @InjectView(R.id.tv_title)
    protected TextView mTvTitle;
    @InjectView(R.id.tv_patient_name)
    protected TextView mTvPatientName;
    @InjectView(R.id.tv_diagnose_time)
    protected TextView mTvDiagTime;
    @InjectView(R.id.rdg_btn)
    protected RadioGroup mRdg;
    @InjectView(R.id.edt_content)
    protected EditText mEdtContent;

    private SlideDateTimePicker mDateTimePicker;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_diagnose_record);
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar("");
        mDateTimePicker = new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        mTvDiagTime.setText(DateUtils.formatDate(date));
                    }
                }).setInitialDate(new Date()).build();
    }

    @OnClick(R.id.rl_diagnose_time)
    protected void onSetTime() {
        mDateTimePicker.show();
    }


    @OnClick(R.id.btn_save)
    protected void onSave() {
        int id = mRdg.getCheckedRadioButtonId();
    }
}
