package com.android.doctor.ui.patient;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.Constants;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by Yong on 2016/4/6.
 */
public class ArcImportListActivity extends BaseActivity {

    public static void startAty(Context c, String pname) {
        Intent intent = new Intent(c, ArcImportListActivity.class);
        intent.putExtra("pname", pname);
        c.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_arc_list);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.archive);
        FragmentPatientList mFragmentPatient = FragmentPatientList.newInstance(Constants.PATIENT_TYPE_DIAG_RECORD, getIntent().getStringExtra("pname"));
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fl_container, mFragmentPatient).commit();
    }

}
