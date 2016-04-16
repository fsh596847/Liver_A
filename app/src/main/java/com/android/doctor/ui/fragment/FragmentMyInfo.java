package com.android.doctor.ui.fragment;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.doctor.helper.FileHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.activity.LoginEntryActivity;
import com.android.doctor.ui.base.BaseFragment;
import com.android.doctor.R;
import com.android.doctor.ui.widget.CircleImageView;

import java.io.File;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016-02-12.
 */
public class FragmentMyInfo extends BaseFragment {
    @InjectView(R.id.iv_avatar)
    protected CircleImageView mAvatar;

    protected RelativeLayout mRlAccountSecurity;

    protected RelativeLayout mRlClearCache;

    protected TextView mTvCacheSize;

    protected RelativeLayout mRlFeedback;

    protected RelativeLayout mRlCheckUpdate;

    protected RelativeLayout mRlAboutUs;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_info;
    }

    protected void initView(View view) {
        //((TextView)view.findViewById(R.id.toolbar_title)).setText(R.string.my);
    }

    private void caculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getActivity().getFilesDir();
        File cacheDir = getActivity().getCacheDir();

        fileSize += FileHelper.getDirSize(filesDir);
        fileSize += FileHelper.getDirSize(cacheDir);

        if (fileSize > 0)
            cacheSize = FileHelper.formatFileSize(fileSize);
        mTvCacheSize.setText(cacheSize);
    }

    @OnClick(R.id.tv_name)
    protected void onLogin() {
        UIHelper.showtAty(getActivity(), LoginEntryActivity.class);
    }
}
