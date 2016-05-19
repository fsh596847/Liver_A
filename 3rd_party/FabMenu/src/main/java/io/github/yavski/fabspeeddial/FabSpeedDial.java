/*
 * Copyright 2016 Yavor Ivanov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.yavski.fabspeeddial;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.internal.NavigationMenu;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import io.github.yavski.fabmenu.R;
import io.github.yavski.fabspeeddial.Eases.EaseType;
import io.github.yavski.fabspeeddial.Types.DimType;
import io.github.yavski.fabspeeddial.Types.PlaceType;

/**
 * Created by yavorivanov on 01/01/2016.
 */
@CoordinatorLayout.DefaultBehavior(FabSpeedDialBehaviour.class)
public class FabSpeedDial extends LinearLayout implements View.OnClickListener {

    /**
     * Called to notify of close and selection changes.
     */
    public interface MenuListener {

        /**
         * Called just before the menu items are about to become visible.
         * Don't block as it's called on the main thread.
         *
         * @param navigationMenu The menu containing all menu items.
         * @return You must return true for the menu to be displayed;
         * if you return false it will not be shown.
         */
        boolean onPrepareMenu(NavigationMenu navigationMenu);

        /**
         * Called when a menu item is selected.
         *
         * @param menuItem The menu item that is selected
         * @return whether the menu item selection was handled
         */
        boolean onMenuItemSelected(MenuItem menuItem);

        void onMenuClosed();
    }

    private static final String TAG = FabSpeedDial.class.getSimpleName();

    private static final int VSYNC_RHYTHM = 16;

    public static final FastOutSlowInInterpolator FAST_OUT_SLOW_IN_INTERPOLATOR =
            new FastOutSlowInInterpolator();

    public static final int BOTTOM_END = 0;
    public static final int BOTTOM_START = 1;
    public static final int TOP_END = 2;
    public static final int TOP_START = 3;
    private static final int DEFAULT_MENU_POSITION = BOTTOM_END;

    private MenuListener menuListener;
    private NavigationMenu navigationMenu;
    private Map<FloatingActionButton, MenuItem> fabMenuItemMap;
    private Map<CardView, MenuItem> cardViewMenuItemMap;

    private LinearLayout menuItemsLayout;
    FloatingActionButton fab;
    private View touchGuard = null;

    private int menuId;
    private int fabGravity;
    private Drawable fabDrawable;
    private ColorStateList fabDrawableTint;
    private ColorStateList fabBackgroundTint;
    private ColorStateList miniFabDrawableTint;
    private ColorStateList miniFabBackgroundTint;
    private ColorStateList miniFabTitleBackgroundTint;
    private boolean miniFabTitlesEnabled;
    private int miniFabTitleTextColor;
    private boolean useTouchGuard;

    private boolean isAnimating;
    // Frames of animations
    private int frames = 80;
    private int duration = 800;
    // Delay
    private int delay = 100;
    // Movement ease
    private EaseType showMoveEaseType = EaseType.EaseOutBack;
    private EaseType hideMoveEaseType = EaseType.EaseOutCirc;
    // Scale ease
    private EaseType showScaleEaseType = EaseType.EaseOutBack;
    private EaseType hideScaleEaseType = EaseType.EaseOutCirc;

    private int buttonWidth = (int)Util.getInstance().dp2px(100);
    private int dotWidth = 10;
    private int menuItemNum = 0;
    private Context mContext;
    private ViewGroup animationLayout = null;
    private boolean animationPlaying = false;
    // Cancelable
    private boolean cancelable = true;
    // Dim value
    private DimType dimType = DimType.DIM_6;

    private int[][] startLocations = new int[9][2];
    private int[][] endLocations = new int[9][2];

    private FabSpeedDial(Context context) {
        super(context);
    }

    public FabSpeedDial(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public FabSpeedDial(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FabSpeedDial, 0, 0);
        resolveCompulsoryAttributes(typedArray);
        resolveOptionalAttributes(typedArray);
        typedArray.recycle();

        if (isGravityBottom()) {
            LayoutInflater.from(context).inflate(R.layout.fab_speed_dial_bottom, this, true);
        } else {
            LayoutInflater.from(context).inflate(R.layout.fab_speed_dial_top, this, true);
        }

        if (isGravityEnd()) {
            setGravity(Gravity.END);
        }

        menuItemsLayout = (LinearLayout) findViewById(R.id.menu_items_layout);

        setOrientation(VERTICAL);

        newNavigationMenu();

        int menuItemCount = navigationMenu.size();
        fabMenuItemMap = new HashMap<>(menuItemCount);
        cardViewMenuItemMap = new HashMap<>(menuItemCount);
    }

