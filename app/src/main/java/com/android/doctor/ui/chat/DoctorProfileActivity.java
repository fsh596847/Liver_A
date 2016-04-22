package com.android.doctor.ui.chat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

/**
 * Created by Yong on 2016/3/26.
 */
public class DoctorProfileActivity extends BaseActivity {

    @InjectView(R.id.rdbtn_doctor_intro)
    protected RadioButton mRdbDoctorIntro;
    @InjectView(R.id.rdbtn_visit_time)
    protected RadioButton mRdbDoctorVisit;
    @InjectView(R.id.fl_container)
    protected FrameLayout frameLayout;
    @InjectView(R.id.fab)
    protected FabSpeedDial fab;
    FragmentDoctorTime f1 = new FragmentDoctorTime();
    @Override
    protected void setContentLayout(){
        setContentView(R.layout.activity_doctor_profile);
    }

    @Override
    protected void initView() {
        setActionBar("");
        fab.setMenuListener(new SimpleMenuListenerAdapter(){
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_call) {

                } else if (id == R.id.action_add_contact) {

                }
                return super.onMenuItemSelected(menuItem);
            }
        });
        updateFragment(f1);
    }

    private void updateFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.fl_container, fragment);
        trans.commitAllowingStateLoss();
    }

    @OnCheckedChanged(R.id.rdbtn_doctor_intro)
    protected void setTab1() {
        if (mRdbDoctorIntro.isChecked()) {

        }
    }

    @OnCheckedChanged(R.id.rdbtn_visit_time)
    protected void setTab2() {
        if (mRdbDoctorVisit.isChecked()) {

        }
    }

}
