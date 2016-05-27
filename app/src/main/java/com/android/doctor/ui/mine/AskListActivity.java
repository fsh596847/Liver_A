package com.android.doctor.ui.mine;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

/**
 * Created by Yong on 2016/5/19.
 */
public class AskListActivity extends BaseActivity {

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_fragment_container);
    }

    @Override
    protected void initView() {
        super.initView();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        FragmentAskList f = new FragmentAskList();
        trans.replace(R.id.fl_container, f);
        trans.commit();
        setActionBar(R.string.ask);
    }
}
