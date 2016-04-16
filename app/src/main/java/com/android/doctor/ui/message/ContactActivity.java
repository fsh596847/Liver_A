package com.android.doctor.ui.message;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.android.doctor.R;
import com.android.doctor.helper.DialogHelper;
import com.android.doctor.helper.UIHelper;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.yuntongxun.kitsdk.utils.LogUtil;
import com.yuntongxun.kitsdk.view.ECProgressDialog;
import com.yuntongxun.kitsdk.view.TopBarView;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 联系人选择页面
 *
 */
public class ContactActivity extends AppCompatActivity implements
        View.OnClickListener {
    private ECProgressDialog mPostingdialog;
    private static final String TAG = "ActivityActivityContactList";
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

    /**查看群组*/
    public static final int REQUEST_CODE_VIEW_GROUP_OWN = 0x2a;
    private TopBarView mTopBarView;
    private boolean mNeedResult;
    private boolean mShowGroup;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_contact);
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
        b1.putInt(FragmentTabContactList.EXTRA_TYPE, 2);
        b2.putInt(FragmentTabContactList.EXTRA_TYPE, 0);
        b3.putInt(FragmentTabContactList.EXTRA_TYPE, 1);

        mTabHost.addTab(mTabHost.newTabSpec(TAB1).setIndicator(TAB1), FragmentTabContactList.class, b1);
        mTabHost.addTab(mTabHost.newTabSpec(TAB2).setIndicator(TAB2), FragmentTabContactList.class, b2);
        mTabHost.addTab(mTabHost.newTabSpec(TAB3).setIndicator(TAB3), FragmentTabContactList.class, b3);
        mTabHost.setCurrentTabByTag(TAB1);
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

    @OnCheckedChanged(R.id.rdbtn_patient)
    protected void setTab1() {
        if (mRdbPatient.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB2);
            switchMenu(View.GONE);
        }
    }

    @OnCheckedChanged(R.id.rdbtn_doctor)
    protected void setTab2() {
        if (mRdbDoctor.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB1);
            switchMenu(View.GONE);
        }
    }

    @OnCheckedChanged(R.id.rdbtn_group)
    protected void setTab3() {
        if (mRdbGroup.isChecked()) {
            mTabHost.setCurrentTabByTag(TAB3);
            switchMenu(View.VISIBLE);
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

    private void switchMenu(int v) {
        mViewAdd.setVisibility(v);
    }

    @OnClick(R.id.imgbtn_add)
    protected void onGroup() {
        DialogHelper.showBottomListDialog(this,
                Arrays.asList(getResources().getStringArray(R.array.group_option_menu)),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        if (position == 0) {
                            UIHelper.showtAty(ContactActivity.this, CreateGroupActivity.class);
                        } else if (position == 1) {
                            UIHelper.showtAty(ContactActivity.this, SearchGroupActivity.class);
                        }
                    }
                }, null);
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fm = getSupportFragmentManager();

        mNeedResult = getIntent().getBooleanExtra("group_select_need_result", false);
        mShowGroup = getIntent().getBooleanExtra("select_type", true);
        // Create the list fragment and add it as our sole content.
        *//*if (fm.findFragmentById(R.id.contact_container) == null) {
            ContactListFragment list = ContactListFragment.newInstance(mShowGroup ? ContactListFragment.TYPE_SELECT : ContactListFragment.TYPE_NON_GROUP);
            fm.beginTransaction().add(R.id.contact_container, list).commit();
        }
        mTopBarView = getTopBarView();
        String actionBtn = getString(R.string.radar_ok_count, getString(R.string.dialog_ok_button) , 0);
        mTopBarView.setTopBarToStatus(1, R.drawable.topbar_back_bt, R.drawable.btn_style_green, null, actionBtn, getString(R.string.select_contacts), null, this);
        mTopBarView.setRightBtnEnable(false);*//*
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                //hideSoftKeyboard();
                finish();
                break;
            case R.id.text_right:
                List<Fragment> fragments = getSupportFragmentManager().getFragments();

                /*if(fragments.get(0) instanceof ContactListFragment) {
                    String chatuser = ((ContactListFragment) fragments.get(0) ).getChatuser();
                    String[] split = chatuser.split(",");
                    if(split.length == 1 && !mNeedResult) {
                        //Intent intent = new Intent(ActivityContactActivity.this , ChattingActivity.class);
                        //intent.putExtra(ChattingFragment.RECIPIENTS, split[0]);
                        //startActivity(intent);
                        finish();
                        return ;
                    }

                    if(mNeedResult) {
                        Intent intent = new Intent();
                        intent.putExtra("Select_Conv_User", split);
                        setResult(-1, intent);
                        finish();
                        return ;
                    }

                    if(split.length > 0) {
                        postCreatePrivateChatroom(split);
                    }
                }*/
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
        /*
        GroupService.doCreateGroup(split, new ECGroupManager.OnInviteJoinGroupListener() {


            @Override
            public void onInviteJoinGroupComplete(ECError error, String groupId,
                                                  String[] members) {
                if ("000000".equals(error.errorCode)) {
                    GroupMemberSqlManager.insertGroupMembers(groupId, members);
                    ArrayList<String> contactName = ContactSqlManager.getContactName(members);
                    String users = DemoUtils.listToString(contactName, ",");
                    Intent intent = new Intent(ActivityActivityContactList.this, ChattingActivity.class);
                    intent.putExtra(ChattingFragment.RECIPIENTS, groupId);
                    intent.putExtra(ChattingFragment.CONTACT_USER, users);
                    startActivity(intent);
                }
                dismissPostingDialog();
            }
        });*/
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
        LogUtil.d(TAG, "onActivityResult: requestCode=" + requestCode
                + ", resultCode=" + resultCode + ", data=" + data);

        // If there's no data (because the user didn't select a picture and
        // just hit BACK, for example), there's nothing to do.
        if (requestCode == REQUEST_CODE_VIEW_GROUP_OWN) {
            if (data == null) {
                return;
            }
        } else if (resultCode != RESULT_OK) {
            LogUtil.d("onActivityResult: bail due to resultCode=" + resultCode);
            return;
        }
        /*
        String contactId = data.getStringExtra(ChattingFragment.RECIPIENTS);
        String contactUser = data.getStringExtra(ChattingFragment.CONTACT_USER);
        if(contactId != null && contactId.length() > 0) {
            Intent intent = new Intent(this ,  ChattingActivity.class);
            intent.putExtra(ChattingFragment.RECIPIENTS, contactId);
            intent.putExtra(ChattingFragment.CONTACT_USER, contactUser);
            startActivity(intent);
            finish();
        }*/
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
