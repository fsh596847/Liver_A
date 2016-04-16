package com.android.doctor.helper;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.android.doctor.ui.activity.CommonFragmentActivity;
import com.android.doctor.ui.patient.AddPatientMainActivity;
import com.android.doctor.ui.patient.FillPatientFormActivity;
import com.android.doctor.ui.activity.MyFavoriteActivity;

/**
 * Created by Yong on 2016-02-18.
 */
public class UIHelper {

    public static void showMerchantListAty(Context context) {
        Intent intent = new Intent(context, AddPatientMainActivity.class);
        context.startActivity(intent);
    }

    public static void showtAty(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void showMyFavoriteAty(Context context) {
        Intent intent = new Intent(context, MyFavoriteActivity.class);
        context.startActivity(intent);
    }

    public static void showPatientPlanAty(Context context, String id) {
        Intent intent = new Intent(context, CommonFragmentActivity.class);
        intent.putExtra(CommonFragmentActivity.EXTRA_FRAGMENT_TYPE,
                CommonFragmentActivity.FRAGMENT_TYPE_PATIENT_PLAN);
        intent.putExtra("id", id);
        context.startActivity(intent);
    }

    public static void showToast(Context context, int resId, int duration) {
        Toast toast = Toast.makeText(context, resId, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(Context context, String str, int duration) {
        Toast toast = Toast.makeText(context, str, duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showToast(Context context, String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void showQuickRegisterAty(Context context) {

    }

    public static void showStatAty(Context context) {
        Intent intent = new Intent(context, CommonFragmentActivity.class);
        intent.putExtra(CommonFragmentActivity.EXTRA_FRAGMENT_TYPE,
                CommonFragmentActivity.FRAGMENT_TYPE_STAT_CHART);
        context.startActivity(intent);
    }

    public static void showAddPatientAty(Context context) {
        Intent intent = new Intent(context, AddPatientMainActivity.class);
        context.startActivity(intent);
    }

    public static void showFillPatientFormAty(Context context) {
        Intent intent = new Intent(context, FillPatientFormActivity.class);
        context.startActivity(intent);
    }
}
