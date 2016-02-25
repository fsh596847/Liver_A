package com.fidots.restaurant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.widget.CircleImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-12.
 */
public class FragmentMyInfo extends Fragment {
    @InjectView(R.id.iv_avatar)
    protected CircleImageView mAvatar;
    @InjectView(R.id.rl_account_security)
    protected RelativeLayout mRlAccountSecurity;
    @InjectView(R.id.rl_clear_cache)
    protected RelativeLayout mRlClearCache;
    @InjectView(R.id.rl_feedback)
    protected RelativeLayout mRlFeedback;
    @InjectView(R.id.rl_check_update)
    protected RelativeLayout mRlCheckUpdate;
    @InjectView(R.id.rl_about_us)
    protected RelativeLayout mRlAboutUs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_info, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        initView(view);
    }

    private void initView(View view) {
        ((TextView)view.findViewById(R.id.toolbar_title)).setText(R.string.my);
    }
}
