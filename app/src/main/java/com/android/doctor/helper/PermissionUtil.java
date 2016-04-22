package com.android.doctor.helper;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;


import com.android.doctor.app.AppContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/4/20.
 */
public class PermissionUtil {
   private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private static  final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    public static boolean checkPermission(final Activity aty, String permission) {
        if (ContextCompat.checkSelfPermission(aty, permission) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public static void checkPermissionList(final Activity aty, List<String> permissionsNeeded) {
        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(aty, permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add("GPS");
        if (!addPermission(aty, permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(aty, permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    showMessageDialog(aty, "");
                return;
            }
            ActivityCompat.requestPermissions(aty,permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
    }

    public static boolean addPermission(Activity aty, List<String> permissionsList, String permission) {
        if (ContextCompat.checkSelfPermission(aty, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!ActivityCompat.shouldShowRequestPermissionRationale(aty, permission))
                return false;
        }
        return true;
    }

    public static void showMessageDialog(final Activity ctx, String message) {
        new AlertDialog.Builder(ctx)
                .setTitle("权限申请")
                .setMessage("在设置-应用-医生中开启权限，以正常使用医生功能")
                .setCancelable(false)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppContext.context().AppExit(ctx);
                    }})
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ctx.startActivityForResult(new Intent(Settings.ACTION_APPLICATION_SETTINGS), 0);
                        AppContext.context().AppExit(ctx);
                    }})
                .show();
    }

}
