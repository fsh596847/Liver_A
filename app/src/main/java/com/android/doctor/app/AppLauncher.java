package com.android.doctor.app;

import android.Manifest;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.app.LoginEntryActivity;
import com.android.doctor.ui.app.MainActivity;

/**
 * 应用启动界面
 * 
 * @author Yong
 * 
 */
public class AppLauncher extends Activity {
    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private String permissions [] = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
        final View view = View.inflate(this, R.layout.app_start, null);
        setContentView(view);
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(2000);
        view.startAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                requirePermission(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationStart(Animation animation) {}
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void requirePermission(int index) {
        Log.d(AppConfig.TAG, "[AppStart-> requirePermission], index= " + index);
        if (index < permissions.length) {
            if (ContextCompat.checkSelfPermission(this, permissions[index]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{permissions[index]}, index);
            } else {
                requirePermission(index + 1);
            }
        } else {
            redirectTo();
        }
    }


    @Override
    public void onRequestPermissionsResult(final int requestCode,final String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (0 < grantResults.length ) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Log.d(AppConfig.TAG, "[AppStart-> onRequestPermissionsResult], onResult " + requestCode + ", " + permissions[0]);
               //ActivityCompat.requestPermissions(this, permissions, requestCode);
                new AlertDialog.Builder(this)
                        .setTitle("权限申请")
                        .setMessage("在设置-应用-医生中开启权限，以正常使用医生功能")
                        .setCancelable(false)
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppContext.context().AppExit(AppLauncher.this);
                            }})
                        .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                AppLauncher.this.startActivityForResult(new Intent(Settings.ACTION_APPLICATION_SETTINGS), requestCode);
                                AppContext.context().AppExit(AppLauncher.this);
                                //finish();*/
                                //ActivityCompat.requestPermissions(App.this, permissions, requestCode);
                            }}).show();
            } else {
                requirePermission(requestCode + 1);
            }
        }
        //
    }
    /**
     * 跳转到...
     */
    private void redirectTo() {
        boolean isLogin = AppContext.context().isLogin();
        if (isLogin) {
            UIHelper.showtAty(this, MainActivity.class);
        } else {
            UIHelper.showtAty(this, LoginEntryActivity.class);
        }
        AppManager.getAppManager().finishActivity(AppLauncher.class);
    }
}
