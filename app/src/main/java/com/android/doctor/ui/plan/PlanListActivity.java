package com.android.doctor.ui.plan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.android.doctor.R;
import com.yuntongxun.kitsdk.view.ECProgressDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 联系人选择页面
 *
 */
public class PlanListActivity extends AppCompatActivity  {
    private ECProgressDialog mPostingdialog;
    private static final String TAG = "PlanListActivity";
    private static final String TAB1 = "state_all";
    private static final String TAB2 = "state_ing";
    private static final String TAB3 = "state_finished";
    private FragmentTabHost mTabHost;
    private ActionBar mActionBar;

    @InjectView(R.id.rdbtn_all)
    protected RadioButton mRdbAll;
    @InjectView(R.id.rdbtn_ing)
    protected RadioButton mRdbIng;
    @InjectView(R.id.rdbtn_finished)
    protected RadioButton mRdbFinished;
    @InjectView(R.id.iv_clear)
    protected ImageView mIvClear;
    @InjectView(R.id.edt_search_box)
    protected EditText mEdtSearch;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_visit_list);
        ButterKnife.inject(this);
        init();
        initView();
    }

    protected void init() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle b = intent.getExtras();
            if (b != null) {
                //parentId = b.getString("pid");
                //childId = b.getString("cid");
                //item = b.getParcelable("item");
                //LogUtil.d(LogUtil.getLogUtilsTag(ActivityActivityContactList.class), item.toJson());
            }
        }
    }

    protected void initView() {
        setActionBar();

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        Bundle b1 = new Bundle();
        Bundle b2 = new Bundle();
        Bundle b3 = new Bundle();
        b1.putInt(FragmentPlanList.EXTRA_PARAM, FragmentPlanList.TYPE_STATE_ALL);
        b2.putInt(FragmentPlanList.EXTRA_PARAM, FragmentPlanList.TYPE_STATE_ING);
        b3.putInt(FragmentPlanList.EXTRA_PARAM, FragmentPlanList.TYPE_STATE_FINISHED);

        mTabHost.addTab(mTabHost.newTabSpec(TAB1).setIndicator(TAB1), FragmentPlanList.class, b1);
        mTabHost.addTab(mTabHost.newTabSpec(TAB2).setIndicator(TAB2), FragmentPlanList.class, b2);
        mTabHost.addTab(mTabHost.newTabSpec(TAB3).setIndicator(TAB3), FragmentPlanList.class, b3);
        mTabHost.setCurrentTabByTag(TAB1);
        //mRdoGrp = (RadioGroup) findViewById(R.id.rdg_btn);
        //mRdoGrp.setOnCheckedChangeListener(this);
        //onActivityCreate();
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
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
    protected void setTab1() {
        if (mRdbAll.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB1);
        }
    }

    @OnCheckedChanged(R.id.rdbtn_ing)
    protected void setTab2() {
        if (mRdbIng.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB2);
        }
    }

    @OnCheckedChanged(R.id.rdbtn_finished)
    protected void setTab3() {
        if (mRdbFinished.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB3);
        }
    }

    @OnTextChanged(R.id.edt_search_box)
    public void onEdtTextChange(CharSequence s, int start, int before, int count) {
        mIvClear.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
    }

    @OnClick(R.id.iv_clear)
    public void onClear() {
        mEdtSearch.setText("");
    }

}