    private void resolveCompulsoryAttributes(TypedArray typedArray) {
        if (typedArray.hasValue(R.styleable.FabSpeedDial_fabMenu)) {
            menuId = typedArray.getResourceId(R.styleable.FabSpeedDial_fabMenu, 0);
        } else {
            throw new AndroidRuntimeException("You must provide the id of the menu resource.");
        }

        if (typedArray.hasValue(R.styleable.FabSpeedDial_fabGravity)) {
            fabGravity = typedArray.getInt(R.styleable.FabSpeedDial_fabGravity, DEFAULT_MENU_POSITION);
        } else {
            throw new AndroidRuntimeException("You must specify the gravity of the Fab.");
        }
    }

    private void resolveOptionalAttributes(TypedArray typedArray) {
        fabDrawable = typedArray.getDrawable(R.styleable.FabSpeedDial_fabDrawable);
        if (fabDrawable == null) {
            fabDrawable = ContextCompat.getDrawable(getContext(), R.drawable.fab_add_clear_selector);
        }

        fabDrawableTint = typedArray.getColorStateList(R.styleable.FabSpeedDial_fabDrawableTint);
        if (fabDrawableTint == null) {
            fabDrawableTint = getColorStateList(R.color.fab_drawable_tint);
        }

        if (typedArray.hasValue(R.styleable.FabSpeedDial_fabBackgroundTint)) {
            fabBackgroundTint = typedArray.getColorStateList(R.styleable.FabSpeedDial_fabBackgroundTint);
        }

        miniFabBackgroundTint = typedArray.getColorStateList(R.styleable.FabSpeedDial_miniFabBackgroundTint);
        if (miniFabBackgroundTint == null) {
            miniFabBackgroundTint = getColorStateList(R.color.fab_background_tint);
        }

        miniFabDrawableTint = typedArray.getColorStateList(R.styleable.FabSpeedDial_miniFabDrawableTint);
        if (miniFabDrawableTint == null) {
            miniFabDrawableTint = getColorStateList(R.color.mini_fab_drawable_tint);
        }

        miniFabTitleBackgroundTint = typedArray.getColorStateList(R.styleable.FabSpeedDial_miniFabTitleBackgroundTint);
        if (miniFabTitleBackgroundTint == null) {
            miniFabTitleBackgroundTint = getColorStateList(R.color.mini_fab_title_background_tint);
        }

        miniFabTitlesEnabled = typedArray.getBoolean(R.styleable.FabSpeedDial_miniFabTitlesEnabled, true);


        miniFabTitleTextColor = typedArray.getColor(R.styleable.FabSpeedDial_miniFabTitleTextColor,
                ContextCompat.getColor(getContext(), R.color.title_text_color));

        useTouchGuard = typedArray.getBoolean(R.styleable.FabSpeedDial_touchGuard, false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        /*LayoutParams layoutParams =
                new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int coordinatorLayoutOffset = getResources().getDimensionPixelSize(R.dimen.coordinator_layout_offset);
        if (fabGravity == BOTTOM_END || fabGravity == TOP_END) {
            layoutParams.setMargins(0, 0, coordinatorLayoutOffset, 0);
        } else {
            layoutParams.setMargins(coordinatorLayoutOffset, 0, 0, 0);
        }
        menuItemsLayout.setLayoutParams(layoutParams);*/

        // Set up the client's FAB
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageDrawable(fabDrawable);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fab.setImageTintList(fabDrawableTint);
        }
        if (fabBackgroundTint != null) {
            fab.setBackgroundTintList(fabBackgroundTint);
        }

        fab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAnimating) return;

