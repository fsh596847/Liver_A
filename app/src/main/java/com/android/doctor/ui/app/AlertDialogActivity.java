package com.android.doctor.ui.app;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.ui.base.BaseActivity;
import com.yuntongxun.kitsdk.utils.ECNotificationManager;

/**
 * Created by Yong on 2016/6/1.
 */
public class AlertDialogActivity extends Activity {

    public static void startAty(Context context,String text) {
        Log.d(AppConfig.TAG, "[AlertDialogActivity-> startAty] " + text);

        Intent intent = new Intent(context, AlertDialogActivity.class);
        intent.putExtra("text", text);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        String kickoffText = getIntent().getStringExtra("text");

        AlertDialog.Builder builder = new AlertDialog.Builder(AppContext.context());
        builder.setMessage(kickoffText);
        builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ECNotificationManager.getInstance().forceCancelNotification();
                AppContext.context().restartAPP();
            }
        });
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppContext.context().AppExit();
            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.show();
    }
}
