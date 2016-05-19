package com.android.doctor.ui.app;

import android.text.TextUtils;
import android.widget.EditText;

import com.android.doctor.helper.UIHelper;
import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;


public class ChangePasswordActivity extends BaseActivity {

	private static final String TAG = ChangePasswordActivity.class.getSimpleName();

	@InjectView(R.id.edt_input_old_password)
	protected EditText mEdtOldPwd;
    @InjectView(R.id.edt_input_new_password)
    protected EditText mEdtNewPwd;
    @InjectView(R.id.edt_input_new_password_again)
    protected EditText mEdtNewPwd2;

	@Override
    protected void setContentLayout() {
    	setContentView(getLayoutId());
    }
	    
	protected int getLayoutId() {
		return R.layout.activity_change_password;
	}	

	protected void initView() {
        setActionBar(R.string.change_password);
    }

    protected boolean preCheck() {
        String oldPwd = mEdtOldPwd.getText().toString();
        String newPwd = mEdtNewPwd.getText().toString();
        String newPwd2 = mEdtNewPwd2.getText().toString();
        if (TextUtils.isEmpty(oldPwd)) {
            UIHelper.showToast("请输入旧密码");
            return false;
        }
        if (TextUtils.isEmpty(newPwd)) {
            UIHelper.showToast("请输入新密码");
            return false;
        }
        if (TextUtils.isEmpty(newPwd2)) {
            UIHelper.showToast("请再次输入新密码");
            return false;
        }
        if (!newPwd.equals(newPwd2)) {
            UIHelper.showToast("两次输入的新密码不一致");
            return false;
        }
        return true;
    }

    @OnClick(R.id.tv_save)
    protected void saveChange() {
        if (!preCheck())return;
    }
}
