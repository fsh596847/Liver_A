package com.android.doctor.ui.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.AnimUtils;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.Constants;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.yuntongxun.kitsdk.utils.LogUtil;
import com.yuntongxun.kitsdk.view.ECProgressDialog;
import com.yuntongxun.kitsdk.view.TopBarView;

import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * 联系人选择页面
 *
 */
public class ContactActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String ARG_REQ_CODE = "ARG_REQ_CODE";
    private ECProgressDialog mPostingdialog;
    private static final String TAG = "ContactActivity";
    private static final String TAB1 = "doctor";
    private static final String TAB2 = "patient";
    private static final String TAB3 = "group";
    private FragmentTabHost mTabHost;
    private ActionBar mActionBar;

    @InjectView(R.id.rdbtn_patient)
    protected RadioButton mRdbPatient;
    @InjectView(R.id.rdbtn_doctor)
    protected RadioButton mRdbDoctor;
    @InjectView(R.id.rdbtn_group)
    protected RadioButton mRdbGroup;
    @InjectView(R.id.imgbtn_add)
    protected View mViewAdd;
    @InjectView(R.id.iv_clear)
    protected ImageView mIvClear;
    @InjectView(R.id.edt_search_box)
    protected EditText mEdtSearch;
    @InjectView(R.id.rl_search_view)
    protected RelativeLayout mRlSearchView;

    /**查看群组*/
    public static final int REQUEST_CODE_VIEW_GROUP_OWN = 0x2a;
    public static final int REQUEST_CODE_CREATE_GROUP = 0x2b;
    public static final int REQUEST_CODE_FOR_NEW_CONVERSATION = 100;
    public static final int REQUEST_CODE_FOR_ADD_MEMBER = 101;

    private TopBarView mTopBarView;
    private boolean mNeedResult;
    private boolean mShowGroup;
    public int mRCode;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_contact);
        ButterKnife.inject(this);
        init();
        initView();
    }

    public static void startAty(Activity context, int reqcode) {
        Intent intent = new Intent(context, ContactActivity.class);
        intent.putExtra(ARG_REQ_CODE, reqcode);
        context.startActivityForResult(intent, reqcode);
    }

    protected void init() {
        Intent intent = getIntent();
        if (intent != null) {
            mRCode = intent.getIntExtra(ARG_REQ_CODE, 0);
            Log.d(TAG, "[ContactActivity-> init] " + mRCode);
        }
    }

    protected void initView() {
        setActionBar();

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        Bundle b1 = new Bundle();
        Bundle b2 = new Bundle();
        Bundle b3 = new Bundle();
        b1.putInt(FragmentContactList.EXTRA_TYPE, Constants.CONTACT_TYPE_DOCTOR);
        b2.putInt(FragmentContactList.EXTRA_TYPE, Constants.CONTACT_TYPE_PATIENT);
        b3.putInt(FragmentContactList.EXTRA_TYPE, Constants.CONTACT_TYPE_GROUP);

        mTabHost.addTab(mTabHost.newTabSpec(TAB1).setIndicator(TAB1), FragmentContactList.class, b1);
        mTabHost.addTab(mTabHost.newTabSpec(TAB2).setIndicator(TAB2), FragmentContactList.class, b2);
        mTabHost.addTab(mTabHost.newTabSpec(TAB3).setIndicator(TAB3), FragmentContactList.class, b3);
        mTabHost.setCurrentTabByTag(TAB1);
        setTab1();
        //mRdoGrp = (RadioGroup) findViewById(R.id.rdg_btn);
        //mRdoGrp.setOnCheckedChangeListener(this);
        //onActivityCreate();
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        //mActionBar.setIcon(R.color.transparent);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(false);

    }

    private Fragment getFragmentByTag(String tag) {
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    public int getRCode() {
        return mRCode;
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

    @OnCheckedChanged(R.id.rdbtn_patient)
    protected void setTab1() {
        if (mRdbPatient.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB2);
            //switchMenu(View.GONE);
        }
    }

    @OnCheckedChanged(R.id.rdbtn_doctor)
    protected void setTab2() {
        if (mRdbDoctor.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB1);
            //switchMenu(View.GONE);
        }
    }

    @OnCheckedChanged(R.id.rdbtn_group)
    protected void setTab3() {
        if (mRdbGroup.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB3);
            //switchMenu(View.VISIBLE);
        }
    }

    @OnTextChanged(R.id.edt_search_box)
    public void onEdtTextChange(CharSequence s, int start, int before, int count) {
        mIvClear.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
    }

    @OnClick(R.id.iv_clear)
    public void onClear() {
        mEdtSearch.setText("");
        Fragment curFragment = getFragmentByTag(mTabHost.getCurrentTabTag());
        if (curFragment != null && curFragment.getClass().equals(FragmentContactList.class)) {
            FragmentContactList frag = (FragmentContactList) curFragment;
            frag.onClearSearch();
        }
    }

    @OnEditorAction(R.id.edt_search_box)
    protected boolean onSearch(TextView v, int actionId,
                               KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String word = mEdtSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(word)) {
                DeviceHelper.hideSoftKeyboard(v);
                Fragment curFragment = getFragmentByTag(mTabHost.getCurrentTabTag());
                if (curFragment != null && curFragment.getClass().equals(FragmentContactList.class)) {
                    FragmentContactList frag = (FragmentContactList) curFragment;
                    frag.onSearch( word);
                }
            }
            return true;
        }
        return false;
    }

    public void showSearchView() {
        if (mRlSearchView.getVisibility() != View.VISIBLE) {
            mRlSearchView.setVisibility(View.VISIBLE);
            //AnimUtils.expand(mRlSearchView);
        }
    }

    public void hideSearchView() {
        if (mRlSearchView.getVisibility() != View.GONE) {
            mRlSearchView.setVisibility(View.GONE);
            //AnimUtils.collapse(mRlSearchView);
        }
    }

    private void switchMenu(int v) {
        mViewAdd.setVisibility(v);
    }

    @OnClick(R.id.imgbtn_add)
    protected void onAdd() {
        int tab = mTabHost.getCurrentTab();
        if (tab == 0 || tab == 1) {
            UIHelper.showtAty(this, AddContactActivity.class);
            return;
        }
        DialogUtils.showBottomListDialog(this,
                Arrays.asList(getResources().getStringArray(R.array.group_option_menu)),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        if (position == 0) {
                            EditGroupActivity.startAtyForCreate(ContactActivity.this, REQUEST_CODE_CREATE_GROUP);
                        } else if (position == 1) {
                            UIHelper.showtAty(ContactActivity.this, SearchGroupActivity.class);
                        }
                    }
                }, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                //hideSoftKeyboard();
                finish();
                break;
            case R.id.text_right:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 创建一个私有群组
     * @param split
     */
    private void postCreatePrivateChatroom(String[] split) {
        mPostingdialog = new ECProgressDialog(this, R.string.create_chatroom_posting);
        mPostingdialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d(TAG, "[ContactActivity -> onActivityResult]: requestCode=" + requestCode
                + ", resultCode=" + resultCode + ", data=" + data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CREATE_GROUP) {
                Fragment f = getFragmentByTag(TAB3);
                if (f != null && f.getClass().equals(FragmentContactList.class)) {
                    ((FragmentContactList)f).onRefresh();
                }
            }
        }
    }


    /**
     * 关闭对话框
     */
    private void dismissPostingDialog() {
        if(mPostingdialog == null || !mPostingdialog.isShowing()) {
            return ;
        }
        mPostingdialog.dismiss();
        mPostingdialog = null;
    }
}
