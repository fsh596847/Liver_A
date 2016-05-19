package com.android.doctor.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.FileUtils;
import com.android.doctor.helper.ImageHelper;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.helper.camera.CameraProxy;
import com.android.doctor.helper.camera.CameraResult;
import com.android.doctor.model.Constants;
import com.android.doctor.model.User;
import com.android.doctor.ui.app.ChangePhoneNumActivity;
import com.android.doctor.ui.base.BaseActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.Arrays;

import butterknife.InjectView;
import butterknife.OnClick;

public class UserInfoActivity extends BaseActivity implements CameraResult {

    @InjectView(R.id.img_avatar)
    protected ImageView mImgAvatar;
    @InjectView(R.id.tv_mobile_num)
    protected TextView mTvMblNum;
    @InjectView(R.id.tv_name)
    protected TextView mTvName;
    @InjectView(R.id.rdg_btn)
    protected RadioGroup mSexRdg;
    @InjectView(R.id.tv_hos)
    protected TextView mTvHos;
    @InjectView(R.id.tv_ks)
    protected TextView mTvKS;
    @InjectView(R.id.tv_zc)
    protected TextView mTvZC;
    @InjectView(R.id.tv_edu_exp)
    protected TextView mTvEduExp;
    @InjectView(R.id.tv_career_exp)
    protected TextView mTvCarExp;
    @InjectView(R.id.tv_favor)
    protected TextView mTvFavor;

    private CameraProxy cameraProxy;
    private User.UserEntity userEntity;

	@Override
	protected void setContentLayout() {
		setContentView(R.layout.activity_user_information);
	}

	@Override
	protected void initView() {
		super.initView();
		setActionBar(R.string.personal_info);
	}

    @Override
    protected void initData() {
        super.initData();
        cameraProxy = new CameraProxy(this,this);
        userEntity = AppContext.context().getUser();
        mTvMblNum.setText(userEntity.getMobilephone());
        mTvName.setText(userEntity.getUsername());
        if (userEntity.getGender() == 0) {
            mSexRdg.check(R.id.rdbtn_female);
        } else {
            mSexRdg.check(R.id.rdbtn_male);
        }
        mTvHos.setText(userEntity.getHosname());
        mTvKS.setText(userEntity.getDeptname());
        mTvZC.setText(userEntity.getBase().getTitle());
        mTvEduExp.setText(userEntity.getBase().getTeachexperience());
        mTvCarExp.setText(userEntity.getBase().getExperience());
        mTvFavor.setText(userEntity.getBase().getGood());
    }

    @OnClick(R.id.tv_arrow1)
    protected void setAvatar() {
        showSetPicWayDialog();
    }

    @OnClick(R.id.tv_arrow2)
    protected void setMblNum() {
        UIHelper.showtAty(this, ChangePhoneNumActivity.class);
    }

    @OnClick(R.id.tv_arrow3)
    protected void setEduExp() {
        EditActivity.startAty(this,
                Constants.REQUEST_CODE_EDIT_EDU_EXP,
                userEntity.getBase().getTeachexperience());
    }

    @OnClick(R.id.tv_arrow4)
    protected void setCarExp() {
        EditActivity.startAty(this,
                Constants.REQUEST_CODE_EDIT_CARRER_EXP,
                userEntity.getBase().getExperience());
    }

    @OnClick(R.id.tv_arrow5)
    protected void setFavor() {
        EditActivity.startAty(this,
                Constants.REQUEST_CODE_EDIT_FAVOR,
                userEntity.getBase().getGood());
    }

    private void showSetPicWayDialog() {
        DialogUtils.showBottomListDialog(this, Arrays.asList(getResources().getStringArray(R.array.add_picture_way)),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        switch (position) {
                            case 0 ://
                                cameraProxy.getPhoto2Camera(FileUtils.getSDCardPath());
                                break;
                            case 1: //
                                cameraProxy.getPhoto2Album(FileUtils.getSDCardPath());
                                break;
                            default:
                                break;
                        }
                    }
                }, null);
    }

    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode,
                                 final Intent imageReturnIntent) {
        if (resultCode != Activity.RESULT_OK)
            return;
        cameraProxy.onResult(requestCode, resultCode, imageReturnIntent);
    }

    @Override
    public void onSuccess(String path) {
        Bitmap bitmap = ImageHelper.getBitmapByPath(path);
    }

    @Override
    public void onFail(String path) {

    }
}
