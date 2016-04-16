package com.android.doctor.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/4/9.
 */
public class EntryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btn_login)
    protected void onLogin() {
        UIHelper.showtAty(this, LoginActivity.class);
    }

    @OnClick(R.id.btn_register)
    protected void onRegister() {
        UIHelper.showtAty(this, RegisterPhoneActivity.class);
    }
}
