package com.android.doctor.ui.plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;

/**
 * Created by Yong on 2016/3/26.
 */
public class SelectUseItemActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;

    public static void startAty(Activity aty, int req) {
        Intent intent = new Intent();
        intent.putExtra("requestCode", req);
        intent.setClass(aty, SelectUseItemActivity.class);
        aty.startActivityForResult(intent, req);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_select_item_list);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.please_select);
        Intent intent = getIntent();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        FragmentMedicUseList fg = FragmentMedicUseList.newInstance(intent.getIntExtra("requestCode", 0));

        ts.add(R.id.fl_container, fg);
        ts.commitAllowingStateLoss();
    }
}
