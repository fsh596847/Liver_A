package com.fidots.restaurant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.fidots.restaurant.R;
import com.fidots.restaurant.helper.UIHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016-02-12.
 */
public class FragmentLocalYellowPage extends Fragment{
    @InjectView(R.id.ll_food)
    protected LinearLayout mLlayoutFood;
    @InjectView(R.id.ll_hotel)
    protected LinearLayout mLlayoutHotel;
    @InjectView(R.id.ll_travel_ticket)
    protected LinearLayout mLlayoutTravelTicket;
    @InjectView(R.id.ll_entertainment)
    protected LinearLayout mLlayoutEntertainment;
    @InjectView(R.id.ll_public_service)
    protected LinearLayout mLlayoutPublicService;
    @InjectView(R.id.ll_house_service)
    protected LinearLayout mLlayoutHouseService;
    @InjectView(R.id.ll_auto_repair)
    protected LinearLayout mLlayoutAutoRepair;
    @InjectView(R.id.ll_my_favorite)
    protected LinearLayout mLlayoutMyFavorite;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_local_yellow_page, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        initView(view);
    }

    private void initView(View view) {
        ((TextView)view.findViewById(R.id.toolbar_title)).setText(R.string.local_page);
    }

    @OnClick(R.id.ll_my_favorite)
    protected void showMyFavoriteAty() {
        UIHelper.showMyFavoriteAty(getContext());
    }
}
