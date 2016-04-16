package com.android.doctor.ui.plan;

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
public class SelectListItemActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_select_item_list);
    }

    @Override
    protected void initView() {
        setActionBar("");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        FragmentTextList fg = new FragmentTextList();
        Bundle b = new Bundle();
        fg.setArguments(b);
        ts.add(R.id.fl_container, fg);
        ts.commitAllowingStateLoss();
    }
}
