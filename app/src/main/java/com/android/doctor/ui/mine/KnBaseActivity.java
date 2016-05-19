package com.android.doctor.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-18.
 */
public class KnBaseActivity extends BaseActivity {

    private static final String tabs[] = {"订阅", "更多"};
    @InjectView(R.id.viewPager)
    protected ViewPager mViewPager;
    @InjectView(R.id.tabLayout)
    protected TabLayout mTabLayout;

    private TabFragmentPagerAdapter mFragmentAdapter;

    public KnBaseActivity() {}

    public static void startAty(Context c) {
        Intent intent = new Intent();
        intent.setClass(c, KnBaseActivity.class);
        c.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_kn_base);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.knowledge_repo);
        initTabs();
    }

    private void initTabs() {
        mFragmentAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), this);
        mFragmentAdapter.setmData(Arrays.asList(tabs));
        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(tabs.length);
        mViewPager.setCurrentItem(0);
        mTabLayout.setupWithViewPager(mViewPager);
        //mTabLayout.removeAllTabs();
        for (int i = 0; i < tabs.length; ++i) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            tab.setText(tabs[i]);
        }
    }

    class TabFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<String> mData;
        private Context context;

        public TabFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;
        }

        @Override
        public Fragment getItem(int position) {
            if (mData == null) return null;
            if (position == 0) return new FragmentSubjectList();
            else if (position == 1) return new FragmentSubsMoreList();
            return null;
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mData == null ? "" : mData.get(position) == null ? null : mData.get(position);
        }

        public void setmData(List<String> mData) {
            this.mData = mData;
        }
    }
}
