package com.android.doctor.ui.mine;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.doctor.app.AppContext;
import com.android.doctor.helper.FileUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.Constants;
import com.android.doctor.model.User;
import com.android.doctor.ui.base.BaseFragment;
import com.android.doctor.R;
import com.android.doctor.ui.plan.TPlanListActivity;
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
    @InjectView(R.id.tv_name)
    protected TextView mTvName;
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
        if (AppContext.context().isLogin()) {
           User.UserEntity userEntity = AppContext.context().getUser();
            if (userEntity != null) {
                mTvName.setText(userEntity.getNickname());
            }
        }
    }

    private void caculateCacheSize() {
        long fileSize = 0;
        String cacheSize = "0KB";
        File filesDir = getActivity().getFilesDir();
        File cacheDir = getActivity().getCacheDir();

        fileSize += FileUtils.getDirSize(filesDir);
        fileSize += FileUtils.getDirSize(cacheDir);

        if (fileSize > 0)
            cacheSize = FileUtils.formatFileSize(fileSize);
        mTvCacheSize.setText(cacheSize);
    }

    @OnClick(R.id.iv_avatar)
    protected void onLookPerInfo() {
        UIHelper.showtAty(getActivity(), UserInfoActivity.class);
    }

    @OnClick(R.id.rl_pub_ann)
    protected void onPubAnn() {
        UIHelper.showtAty(getActivity(), PubAnnocActivity.class);
    }

    @OnClick(R.id.rl_my_diag)
    protected void onMyDiag() {

    }

    @OnClick(R.id.rl_tplan)
    protected void onMyTPlan() {
        TPlanListActivity.startAty(getActivity(), Constants.REQ_CODE_FOR_CREATE);
    }

    @OnClick(R.id.rl_know_lib)
    protected void onKnlLib() {
        KnBaseActivity.startAty(getActivity());
    }

    @OnClick(R.id.rl_collect)
    protected void onMyCollect() {
        User.UserEntity u = AppContext.context().getUser();
        SuggListActivity.startAty(getActivity(), u.getDuid());
    }


    @OnClick(R.id.tv_name)
    protected void onLogin() {
        //UIHelper.showtAty(getActivity(), LoginEntryActivity.class);
    }

    @OnClick(R.id.img_setting)
    protected void onSetting(){
        UIHelper.showtAty(getActivity(), SettingActivity.class);
    }
}
