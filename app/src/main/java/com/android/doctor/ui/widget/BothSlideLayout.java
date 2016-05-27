package com.android.doctor.ui.widget;

/**
 * Created by Yong on 2016/3/26.
 */
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;


/**
 * Created by Bruce on 11/24/14.
 */
public class BothSlideLayout extends LinearLayout {
    public static final int SLIDE_LEFT = -1;
    public static final int SLIDE_RIGHT = 1;

    private ViewDragHelper viewDragHelper;
    private View contentView;
    private View actionLeftView;
    private View actionRightView;
    private int dragLeftDistance = 0;
    private int dragRightDistance = 0;
    private final double AUTO_OPEN_SPEED_LIMIT = 1000.0;
    private int draggedX;
    private boolean canSlide;
    private OnSlideListener onSlideListener;

    public BothSlideLayout(Context context) {
        this(context, null);
    }

    public BothSlideLayout(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BothSlideLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        viewDragHelper = ViewDragHelper.create(this, new DragHelperCallback());
    }

    @Override
    protected void onFinishInflate() {
        actionLeftView = getChildAt(0);
        contentView = getChildAt(1);
        actionRightView = getChildAt(2);
        actionLeftView.setVisibility(GONE);
        actionRightView.setVisibility(GONE);
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //actionLeftView.measure(widthMeasureSpec, heightMeasureSpec);
        //actionRightView.measure(widthMeasureSpec, heightMeasureSpec);
        dragRightDistance = actionLeftView.getMeasuredWidth();
        dragLeftDistance = actionRightView.getMeasuredWidth();
        //Log.d("onMeasure: " ," " + dragRightDistance + ", " + dragLeftDistance);
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            return view == contentView || view == actionRightView ;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            draggedX = left;
            if (changedView == contentView) {
                actionRightView.offsetLeftAndRight(dx);
            } else {
                contentView.offsetLeftAndRight(dx);
            }
            if (actionRightView.getVisibility() == View.GONE) {
                actionRightView.setVisibility(View.VISIBLE);
            }
            invalidate();
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (child == contentView) {
                final int leftBound = getPaddingLeft();
                final int minLeftBound = -leftBound - dragLeftDistance;
                final int newLeft = Math.min(Math.max(minLeftBound, left), 0);
                return newLeft;
            } else {
                final int minLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() - dragLeftDistance;
                final int maxLeftBound = getPaddingLeft() + contentView.getMeasuredWidth() + getPaddingRight();
                final int newLeft = Math.max(Math.max(left, minLeftBound), maxLeftBound);
                return newLeft;
            }
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            return dragLeftDistance;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            boolean settleToOpen = false;
            if (xvel > AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = false;
            } else if (xvel < -AUTO_OPEN_SPEED_LIMIT) {
                settleToOpen = true;
            } else if (draggedX <= -dragLeftDistance / 2) {
                settleToOpen = true;
            } else if (draggedX > -dragLeftDistance / 2) {
                settleToOpen = false;
            }

            final int settleDestX = settleToOpen ? -dragLeftDistance : 0;
            viewDragHelper.smoothSlideViewTo(contentView, settleDestX, 0);
            ViewCompat.postInvalidateOnAnimation(BothSlideLayout.this);
            if (onSlideListener != null) {
                onSlideListener.onSlide(settleToOpen ? SLIDE_LEFT : 0);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(canSlide && viewDragHelper.shouldInterceptTouchEvent(ev)) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (canSlide) {
            viewDragHelper.processTouchEvent(event);
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(viewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void slideOpenLeftView() {
        if (actionLeftView.getVisibility() == View.GONE) {
            actionLeftView.setVisibility(View.VISIBLE);
        }
        invalidate();
        ViewCompat.postInvalidateOnAnimation(BothSlideLayout.this);
    }


    public void slideOpenRightView() {
        if (actionRightView.getVisibility() == View.GONE) {
            actionRightView.setVisibility(View.VISIBLE);
        }
        invalidate();
        ViewCompat.postInvalidateOnAnimation(BothSlideLayout.this);
    }

    public void closeSlide() {
        canSlide = false;
        if (actionRightView.getVisibility() == View.VISIBLE) {
            actionRightView.setVisibility(View.GONE);
        }
        if (actionLeftView.getVisibility() == View.VISIBLE) {
            actionLeftView.setVisibility(View.GONE);
        }
        invalidate();
        ViewCompat.postInvalidateOnAnimation(BothSlideLayout.this);
    }

    public void setCanSlide(boolean canSlide) {
        this.canSlide = canSlide;
    }

    public void setOnSlideListener(OnSlideListener onSlideListener) {
        this.onSlideListener = onSlideListener;
    }

    public interface OnSlideListener {
        void onSlide(int dir);
    }
}
