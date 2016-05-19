package com.android.doctor.ui.mine;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.app.AppManager;
import com.android.doctor.helper.PermissionUtil;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.Constants;
import com.android.doctor.ui.app.ChangePasswordActivity;
import com.android.doctor.ui.app.LoginActivity;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

	@Override
	protected void setContentLayout() {
		setContentView(R.layout.fragment_user_setting);
	}

	@Override
	protected void initView() {
		super.initView();
		setActionBar(R.string.setting);
	}

    @OnClick(R.id.rl_change_passwd)
    protected void changePwd() {
        UIHelper.showtAty(this, ChangePasswordActivity.class);
    }

	@OnClick(R.id.rl_contact_service)
	protected void onContactSrv() {
		if (!PermissionUtil.checkPermission(this, Manifest.permission.CALL_PHONE)){
			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 0);
			return;
		}
		Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + getString(R.string.service_mobile)));
		startActivity(intent);
	}

	@OnClick(R.id.rl_feedback)
	protected void feedback() {
		EditActivity.startAty(this, Constants.REQUEST_CODE_EDIT_SUGGEST, "");
	}

    @OnClick(R.id.btn_logout)
    protected void onLogout() {
        AppContext.context().logout();
        AppManager.getAppManager().finishAllActivity();
        UIHelper.showtAty(this, LoginActivity.class);
    }
}
