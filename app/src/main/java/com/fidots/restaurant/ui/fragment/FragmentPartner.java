package com.fidots.restaurant.ui.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.fragment.base.BaseFragment;

/**
 * Created by Yong on 2016-02-23.
 */
public class FragmentPartner extends BaseFragment{


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_partner;
    }

    @Override
    protected void initView(View view) {
        ((TextView)view.findViewById(R.id.toolbar_title)).setText(R.string.partner);
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        FragmentRestaurantDetails fr = new FragmentRestaurantDetails();
        trans.replace(R.id.fl_container, fr);
        trans.commit();
    }
}
