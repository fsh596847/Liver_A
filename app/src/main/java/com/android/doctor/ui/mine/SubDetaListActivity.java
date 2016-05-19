package com.android.doctor.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.Constants;
import com.android.doctor.model.User;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.topic.FragmentSuggList;

import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-18.
 */
public class SubDetaListActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;
    private String mType;

    public SubDetaListActivity() {}

    public static void startAty(Context c) {
        Intent intent = new Intent();
        intent.setClass(c, SubDetaListActivity.class);
        c.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_subs_deta);
    }

    @Override
    protected void initView() {
        User.UserEntity u = AppContext.context().getUser();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        FragmentSuggList f = FragmentSuggList.newInstance(Constants.USER_COLLECTED_ARTICLE, u.getDuid());
        trans.replace(R.id.fl_container, f);
        trans.commit();
        setActionBar(R.string.my_collect);
    }

}
