package com.android.doctor.ui.plan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DeviceHelper;
import com.yuntongxun.kitsdk.view.ECProgressDialog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * 联系人选择页面
 *
 */
public class PlanListActivity extends AppCompatActivity  {

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
    @InjectView(R.id.header_view_frame)
    protected PtrClassicFrameLayout mPtrFrame;

    public static void startAty(Context context, String uid) {
        Intent intent = new Intent(context, PlanListActivity.class);
        intent.putExtra(FragmentDoctorPlanList.ARG_ID, uid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_visit_list);
        ButterKnife.inject(this);
        init();
        initView();
    }

    protected void init() {
    }

    protected void initView() {
        setActionBar();
        Intent intent = getIntent();
        String uid = intent.getStringExtra(FragmentDoctorPlanList.ARG_ID);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        mTabHost.addTab(mTabHost.newTabSpec(TAB1).setIndicator(TAB1), FragmentDoctorPlanList.class, getBundle(FragmentDoctorPlanList.TYPE_STATE_ALL, uid));
        mTabHost.addTab(mTabHost.newTabSpec(TAB2).setIndicator(TAB2), FragmentDoctorPlanList.class, getBundle(FragmentDoctorPlanList.TYPE_STATE_ING, uid));
        mTabHost.addTab(mTabHost.newTabSpec(TAB3).setIndicator(TAB3), FragmentDoctorPlanList.class, getBundle(FragmentDoctorPlanList.TYPE_STATE_FINISHED, uid));

        setTab1();
        initPRefreshView();
    }

    private void initPRefreshView() {
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return ((FragmentDoctorPlanList)getCurrentFragment()).isScrollTop();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPtrFrame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((FragmentDoctorPlanList)getCurrentFragment()).onRefresh();
                        mPtrFrame.refreshComplete();
                    }
                }, 500);
            }
        });
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);
    }

    private Bundle getBundle(int stat, String uid) {
        Bundle b = new Bundle();
        b.putString(FragmentDoctorPlanList.ARG_ID, uid);
        b.putInt(FragmentDoctorPlanList.ARG_TYPE, stat);
        return b;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag(
                mTabHost.getCurrentTabTag());
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

    @OnEditorAction(R.id.edt_search_box)
    protected boolean onSearch(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String word = mEdtSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(word)) {
                DeviceHelper.hideSoftKeyboard(v);
                ((FragmentDoctorPlanList)getCurrentFragment()).onSearch(word);
            }
            return true;
        }
        return false;
    }

    @OnClick(R.id.iv_clear)
    public void onClear() {
        mEdtSearch.setText("");
        ((FragmentDoctorPlanList)getCurrentFragment()).onClearSearch();
    }

}
