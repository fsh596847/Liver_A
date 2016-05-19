package com.android.doctor.ui.mine;

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

public class PubAnnocActivity extends BaseActivity {
	@InjectView(R.id.rgb_group)
	protected RadioGroup mRbtn;
	@InjectView(R.id.tv_time)
	protected TextView mTvTime;
	@InjectView(R.id.edt_content)
	protected EditText mEdtContent;

    private SlideDateTimePicker mDateTimePicker;
	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_anc);
	}

	@Override
	protected void initView() {
		super.initView();
		setActionBar(R.string.pub_announce);
        mDateTimePicker = new SlideDateTimePicker.Builder(getSupportFragmentManager())
                .setListener(new SlideDateTimeListener() {
                    @Override
                    public void onDateTimeSet(Date date) {
                        mTvTime.setText(DateUtils.formatDate(date));
                    }
                }).setInitialDate(new Date()).build();
	}


	@OnClick(R.id.tv_save)
	protected void onPub() {
        int id = mRbtn.getCheckedRadioButtonId();
        int stat = id == R.id.rbtn_diag ? 0 : id == R.id.rbtn_stop ? 1 : 2;

	}

    @OnClick(R.id.tv_time)
    protected void onSetTime() {
        mDateTimePicker.show();
    }
}
