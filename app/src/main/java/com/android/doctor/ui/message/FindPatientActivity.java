package com.android.doctor.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.fragment.FragmentHome;

/**
 * Created by Yong on 2016/3/28.
 */
public class FindPatientActivity extends BaseActivity {

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_list_patient);
    }

    @Override
    protected void initView() {
        Bundle b = new Bundle();
        b.putInt("type", 1);
        FragmentHome fh = new FragmentHome();
        fh.setArguments(b);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fl_container, fh).commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        setResult(resultCode, data);
    }
}
