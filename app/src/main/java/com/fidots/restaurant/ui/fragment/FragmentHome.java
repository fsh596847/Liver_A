package com.fidots.restaurant.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.adapter.PopDownMenuAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016-02-12.
 */
public class FragmentHome extends Fragment {
    @InjectView(R.id.tv_city)
    protected TextView mTvCity;
    @InjectView(R.id.tv_country)
    protected TextView mTvCountry;
    @InjectView(R.id.fl_popup_menu)
    protected FrameLayout mFlPopupMenu;
    @InjectView(R.id.view_mask)
    protected View mViewMask;
    @InjectView(R.id.fl_recommend_new_shop)
    protected FrameLayout mFlRecommendShop;
    @InjectView(R.id.fl_category_business_dinners)
    protected FrameLayout mFlCateBusinessDinner;
    @InjectView(R.id.fl_category_flavour_snack)
    protected FrameLayout mFlCateFlavourSnack;
    @InjectView(R.id.fl_category_midnight_stall)
    protected FrameLayout mFlCateMidStall;
    @InjectView(R.id.fl_category_leisure_dinners)
    protected FrameLayout mFlCateLeisureDinners;
    @InjectView(R.id.fl_local_special_product)
    protected FrameLayout mFlLocalSpecial;

    private List<String> mCountryData;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        initView();
    }

    public void initView() {
        initContentView();
        initPopDownMenu();
    }

    private void initContentView() {
        FragmentNewShopRecommend fragmentNSR = new FragmentNewShopRecommend();
        FragmentDinnerCatagory fragmentCBD = new FragmentDinnerCatagory();
        FragmentDinnerCatagory fragmentCFS = new FragmentDinnerCatagory();
        FragmentDinnerCatagory fragmentCMS = new FragmentDinnerCatagory();
        FragmentDinnerCatagory fragmentCLD = new FragmentDinnerCatagory();
        FragmentLocalSpecificProduct fragmentLSP = new FragmentLocalSpecificProduct();

        FragmentManager childFragmentManager = getActivity().getSupportFragmentManager();
        //FragmentManager childFragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = childFragmentManager.beginTransaction();

        transaction.replace(R.id.fl_recommend_new_shop, fragmentNSR);
        transaction.replace(R.id.fl_category_business_dinners, fragmentCBD);
        transaction.replace(R.id.fl_category_flavour_snack, fragmentCFS);
        transaction.replace(R.id.fl_category_midnight_stall, fragmentCMS);
        transaction.replace(R.id.fl_category_leisure_dinners, fragmentCLD);
        transaction.replace(R.id.fl_local_special_product, fragmentLSP);
        transaction.commit();
    }

    private void initPopDownMenu() {
        mCountryData = new ArrayList<>();
        mCountryData.add("海淀区");
        mCountryData.add("朝阳区");
        mCountryData.add("东城区");
        mCountryData.add("西城区");
        mCountryData.add("昌平区");
        mCountryData.add("海淀区");
        mCountryData.add("海淀区");
        Context context = getContext();
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.popup_country_layout, null);
        GridView gridView = (GridView) view.findViewById(R.id.gv_country);
        PopDownMenuAdapter adapter = new PopDownMenuAdapter(context, mCountryData, R.layout.item_country, true);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                closePopDownMenu();
                mTvCountry.setText(mCountryData.get(position));
            }
        });
        mFlPopupMenu.addView(view);
    }


    private void closePopDownMenu() {
        mTvCountry.setCompoundDrawablesWithIntrinsicBounds(null, null,
                getResources().getDrawable(R.drawable.arrow_down), null);
        mFlPopupMenu.setVisibility(View.GONE);
        mFlPopupMenu.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
        mViewMask.setVisibility(View.GONE);
        mViewMask.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
    }

    private void showPopDownMenu() {
        mTvCountry.setCompoundDrawablesWithIntrinsicBounds(null, null,
                getResources().getDrawable(R.drawable.arrow_up), null);
        mFlPopupMenu.setVisibility(View.VISIBLE);
        mFlPopupMenu.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
        mViewMask.setVisibility(View.VISIBLE);
        mViewMask.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
    }

    @OnClick(R.id.tv_country)
    protected void switchMenu() {
        if (mFlPopupMenu.isShown()) {
            closePopDownMenu();
        } else {
           showPopDownMenu();
        }
    }

    @OnClick(R.id.view_mask)
    protected void onViewMaskClk() {
        closePopDownMenu();
    }
}
