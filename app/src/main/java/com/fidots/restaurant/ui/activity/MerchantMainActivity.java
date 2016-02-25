package com.fidots.restaurant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.fragment.MerchantListFragment;
import com.fidots.restaurant.ui.fragment.TodayPromotionFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;

public class MerchantMainActivity extends AppCompatActivity{
	
	public static final String TAB_ALL_MERCHANT = "tab_all_merchant";
	public static final String TAB_TODAY_PROMOTION = "tab_today_promotion";
	
	private FragmentTabHost mTabHost;
	private ActionBar mActionBar;
    @InjectView(R.id.rdg_btn)
	protected RadioGroup mRdoGrp;
    @InjectView(R.id.rdbtn_all)
    protected RadioButton mRdbAll;
    @InjectView(R.id.rdbtn_promotion)
    protected RadioButton mRdbToday;
	public MerchantMainActivity() {}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_tabhost_merchant);
		setActionBar();
		initView();
	}
	
	private void initView() {
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_ALL_MERCHANT).setIndicator(TAB_ALL_MERCHANT), MerchantListFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec(TAB_TODAY_PROMOTION).setIndicator(TAB_TODAY_PROMOTION), TodayPromotionFragment.class, null);
        mTabHost.setCurrentTabByTag(TAB_ALL_MERCHANT);
		//mRdoGrp = (RadioGroup) findViewById(R.id.rdg_btn);
		//mRdoGrp.setOnCheckedChangeListener(this);
        ButterKnife.inject(this);
	}
	
	private void setActionBar() {
		//
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		mActionBar = getSupportActionBar();
		//mActionBar.setIcon(R.color.transparent);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowTitleEnabled(false);
		//mActionBar.setDisplayShowCustomEnabled(true);
		//mActionBar.setElevation(0);
		//ActionBarUtil.ChangeActionBarHomeUpDrawable(this,R.drawable.icon_fanhui);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		//inflater.inflate(R.menu.promotion_manage_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnCheckedChanged(R.id.rdbtn_all)
	protected void setTabAllMerchant() {
        if (mRdbAll.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB_ALL_MERCHANT);
        }
	}

    @OnCheckedChanged(R.id.rdbtn_promotion)
    protected void setTab() {
        if (mRdbToday.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB_TODAY_PROMOTION);
        }
    }
}
