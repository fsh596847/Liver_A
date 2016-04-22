package com.android.doctor.ui.patient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;

public class HospitalPatientMainActivity extends BaseActivity {
	
	public static final String TAB_1 = "TAB_1";
	public static final String TAB_2 = "TAB_2";
	@InjectView(R.id.edt_search_box)
	protected EditText mEdtSearch;
	@InjectView(R.id.iv_clear)
	protected ImageView mIvClear;
	@InjectView(R.id.tv_cancel)
	protected TextView mTvCancel;
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
		Bundle b1 = new Bundle();
		b1.putInt("type", FragmentPatientList.TYPE_OUT_HOSPITAL_PATIENT);
		Bundle b2 = new Bundle();
		b2.putInt("type", FragmentPatientList.TYPE_CLINIC_PATIENT);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_1).setIndicator(TAB_1), FragmentPatientList.class, b1);
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

	private void setCancelViewBg(boolean focus) {
		if (focus) {
			mEdtSearch.requestFocus();
			DeviceHelper.showSoftKeyboard(mEdtSearch);
			mTvCancel.setTextColor(getResources().getColor(R.color.appThemePrimary));
		} else {
			mEdtSearch.clearFocus();
            mEdtSearch.setText("");
			DeviceHelper.hideSoftKeyboard(mEdtSearch);
			mTvCancel.setTextColor(getResources().getColor(R.color.gray));
            Fragment curFragment = getCurrentFragment();
            if (curFragment != null && curFragment.getClass().equals(FragmentPatientList.class)) {
                FragmentPatientList frag = (FragmentPatientList) curFragment;
                frag.clearOption("py");
            }
		}
	}

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
    }

    @OnEditorAction(R.id.edt_search_box)
    protected boolean onSearch(TextView v, int actionId,
                               KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String word = mEdtSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(word)) {
                DeviceHelper.hideSoftKeyboard(v);
                Fragment curFragment = getCurrentFragment();
                if (curFragment != null && curFragment.getClass().equals(FragmentPatientList.class)) {
                    FragmentPatientList frag = (FragmentPatientList) curFragment;
                    frag.onSearch("py", word);
                }
            }
            return true;
        }
        return false;
    }

	@OnFocusChange(R.id.edt_search_box)
	protected void onEditTextFocusChange(View v, boolean hasFocus) {
		setCancelViewBg(hasFocus);
	}

	@OnClick(R.id.tv_cancel)
	protected void onClickCancelView(){
		boolean focused = mEdtSearch.isFocused();
		setCancelViewBg(!focused);
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
		InvitePatientActivity.startAty(this, new HosPaitentList.HosPatientEntity(), 2);
    }
}
