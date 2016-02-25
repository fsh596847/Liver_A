package com.fidots.restaurant.ui.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.adapter.PopDownMenuAdapter;
import com.fidots.restaurant.ui.adapter.SimpleListAdapter;
import com.fidots.restaurant.ui.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016-02-18.
 */
public class MerchantListFragment extends BaseFragment {

    @InjectView(R.id.img_hot_merchant)
    protected ImageView mImgHotMerchant;
    //@InjectView(R.id.tv_select_food_cata)
    protected TextView mTvSwitchFood;
    //@InjectView(R.id.tv_sort)
    protected TextView mTvSort;
    //@InjectView(R.id.tv_address_switch)
    protected TextView mTvAddrSwitch;
    @InjectView(R.id.ll_tab_menu)
    protected LinearLayout mLlTabMenu;
    @InjectView(R.id.fl_popup_menu)
    protected FrameLayout mPopupMenuViews;
    @InjectView(R.id.view_mask)
    protected View mMaskView;
    @InjectView(R.id.recyc_view)
    protected RecyclerView mMerchantList;
    private int current_tab_position = -1;
    //分割线颜色
    private int dividerColor = 0xffcccccc;
    //tab选中颜色
    private int textSelectedColor = 0xff890c85;
    //tab未选中颜色
    private int textUnselectedColor = 0xff111111;
    //遮罩颜色
    private int maskColor = 0x88888888;
    //tab字体大小
    private int menuTextSize = 14;

    private String tabs[] = {"美食", "排序", "位置切换"};

    private String foods[] = {"川菜", "云南菜", "江浙菜", "东北菜", "粤菜", "鲁菜"};

    private String sortType[] = {"按位置排序", "按好评排序"};

    private String locs[] = {"海淀区", "通州区", "房山区"};
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_merchant_list;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        //initTabView();
        initTab();
        initPopDownView();
    }

    private void initTabView() {
        for (int i = 0; i < tabs.length; ++i) {
            addTab(Arrays.asList(tabs), i);
        }
    }

    private void initTab() {
        for (int i = 0; i < mLlTabMenu.getChildCount(); i = i + 2) {
            final View view = mLlTabMenu.getChildAt(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchMenu(view);
                }
            });
        }
    }

    private void initPopDownView() {
        Context context = getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.popup_food_layout, null);
        ListView list1 = (ListView) view1.findViewById(R.id.list);

        PopDownMenuAdapter adapter1 = new PopDownMenuAdapter(context, Arrays.asList(foods), R.layout.item_select_food, false);
        list1.setAdapter(adapter1);

        View view2 = layoutInflater.inflate(R.layout.popup_food_layout, null);
        ListView list2 = (ListView) view2.findViewById(R.id.list);

        PopDownMenuAdapter adapter2 = new PopDownMenuAdapter(context, Arrays.asList(sortType), R.layout.item_select_food, false);
        list2.setAdapter(adapter2);

        View view3 = layoutInflater.inflate(R.layout.popup_food_layout, null);
        ListView list3 = (ListView) view3.findViewById(R.id.list);

        PopDownMenuAdapter adapter3 = new PopDownMenuAdapter(context, Arrays.asList(locs), R.layout.item_select_food, false);
        list3.setAdapter(adapter3);

        mPopupMenuViews.addView(view1, 0);
        mPopupMenuViews.addView(view2, 1);
        mPopupMenuViews.addView(view3, 2);
    }


    private void addTab(@NonNull List<String> tabTexts, int i) {
        final TextView tab = new TextView(getContext());
        tab.setSingleLine();
        tab.setEllipsize(TextUtils.TruncateAt.END);
        tab.setGravity(Gravity.CENTER);
        //tab.setTextSize(TypedValue.COMPLEX_UNIT_PX,menuTextSize);
        tab.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        tab.setTextColor(textUnselectedColor);
        tab.setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.arrow_down), null);
        tab.setText(tabTexts.get(i));
        tab.setPadding(dpTpPx(5), dpTpPx(12), dpTpPx(5), dpTpPx(12));
        //添加点击事件
        tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchMenu(tab);
            }
        });
        mLlTabMenu.addView(tab);
        //添加分割线
        if (i < tabTexts.size() - 1) {
            View view = new View(getContext());
            view.setLayoutParams(new LinearLayout.LayoutParams(dpTpPx(0.5f), ViewGroup.LayoutParams.MATCH_PARENT));
            view.setBackgroundColor(getResources().getColor(R.color.divider_color));
            mLlTabMenu.addView(view);
        }
    }

    @OnClick(R.id.view_mask)
    protected void onViewMaskClk() {
        closeMenu();
    }

    private void switchMenu(View target) {
        for (int i = 0; i < mLlTabMenu.getChildCount(); i = i + 2) {
            if (target == mLlTabMenu.getChildAt(i)) {
                if (current_tab_position == i) {
                    closeMenu();
                } else {
                    if (current_tab_position == -1) {
                        mPopupMenuViews.setVisibility(View.VISIBLE);
                        mPopupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
                        mMaskView.setVisibility(View.VISIBLE);
                        mMaskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
                        mPopupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    } else {
                        mPopupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                    }
                    current_tab_position = i;
                    ((TextView) mLlTabMenu.getChildAt(i)).setTextColor(textSelectedColor);
                    ((TextView) mLlTabMenu.getChildAt(i)).setCompoundDrawablesWithIntrinsicBounds(null, null,
                            getResources().getDrawable(R.drawable.arrow_up), null);
                }
            } else {
                ((TextView) mLlTabMenu.getChildAt(i)).setTextColor(textUnselectedColor);
                ((TextView) mLlTabMenu.getChildAt(i)).setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(R.drawable.arrow_down), null);
                mPopupMenuViews.getChildAt(i / 2).setVisibility(View.GONE);
            }
        }
    }

    /**
     * 改变tab文字
     *
     * @param text
     */
    public void setTabText(String text) {
        if (current_tab_position != -1) {
            ((TextView) mLlTabMenu.getChildAt(current_tab_position)).setText(text);
        }
    }

    public void setTabClickable(boolean clickable) {
        for (int i = 0; i < mLlTabMenu.getChildCount(); i = i + 2) {
            mLlTabMenu.getChildAt(i).setClickable(clickable);
        }
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (current_tab_position != -1) {
            ((TextView) mLlTabMenu.getChildAt(current_tab_position)).setTextColor(textUnselectedColor);
            ((TextView) mLlTabMenu.getChildAt(current_tab_position)).setCompoundDrawablesWithIntrinsicBounds(null, null,
                    getResources().getDrawable(R.drawable.arrow_down), null);
            mPopupMenuViews.setVisibility(View.GONE);
            mPopupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
            mMaskView.setVisibility(View.GONE);
            mMaskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
            current_tab_position = -1;
        }

    }

    public int dpTpPx(float value) {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, dm) + 0.5);
    }

}
