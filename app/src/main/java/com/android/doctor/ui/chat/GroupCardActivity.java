package com.android.doctor.ui.chat;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.EditText;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.GroupMemberList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/26.
 */
public class GroupCardActivity extends BaseActivity {

    @InjectView(R.id.tv_group_nickname)
    protected EditText mTvNickName;
    @InjectView(R.id.tv_phone_member)
    protected EditText mTvPhoneNum;
    @InjectView(R.id.tv_my_email)
    protected EditText mTvEmail;
    @InjectView(R.id.tv_note)
    protected EditText mTvNote;

    private GroupMemberList.GroupMemberEntity mEntity;

    public static void startAty(Activity aty, GroupMemberList.GroupMemberEntity entity) {
        Intent intent = new Intent(aty, GroupCardActivity.class);
        intent.putExtra(EditGroupActivity.ARG_ENTITY, entity);
        aty.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.fragment_group_card);
    }

    @Override
    protected void init() {
        super.init();
        mEntity = getIntent().getParcelableExtra(EditGroupActivity.ARG_ENTITY);
    }

    @Override
    protected void initView() {
        setActionBar(R.string.group_card);
    }

    @Override
    protected void initData() {
        super.initData();
        setViewData();
    }

    private void setViewData() {
        mTvNickName.setText(getIntent().getStringExtra("nickname"));
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity != null) {
            mTvPhoneNum.setText(userEntity.getMobilephone());
            mTvEmail.setText("");
            mTvNote.setText("");
        }
    }

    private Map<String, String> genModifyGroupCardParam() {
        Map<String, String> param = new HashMap<>();
        param.put("groupId", mEntity.getGroupId());
        param.put("owneruid", mEntity.getOwneruid());
        param.put("display", mTvNickName.getText().toString());
        param.put("mail", mTvEmail.getText().toString());
        param.put("tel", mTvPhoneNum.getText().toString());
        param.put("remark", mTvNote.getText().toString());
        return param;
    }

    private void onCheckNull() {

    }

    /**保存*/
    @OnClick(R.id.btn_save_change)
    protected void onSaveChange() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.modifyGroupCard(genModifyGroupCardParam());
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                dismissProcessDialog();
                RespEntity r = response.body();
                if (r != null) {
                    UIHelper.showToast(r.getError_msg());
                }
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                Log.d(AppConfig.TAG,"[EditGroupActivity->onSaveChange->onFailure]" + t.toString());
                dismissProcessDialog();
            }
        });
    }

}
