package com.fidots.restaurant.helper;

import android.content.Context;
import android.content.Intent;

import com.fidots.restaurant.ui.activity.CommonFragmentActivity;
import com.fidots.restaurant.ui.activity.MerchantMainActivity;
import com.fidots.restaurant.ui.activity.MerchantSpecificActivity;
import com.fidots.restaurant.ui.activity.MyFavoriteActivity;

/**
 * Created by Yong on 2016-02-18.
 */
public class UIHelper {

    public static void showMerchantListAty(Context context) {
        Intent intent = new Intent(context, MerchantMainActivity.class);
        context.startActivity(intent);
    }

    public static void showSpecificMerchantAty(Context context) {
        Intent intent = new Intent(context, CommonFragmentActivity.class);
        context.startActivity(intent);
    }

    public static void showMyFavoriteAty(Context context) {
        Intent intent = new Intent(context, MyFavoriteActivity.class);
        context.startActivity(intent);
    }
}
