package com.android.doctor.app;

import java.io.File;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.android.doctor.helper.AppAsyncTask;
import com.android.doctor.helper.FileHelper;
import com.android.doctor.R;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.PreferenceHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.activity.LoginEntryActivity;
import com.android.doctor.ui.activity.MainActivity;

/**
 * 应用启动界面
 * 
 * @author Yong
 * 
 */
public class AppStart extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final View view = View.inflate(this, R.layout.app_start, null);
        setContentView(view);
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(800);
        view.startAnimation(aa);
        aa.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
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
        int cacheVersion = PreferenceHelper.readInt(this, "first_install",
                "first_install", -1);
        int currentVersion = DeviceHelper.getVersionCode();
        if (cacheVersion < currentVersion) {
            PreferenceHelper.write(this, "first_install", "first_install",
                    currentVersion);
            cleanImageCache();
        }
    }

    private void cleanImageCache() {
        final File folder = FileHelper.getSaveFolder("restaurant/rest_img");
        AppAsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for (File file : folder.listFiles()) {
                    file.delete();
                }
            }
        });
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
        //Intent uploadLog = new Intent(this, LogUploadService.class);
        //startService(uploadLog);
        //Intent intent = new Intent(this, MainActivity.class);
       // startActivity(intent);
        finish();
    }
}
