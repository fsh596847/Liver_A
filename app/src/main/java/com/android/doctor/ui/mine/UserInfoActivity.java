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
import com.android.doctor.helper.camera.CameraCore;
import com.android.doctor.helper.camera.CameraProxy;
import com.android.doctor.helper.camera.CameraResult;
import com.android.doctor.model.Constants;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.model.UserInfoEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.app.ChangePhoneNumActivity;
import com.android.doctor.ui.base.BaseActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

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
        onLoadUserInfo();
    }

    private void setViewData(UserInfoEntity e) {
        if (e == null) return;
        userEntity = e.getData();
        if (userEntity == null) return;

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

        setUserFace();
    }

    private void setUserFace() {
        if (userEntity == null) return;
        AppContext.getImageLoader().displayImage(RestClient.getFaceURL(userEntity.getDuid()), mImgAvatar);
    }

    private void onLoadUserInfo() {
        ApiService service = RestClient.createService(ApiService.class);
        User.UserEntity ue = AppContext.context().getUser();
        Call<RespEntity<UserInfoEntity>> call = service.getUserInfo(ue.getDuid());
        call.enqueue(new RespHandler<UserInfoEntity>() {
            @Override
            public void onSucceed(RespEntity<UserInfoEntity> resp) {
                setViewData(resp.getResponse_params());
            }

            @Override
            public void onFailed(RespEntity<UserInfoEntity> resp) {}
        });
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
        if (userEntity == null) return;
        EditActivity.startAty(this,
                Constants.REQUEST_CODE_EDIT_EDU_EXP,
                userEntity.getBase().getTeachexperience());
    }

    @OnClick(R.id.tv_arrow4)
    protected void setCarExp() {
        if (userEntity == null) return;
        EditActivity.startAty(this,
                Constants.REQUEST_CODE_EDIT_CARRER_EXP,
                userEntity.getBase().getExperience());
    }

    @OnClick(R.id.tv_arrow5)
    protected void setFavor() {
        if (userEntity == null) return;
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
        if (requestCode == CameraCore.REQUEST_TAKE_PHOTO_CODE
                || requestCode == CameraCore.REQUEST_TAKE_PICTRUE_CODE) {
            cameraProxy.onResult(requestCode, resultCode, imageReturnIntent);
        } else if (requestCode == Constants.REQ_CODE_FOR_UPDATE) {
            onLoadUserInfo();
        }
    }

    @Override
    public void onSuccess(final String path) {

        MediaType txMediaType = MediaType.parse("text/plain");
        User.UserEntity u = AppContext.context().getUser();
        Map<String,RequestBody> map = new HashMap<>();
        map.put("useruuid", RequestBody.create(txMediaType,u.getDuid()));
        map.put("facetype", RequestBody.create(txMediaType,"0"));
        MediaType imgMediaType = MediaType.parse("image/*");

        String fileName = FileUtils.getFileName(path);
        map.put("files\"; filename=\""+ fileName +"\"", RequestBody.create(imgMediaType, new File(path)));

        uptUserFace(map, new RespHandler() {
            @Override
            public void onSucceed(RespEntity resp) {
                onProResult(resp);
                setUserFace();
            }

            @Override
            public void onFailed(RespEntity resp) {
                onProResult(resp);
            }
        });
    }

    @Override
    public void onFail(String path) {}


    private void uptUserFace(Map<String,RequestBody> params ,RespHandler respHandler) {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.uptUserFace(params);
        call.enqueue(respHandler);
    }
}
