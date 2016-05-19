package com.android.doctor.ui.base;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by Yong on 2016/4/28.
 */
public class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener{
    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return true;
    }

}
