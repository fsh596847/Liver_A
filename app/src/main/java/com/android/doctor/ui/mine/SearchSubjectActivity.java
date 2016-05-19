package com.android.doctor.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.topic.FragmentSuggList;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * Created by Yong on 2016-02-18.
 */
public class SearchSubjectActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;
    @InjectView(R.id.edt_search_box)
    protected EditText mEdtSearch;
    private String mType;
    private FragmentSearchSubList fragSearch;

    public SearchSubjectActivity() {}

    public static void startAty(Context c) {
        Intent intent = new Intent();
        intent.setClass(c, SearchSubjectActivity.class);
        c.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_search_subject);
    }

    @Override
    protected void initView() {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        fragSearch= FragmentSearchSubList.newInstance("");
        trans.replace(R.id.fl_container, fragSearch);
        trans.commit();
    }

    @OnEditorAction(R.id.edt_search_box)
    protected boolean onSearch(TextView v, int actionId,
                               KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            String word = mEdtSearch.getText().toString().trim();
            if (!TextUtils.isEmpty(word)) {
                DeviceHelper.hideSoftKeyboard(v);
                fragSearch.onSearch(word);
            }
            return true;
        }
        return false;
    }

    @OnClick(R.id.tv_cancel)
    protected void cancel() {
        onBackPressed();
    }
}
