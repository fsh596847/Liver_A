package com.android.doctor.ui.patient;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

/**
 * Created by Yong on 2016/3/11.
 */
public class FillPatientFormActivity extends BaseActivity {

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_patient_add_form);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.add_patient);
    }

}