                if (animationLayout != null && animationLayout.getVisibility() == VISIBLE) {
                    v.setSelected(false);
                    startHideAnimations();
                } else {

                    requestFocus();

                    boolean showMenu = true;
                    if (menuListener != null) {
                        newNavigationMenu();
                        showMenu = menuListener.onPrepareMenu(navigationMenu);
                    }

                    if (showMenu) {
                        fab.setSelected(true);
                        dimAnimationLayout();
                        startShowAnimations();
                    } else {
                        fab.setSelected(false);
                    }
                }
            }
        });

        // Needed in order to intercept key events
        setFocusableInTouchMode(true);

        if (useTouchGuard) {
            ViewParent parent = getParent();

            touchGuard = new View(getContext());
            touchGuard.setOnClickListener(this);
            touchGuard.setWillNotDraw(true);
            touchGuard.setVisibility(GONE);
            if (parent instanceof FrameLayout) {
                ((FrameLayout) parent).addView(touchGuard, ((FrameLayout) parent).indexOfChild(this));
            } else if (parent instanceof RelativeLayout) {
                ((RelativeLayout) parent).addView(
                        touchGuard, ((RelativeLayout) parent).indexOfChild(this),
                        new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
            } else {
                Log.d(TAG, "touchGuard requires that the parent of this FabSpeedDialer be a FrameLayout or RelativeLayout");
            }
        }

        setOnClickListener(this);
    }

    private void newNavigationMenu() {
        navigationMenu = new NavigationMenu(getContext());
        new SupportMenuInflater(getContext()).inflate(menuId, navigationMenu);

        navigationMenu.setCallback(new MenuBuilder.Callback() {
            @Override
            public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
                return menuListener != null && menuListener.onMenuItemSelected(item);
            }

            @Override
            public void onMenuModeChange(MenuBuilder menu) {
            }
        });
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    @Override
    public void onClick(View v) {
        fab.setSelected(false);
        startHideAnimations();

        if (menuListener != null) {
            if (v == this || v == touchGuard) {
                menuListener.onMenuClosed();
            } else if (v instanceof FloatingActionButton) {
                menuListener.onMenuItemSelected(fabMenuItemMap.get(v));
            } else if (v instanceof CardView) {
                menuListener.onMenuItemSelected(cardViewMenuItemMap.get(v));
            }
        } else {
            Log.d(TAG, "You haven't provided a MenuListener.");
        }
    }

    public void setMenuListener(MenuListener menuListener) {
        this.menuListener = menuListener;
    }


    private View createFabMenuItem(MenuItem menuItem) {
        ViewGroup fabMenuItem = (ViewGroup) LayoutInflater.from(getContext())
                .inflate(getMenuItemLayoutId(), this, false);

        FloatingActionButton miniFab = (FloatingActionButton) fabMenuItem.findViewById(R.id.mini_fab);
        //CardView cardView = (CardView) fabMenuItem.findViewById(R.id.card_view);
        TextView titleView = (TextView) fabMenuItem.findViewById(R.id.title_view);
        miniFab.setImageDrawable(menuItem.getIcon());
        fabMenuItemMap.put(miniFab, menuItem);
        //cardViewMenuItemMap.put(cardView, menuItem);

        //miniFab.setBackgroundTintList(fabBackgroundTint);
        miniFab.setImageDrawable(menuItem.getIcon());
        miniFab.setOnClickListener(this);
        //cardView.setOnClickListener(this);

        ViewCompat.setAlpha(miniFab, 0f);
        //ViewCompat.setAlpha(cardView, 0f);

        final CharSequence title = menuItem.getTitle();
        if (!TextUtils.isEmpty(title) && miniFabTitlesEnabled) {
            miniFab.setBackgroundColor(miniFabTitleBackgroundTint.getDefaultColor());
            //cardView.setCardBackgroundColor(miniFabTitleBackgroundTint.getDefaultColor());
            titleView.setText(title);
            titleView.setTypeface(null, Typeface.BOLD);
            titleView.setTextColor(miniFabTitleTextColor);
        } else {
            //fabMenuItem.removeView(cardView);
        }

        miniFab.setBackgroundTintList(miniFabBackgroundTint);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            miniFab.setImageTintList(miniFabDrawableTint);
        }

        return fabMenuItem;
    }

    private int getMenuItemLayoutId() {
        if (isGravityEnd()) {
            return R.layout.fab_menu_item_end;
        } else {
            return R.layout.fab_menu_item_start;
        }
    }

    private boolean isGravityBottom() {
        return fabGravity == BOTTOM_END || fabGravity == BOTTOM_START;
    }

    private boolean isGravityEnd() {
        return fabGravity == BOTTOM_END || fabGravity == TOP_END;
    }

    private ColorStateList getColorStateList(int colorRes) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_enabled}, // enabled
                new int[]{-android.R.attr.state_enabled}, // disabled
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_pressed}  // pressed
        };

        int color = ContextCompat.getColor(getContext(), colorRes);

        int[] colors = new int[]{color, color, color, color};
        return new ColorStateList(states, colors);
    }

    @Override
    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (menuItemsLayout.getChildCount() > 0
                && event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_UP
                && event.getRepeatCount() == 0) {
            fab.setSelected(false);
            startHideAnimations();
            return true;
        }

        return super.dispatchKeyEventPreIme(event);
    }

    /**
     * Dim the background layout.
     */
    private void dimAnimationLayout() {
        if (animationLayout == null) {
            animationLayout = createAnimationLayout();
            animationLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (animationPlaying) return;
                    if (cancelable) startHideAnimations();
                }
            });
        }
        animationLayout.setVisibility(VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(animationLayout, "backgroundColor",
                DimType.DIM_0.value,
                dimType.value)
                .setDuration(duration + delay * (menuItemNum - 1));
        objectAnimator.setEvaluator(new ArgbEvaluator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                //state = StateType.OPENING;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //state = StateType.OPEN;
            }
        });
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //animatorListener.showing(animation.getAnimatedFraction());
            }
        });
        objectAnimator.start();
    }

    private ViewGroup createAnimationLayout() {
        ViewGroup rootView = (ViewGroup) ((Activity)mContext).getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(layoutParams);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * Put the sub button to the background layout.
     *
     * @param view The sub button.
     * @param location Location in background layout.
     * @return The sub button after set.
     */
    private View setViewLocationInAnimationLayout(final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = null;
        lp = new LinearLayout.LayoutParams(buttonWidth, buttonWidth);
        lp.leftMargin = x;
        lp.topMargin = y;
        animationLayout.addView(view, lp);
        return view;
    }


    private void startShowAnimations() {
        if (animationLayout != null) animationLayout.removeAllViews();

            getEndLocations();
        this.menuItemNum = navigationMenu.size();
        for (int i = 0; i < navigationMenu.size(); i++) {
            MenuItem menuItem = navigationMenu.getItem(i);
            if (menuItem.isVisible()) {
                View v = createFabMenuItem(menuItem);
                fab.getLocationOnScreen(startLocations[i]);
                startLocations[i][0] -= (buttonWidth) / 2;
                startLocations[i][1] -= (buttonWidth) / 2;
                setShowAnimation(v, startLocations[i], endLocations[i], i);
            }
        }
    }

    private void getEndLocations() {
        int width = Util.getInstance().getScreenWidth(mContext);
        int height = Util.getInstance().getScreenHeight(mContext);
        switch (navigationMenu.size()) {
            case 1:
                endLocations[0][0] = width / 2 - buttonWidth / 2;
                endLocations[0][1] = height / 2 - buttonWidth / 2;
                break;
            case 2:
                //if (placeType.equals(PlaceType.CIRCLE_2_1)) {
                    endLocations[0][0] = width / 3 - buttonWidth / 2;
                    endLocations[0][1] = height / 2 - buttonWidth / 2;
                    endLocations[1][0] = width * 2 / 3 - buttonWidth / 2;
                    endLocations[1][1] = height / 2 - buttonWidth / 2;
                //}
                /*if (placeType.equals(PlaceType.CIRCLE_2_2)) {
                    endLocations[0][0] = width / 2 - buttonWidth / 2;
                    endLocations[0][1] = height / 3 - buttonWidth / 2;
                    endLocations[1][0] = width / 2 - buttonWidth / 2;
                    endLocations[1][1] = height * 2 / 3 - buttonWidth / 2;
                }*/
                break;
            case 3:
                //if (placeType.equals(PlaceType.CIRCLE_3_1)) {
                    int dis = buttonWidth * 9 / 8;
                    endLocations[0][0] = width / 2 - dis - buttonWidth / 2;
                    endLocations[0][1] = height / 2 - buttonWidth / 2;
                    endLocations[1][0] = width / 2 - buttonWidth / 2;
                    endLocations[1][1] = height / 2 - buttonWidth / 2;
                    endLocations[2][0] = width / 2 + dis - buttonWidth / 2;
                    endLocations[2][1] = height / 2 - buttonWidth / 2;
                //}
            default:
                break;
        }
    }
    /**
     * Set show animation of each sub button.
     *
     * @param button The sub button.
     * @param startLocation Start location of the animation.
     * @param endLocation End location of the animation.
     * @param index Index of the sub button in the array.
     */
    public void setShowAnimation(
            final View button,
            int[] startLocation,
            int[] endLocation,
            final int index) {

        button.bringToFront();

        final View view = setViewLocationInAnimationLayout(button, startLocation);

        float[] sl = new float[2];
        float[] el = new float[2];
        sl[0] = startLocation[0] * 1.0f;
        sl[1] = startLocation[1] * 1.0f;
        el[0] = endLocation[0] * 1.0f;
        el[1] = endLocation[1] * 1.0f;

        Log.d("createFabMenuItem ", "" + " sw, sh: " + sl[0] + ", " + sl[1] + " ew, eh : " +  el[0] + ", " + el[1]);

        float[] xs = new float[frames + 1];
        float[] ys = new float[frames + 1];
        getShowXY(sl, el, xs, ys);

        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(view, "x", xs).setDuration(duration);
        xAnimator.setStartDelay(delay * index);
        xAnimator.setInterpolator(InterpolatorFactory.getInterpolator(showMoveEaseType));
        xAnimator.start();

        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(view, "y", ys).setDuration(duration);
        yAnimator.setStartDelay(delay * index);
        yAnimator.setInterpolator(InterpolatorFactory.getInterpolator(showMoveEaseType));
        yAnimator.start();

        // scale animation
        float scaleW = 0;
        float scaleH = 0;
        scaleW = dotWidth * 1.0f / buttonWidth;
        scaleH = dotWidth * 1.0f / buttonWidth;

        view.setScaleX(scaleW);
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(view, "scaleX",
                scaleW,
                1f).setDuration(duration);
        scaleXAnimator.setStartDelay(delay * index);
        scaleXAnimator.setInterpolator(InterpolatorFactory.getInterpolator(showScaleEaseType));
        scaleXAnimator.start();

        view.setScaleY(scaleH);
        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(view, "scaleY",
                scaleH,
                1f).setDuration(duration);
        scaleYAnimator.setStartDelay(delay * index);
        scaleYAnimator.setInterpolator(InterpolatorFactory.getInterpolator(showScaleEaseType));
        scaleYAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                animationPlaying = false;
            }
        });
        scaleYAnimator.start();

        // alpha animation
        View view1 = null;
        View view2 = null;
        view1 = view.findViewById(R.id.mini_fab);
        view2 = view.findViewById(R.id.title_view);

        ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(view1, "alpha",
                0f,
                1f).setDuration(duration);
        alphaAnimator1.setStartDelay(delay * index);
        alphaAnimator1.setInterpolator(InterpolatorFactory.getInterpolator(showMoveEaseType));
        alphaAnimator1.start();
        ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(view2, "alpha",
                1f,
                1f).setDuration(duration);
        alphaAnimator2.setStartDelay(delay * index);
        alphaAnimator2.setInterpolator(InterpolatorFactory.getInterpolator(showMoveEaseType));
        alphaAnimator2.start();

        // rotation animation

            /*ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(
                    view.getFrameLayout(), "rotation",
                    0,
                    rotateDegree).setDuration(duration);
            rotateAnimator.setStartDelay(delay * index);
            rotateAnimator.setInterpolator(InterpolatorFactory.getInterpolator(showRotateEaseType));
            rotateAnimator.start();*/

    }

    /**
     * Get the function of the road of the animation of showing.
     * Then calculate each points to be ready for the animation.
     *
     * @param startPoint Start point of the animation.
     * @param endPoint End point of the animation.
     * @param xs The values on the x axis.
     * @param ys The values on the y axis.
     */
    private void getShowXY(float[] startPoint, float[] endPoint, float[] xs, float[] ys) {
        float x1 = startPoint[0];
        float y1 = startPoint[1];
        float x2 = endPoint[0];
        float y2 = endPoint[1];
        float k = (y2 - y1) / (x2 - x1);
        float b = y1 - x1 * k;

        float per = 1f / frames;
        float xx = endPoint[0] - startPoint[0];
        for (int i = 0; i <= frames; i++) {
            float offset = i * per;
            xs[i] = startPoint[0] + offset * xx;
            ys[i] = k * xs[i] + b;
        }
    }

    /**
     * Get the function of the road of the animation of dismissing.
     * Then calculate each points to be ready for the animation.
     *
     * @param startPoint Start point of the animation.
     * @param endPoint End point of the animation.
     * @param xs The values on the x axis.
     * @param ys The values on the y axis.
     */
    private void getHideXY(float[] startPoint, float[] endPoint, float[] xs, float[] ys) {
        float x1 = startPoint[0];
        float y1 = startPoint[1];
        float x2 = endPoint[0];
        float y2 = endPoint[1];
        float k = (y2 - y1) / (x2 - x1);
        float b = y1 - x1 * k;

        float per = 1f / frames;
        float xx = endPoint[0] - startPoint[0];
        for (int i = 0; i <= frames; i++) {
            float offset = i * per;
            xs[i] = startPoint[0] + offset * xx;
            ys[i] = k * xs[i] + b;
        }
    }

    /**
     * Start all animations about dismissing.
     */
    private void startHideAnimations() {
        animationPlaying = true;
        lightAnimationLayout();
        for (int i = 0; i < menuItemNum; i++) {
            View fabMenuItem = animationLayout.getChildAt(i);
            setHideAnimation(fabMenuItem, endLocations[i], startLocations[i], i);
        }
    }

    /**
     * Set hide animation of each sub button.
     *
     * @param button The sub button.
     * @param startLocation Start location of the animation.
     * @param endLocation End location of the animation.
     * @param index Index of the sub button in the array.
     */
    public void setHideAnimation(
            final View button,
            int[] startLocation,
            int[] endLocation,
            int index) {

        // position animation
        float[] sl = new float[2];
        float[] el = new float[2];
        sl[0] = startLocation[0] * 1.0f;
        sl[1] = startLocation[1] * 1.0f;
        el[0] = endLocation[0] * 1.0f;
        el[1] = endLocation[1] * 1.0f;

        float[] xs = new float[frames + 1];
        float[] ys = new float[frames + 1];
        getHideXY(sl, el, xs, ys);

        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(button, "x", xs).setDuration(duration);
        xAnimator.setStartDelay(index * delay);
        xAnimator.setInterpolator(InterpolatorFactory.getInterpolator(hideMoveEaseType));
        xAnimator.start();

        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(button, "y", ys).setDuration(duration);
        yAnimator.setStartDelay(index * delay);
        yAnimator.setInterpolator(InterpolatorFactory.getInterpolator(hideMoveEaseType));
        yAnimator.start();

        // scale animation
        float scaleW = 0;
        float scaleH = 0;
        scaleW = dotWidth * 1.0f / buttonWidth;
        scaleH = dotWidth * 1.0f / buttonWidth;

        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(button, "scaleX",
                1f,
                scaleW).setDuration(duration);
        scaleXAnimator.setStartDelay(index * delay);
        scaleXAnimator.setInterpolator(InterpolatorFactory.getInterpolator(hideScaleEaseType));
        scaleXAnimator.start();

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(button, "scaleY",
                1f,
                scaleH).setDuration(duration);
        scaleYAnimator.setStartDelay(index * delay);
        scaleYAnimator.setInterpolator(InterpolatorFactory.getInterpolator(hideScaleEaseType));
        scaleYAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        scaleYAnimator.start();

        // alpha animation
        View view1 = null;
        View view2 = null;

        view1 = button.findViewById(R.id.mini_fab);
        view2 = button.findViewById(R.id.title_view);

        ObjectAnimator alphaAnimator1 = ObjectAnimator.ofFloat(view1, "alpha",
                1f,
                0f).setDuration(duration);
        alphaAnimator1.setStartDelay(delay * index);
        alphaAnimator1.setInterpolator(InterpolatorFactory.getInterpolator(hideMoveEaseType));
        alphaAnimator1.start();
        ObjectAnimator alphaAnimator2 = ObjectAnimator.ofFloat(view2, "alpha",
                1f,
                0f).setDuration(duration);
        alphaAnimator2.setStartDelay(delay * index);
        alphaAnimator2.setInterpolator(InterpolatorFactory.getInterpolator(hideMoveEaseType));
        alphaAnimator2.start();

        // rotation animation
        /*if (button instanceof CircleButton) {
            ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(
                    ((CircleButton) button).getFrameLayout(), "rotation",
                    0,
                    -rotateDegree).setDuration(duration);
            rotateAnimator.setStartDelay(index * delay);
            rotateAnimator.setInterpolator(InterpolatorFactory.getInterpolator(hideRotateEaseType));
            rotateAnimator.start();
        }*/
    }

    /**
     * Light the background, used when the boom menu button is to dismiss.
     */
    public void lightAnimationLayout() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(animationLayout, "backgroundColor",
                dimType.value,
                DimType.DIM_0.value)
                .setDuration(duration + delay * (menuItemNum - 1));
        objectAnimator.setEvaluator(new ArgbEvaluator());
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //animationLayout.removeAllViews();
                fab.setSelected(false);
                animationLayout.setVisibility(GONE);
                animationPlaying = false;
                /*if (animatorListener != null) animatorListener.hided();
                state = StateType.CLOSED;*/
            }
        });
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /*if (animatorListener != null) animatorListener.hiding(animation.getAnimatedFraction());*/
            }
        });
        objectAnimator.start();
    }
}