package com.android.doctor.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.p_v.flexiblecalendar.view.CircularEventCellView;

/**
 * @author p-v
 */
public class DateCellView extends CircularEventCellView {

    public DateCellView(Context context) {
        super(context);
    }

    public DateCellView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateCellView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height =  (3*width)/5;
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
