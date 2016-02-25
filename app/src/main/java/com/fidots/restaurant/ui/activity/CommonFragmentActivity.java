package com.fidots.restaurant.ui.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.fragment.FragmentSpecificMerchantInfo;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-18.
 */
public class CommonFragmentActivity extends BaseActivity{
    public static String EXTRA_FRAGMENT_TYPE = "FRAGMENT_TYPE";
    public static String FRAGMENT_TYPE_CHARITY_NEWS_LIST = "FRAGMENT_CHARITY_NEWS_LIST";
    public static String FRAGMENT_TYPE_CHARITY_PROJECT_LIST = "FRAGMENT_CHARITY_PROJECT_LIST";
    public static String FRAGMENT_TYPE_CHARITY_NEWS_DETAIL = "FRAGMENT_CHARITY_NEWS_DETAIL";
    public static String FRAGMENT_TYPE_INIT_PROJECT = "FRAGMENT_INIT_PROJECT";
    public static String FRAGMENT_TYPE_PROJECT_DETAIL = "FRAGMENT_PROJECT_DETAIL";
    public static String FRAGMENT_TYPE_PROJECT_GRAPHIC_DETAIL = "FRAGMENT_PROJECT_GRAPHIC_DETAIL";
    public static String FRAGMENT_TYPE_USER_SETTING = "FRAGMENT_USER_SETTING";

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;
    private String mType;

    public CommonFragmentActivity() {

    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_fragment_container);
        ButterKnife.inject(this);
    }

    @Override
    protected void initView() {
        mType = getIntent().getStringExtra(EXTRA_FRAGMENT_TYPE);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        FragmentSpecificMerchantInfo f = new FragmentSpecificMerchantInfo();
        trans.replace(R.id.fl_container, f);
        trans.commit();
    }

}
