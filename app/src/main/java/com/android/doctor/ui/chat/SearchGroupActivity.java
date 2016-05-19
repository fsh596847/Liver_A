package com.android.doctor.ui.chat;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * Created by Yong on 2016/3/26.
 */
public class SearchGroupActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;
    @InjectView(R.id.edt_search_box)
    protected EditText mEdtSearch;
    @InjectView(R.id.iv_clear)
    protected ImageView mIvClear;
    private FragmentGroupList fragmentGroupList;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.fragment_search_group);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.search_group);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        fragmentGroupList = FragmentGroupList.newInstance(FragmentGroupList.LIST_GROUP_TYPE_SEARCH);
        ts.add(R.id.fl_container, fragmentGroupList);
        ts.commitAllowingStateLoss();
    }

    @OnEditorAction(R.id.edt_search_box)
    protected boolean onSearch(TextView v, int actionId,
                               KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String word = mEdtSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(word)) {
                DeviceHelper.hideSoftKeyboard(v);
                fragmentGroupList.onSearch(word);
            }
            return true;
        }
        return false;
    }

    @OnTextChanged(R.id.edt_search_box)
    public void onEdtPhoneNum(CharSequence s, int start, int before, int count) {
        mIvClear.setVisibility(TextUtils.isEmpty(s) ? View.INVISIBLE : View.VISIBLE);
    }

    @OnClick(R.id.iv_clear)
    public void onClear() {
        mEdtSearch.setText("");
        fragmentGroupList.onClearSearch();
    }
}
