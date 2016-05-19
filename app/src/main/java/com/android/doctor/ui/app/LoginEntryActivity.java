package com.android.doctor.ui.app;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.OnClick;

/**
 * Created by Yong on 2016/4/9.
 */
public class LoginEntryActivity extends BaseActivity {

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_entry_login);
    }

    @OnClick(R.id.btn_login)
    protected void onLogin() {
        UIHelper.showtAty(this, LoginActivity.class);
    }

    @OnClick(R.id.btn_forget)
    protected void onForget() {
        UIHelper.showtAty(this, ForgetPasswordActivity.class);
    }
}
