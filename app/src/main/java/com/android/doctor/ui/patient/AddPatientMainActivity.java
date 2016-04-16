package com.android.doctor.ui.patient;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class AddPatientMainActivity extends BaseActivity {
	
	public static final String TAB_1 = "TAB_1";
	public static final String TAB_2 = "TAB_2";
	
	private FragmentTabHost mTabHost;
	private ActionBar mActionBar;
    @InjectView(R.id.rdg_btn)
	protected RadioGroup mRdoGrp;
    @InjectView(R.id.rdbtn_leave_patient)
    protected RadioButton mTab1;
    @InjectView(R.id.rdbtn_clinic)
    protected RadioButton mTab2;

	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_invite_patient_tabhost);
	}
	
	public void initView() {
		setActionBar();
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
		Bundle b = new Bundle();
		b.putInt("type", FragmentPatientList.TYPE_LEAVE_HOSPITAL_PATIENT);
		Bundle b1 = new Bundle();
		b1.putInt("type", FragmentPatientList.TYPE_CLINIC_PATIENT);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_1).setIndicator(TAB_1), FragmentPatientList.class, b);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_2).setIndicator(TAB_2), FragmentPatientList.class, b1);
        mTabHost.setCurrentTabByTag(TAB_1);
		//mRdoGrp = (RadioGroup) findViewById(R.id.rdg_btn);
		//mRdoGrp.setOnCheckedChangeListener(this);
	}
	
	private void setActionBar() {
		//
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mActionBar = getSupportActionBar();
		//mActionBar.setIcon(R.color.transparent);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(false);
		//mActionBar.setDisplayShowCustomEnabled(true);
		//mActionBar.setElevation(0);
		//ActionBarUtil.ChangeActionBarHomeUpDrawable(this,R.drawable.icon_fanhui);
	}


    @OnCheckedChanged(R.id.rdbtn_leave_patient)
	protected void setTab1() {
        if (mTab1.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB_1);
        }
	}

    @OnCheckedChanged(R.id.rdbtn_clinic)
    protected void setTab2() {
        if (mTab2.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB_2);
        }
    }

    @OnClick(R.id.tv_add_manual)
    protected void addManual() {
        UIHelper.showFillPatientFormAty(this);
    }
}
