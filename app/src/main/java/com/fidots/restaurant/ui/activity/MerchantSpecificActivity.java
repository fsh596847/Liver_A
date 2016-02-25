package com.fidots.restaurant.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.fragment.FragmentMerchantSpecificProduct;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-18.
 */
public class MerchantSpecificActivity extends BaseActivity{

    @InjectView(R.id.img_merchant)
    protected ImageView mImgMerchant;
    @InjectView(R.id.tv_location)
    protected TextView mTvLocation;
    @InjectView(R.id.tv_phone)
    protected TextView mTvPhone;
    @InjectView(R.id.tv_business_hour)
    protected TextView mTvBusinessHour;
   // @InjectView(R.id.fl_specific_container)
    protected FrameLayout mFlContainer;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.item_specific_merchant_base);
        ButterKnife.inject(this);
    }

    @Override
    protected void initView() {
        setActionBar("");
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        FragmentMerchantSpecificProduct f = new FragmentMerchantSpecificProduct();
        //trans.replace(R.id.fl_specific_container, f);
        trans.commit();
    }

}
