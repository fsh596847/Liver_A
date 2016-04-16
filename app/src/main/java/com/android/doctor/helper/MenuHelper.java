package com.android.doctor.helper;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import java.lang.reflect.Field;

/**
 * Created by Yong on 2016/3/25.
 */
public class MenuHelper {
    public static void displayPopupMenu(Context context, int layout,
                                        View view,
                                        PopupMenu.OnMenuItemClickListener listener) {
        if (layout == 0) {
            return;
        }

        PopupMenu popup = new PopupMenu(context, view);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(layout, menu);
        Object menuHelper;

        try {
            Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
            fMenuHelper.setAccessible(true);
            menuHelper = fMenuHelper.get(popup);
            Class[] argTypes = new Class[] { boolean.class };
            menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
        } catch (Exception e) {
            popup.show();
            return;
        }
        popup.setOnMenuItemClickListener(listener);
        popup.show();
    }
}
