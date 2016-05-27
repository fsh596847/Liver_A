package com.android.doctor.ui.chat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.app.DataCacheManager;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.Constants;
import com.android.doctor.model.ContactGroupList;
import com.android.doctor.model.GroupDeta;
import com.android.doctor.model.GroupList;
import com.android.doctor.model.MessageEvent;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/26.
 */
public class GroupProfileActivity extends BaseActivity {

    public static final String ARG_ENTIT = "ARG_ENTIT";
    private static final int REQUEST_CODE_EDIT_GROUP = 101;
    @InjectView(R.id.tv_group_master)
    protected TextView mTvGroupMaster;
    @InjectView(R.id.tv_group_member)
    protected TextView mTvGroupMember;
    @InjectView(R.id.tv_join_condition)
    protected TextView mTvJoinVerify;
    @InjectView(R.id.tv_group_divide)
    protected TextView mTvGroupClass;
    @InjectView(R.id.tv_group_announce)
    protected TextView mTvGroupBrief;
    @InjectView(R.id.tv_my_group_card)
    protected TextView mTvGroupCard;
    @InjectView(R.id.tbtn_receive_msg)
    protected ToggleButton mBtnToggle;
    @InjectView(R.id.ll_my_group_info)
    protected View mMyGroupView;
    @InjectView(R.id.btn_group)
    protected AppCompatButton mBtnGroup;
    private GroupList.GroupsEntity mEntity;
    private String groupId;
    private boolean mIsOwner, mIsMember;

    public static void startAty(Context context, String groupId) {
        Intent intent = new Intent(context, GroupProfileActivity.class);
        intent.putExtra(ARG_ENTIT, groupId);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.fragment_group_detail);
    }

    @Override
    protected void initData() {
        super.initData();
        onLoadGroupDeta();
    }

    @Override
    protected void init() {
        super.init();
        Intent in = getIntent();
        groupId = in.getStringExtra(ARG_ENTIT);
    }

    protected void setViewData() {
        if (mEntity == null) return;
        setActionBar(mEntity.getName());
        mTvGroupMaster.setText(mEntity.getOwnernickname());
        mTvGroupMember.setText(mEntity.getCount());
        List<String> arrs = Arrays.asList(getResources().getStringArray(R.array.group_identify));
        int pos = mEntity.getPermission() == null ? 0 : Integer.parseInt(mEntity.getPermission());
        if (0 <= pos && pos < arrs.size())
        mTvJoinVerify.setText(arrs.get(pos));
        String gc = "";
        List<String> gclass = mEntity.getGroupclass();
        if (gclass != null) {
            for (String s : gclass) {
                gc = gc + s + " ";
            }
        }
        mTvGroupClass.setText(gc);
        mTvGroupBrief.setText(mEntity.getDeclared());

        setIfOwnerView();
    }

    private void setIfOwnerView() {
        mIsOwner = judgeIamGroupOwner(mEntity.getOwner());
        mMyGroupView.setVisibility(mIsOwner ? View.VISIBLE : View.GONE);
        mIsMember = judgeIamGroupMember(mEntity.getGroupId());
        mBtnGroup.setText(mIsOwner ? getString(R.string.manage_group)
                : mIsMember ? getString(R.string.exit_group) : getString(R.string.apply_for_join));
        mTvGroupCard.setText(mEntity.getOwnernickname());
    }

    private void onLoadGroupDeta() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<GroupDeta>> call = service.getGroupDeta(groupId);
        call.enqueue(new RespHandler<GroupDeta>() {
            @Override
            public void onSucceed(RespEntity<GroupDeta> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    mEntity = resp.getResponse_params().getGroup();
                }
                setViewData();
            }
            @Override
            public void onFailed(RespEntity<GroupDeta> resp) {
            }
        });
    }

    public static boolean judgeIamGroupOwner(String ownerId) {
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity != null && userEntity.getDuid() != null) {
            return userEntity.getDuid().equals(ownerId);
        }
        return false;
    }

    private boolean judgeIamGroupMember(String gid) {
        ContactGroupList.GroupsEntity g = DataCacheManager.getInstance().findGroup(gid);
        return g != null;
    }

    private Map<String,String> genIfReceiveMsgParam() {
        Map<String, String> param = new HashMap<>();
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity != null) {
            User.UserEntity.YtxsubaccountEntity account = userEntity.getYtxsubaccount();
            param.put("sid", account.getSubaccountsid());
            param.put("token", account.getSubtoken());
            param.put("groupId", mEntity.getGroupId());
            param.put("owneruid", mEntity.getOwner());
            param.put("rule", mBtnToggle.isChecked() ? "0" : "1");
        }
        return param;
    }


    private Map<String,String> genApplyForJoinParam() {
        Map<String, String> param = new HashMap<>();
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity != null) {
            User.UserEntity.YtxsubaccountEntity ytx = userEntity.getYtxsubaccount();
            param.put("uid", userEntity.getDuid());
            param.put("usertype", "0");
            param.put("nickname", userEntity.getNickname());
            param.put("sid", ytx.getSubaccountsid());
            param.put("token", ytx.getSubtoken());
            param.put("groupId", mEntity.getGroupId());
            param.put("groupname", mEntity.getName());
            param.put("groupowner", mEntity.getOwner());
            param.put("ownertype", "0");
            param.put("voipAccount", ytx.getVoipaccount());
        }
        return param;
    }

    private Map<String,String> genExitGroupParam() {
        Map<String, String> param = new HashMap<>();
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity != null) {
            User.UserEntity.YtxsubaccountEntity account = userEntity.getYtxsubaccount();
            param.put("sid", account.getSubaccountsid());
            param.put("token", account.getSubtoken());
            param.put("groupId", mEntity.getGroupId());
            param.put("owneruid", mEntity.getOwner());
        }
        return param;
    }

    private void onSetReceiveGroupMsg() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.setGroupMsg(genIfReceiveMsgParam());
        call.enqueue(new RespHandler() {
            @Override
            public void onSucceed(RespEntity resp) {
                dismissProcessDialog();
                if (resp != null) {
                    UIHelper.showToast(resp.getError_msg());
                }
            }

            @Override
            public void onFailed(RespEntity resp) {
                dismissProcessDialog();
            }
        });
    }


    private void onApplyForJoinGroup() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.applyJoinGroup(genApplyForJoinParam());
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                dismissProcessDialog();
                RespEntity r = response.body();
                if (r != null) {
                    UIHelper.showToast(r.getError_msg());
                }
                EventBus.getDefault().post(new MessageEvent(Constants.EVENT_MSG_UPDATE_CONTACT_GROUP));
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                Log.d(AppConfig.TAG,"[GroupProfileActivity-> onSetReceiveGroupMsg-> onFailure]" + t.toString());
                dismissProcessDialog();
            }
        });
    }

    private void onExitGroup() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<Object>> call = service.exitGroup(genExitGroupParam());
        call.enqueue(new RespHandler<Object>() {
            @Override
            public void onSucceed(RespEntity<Object> resp) {
                dismissProcessDialog();
                if (resp != null) {
                    UIHelper.showToast(resp.getError_msg());
                }
                EventBus.getDefault().post(new MessageEvent(Constants.EVENT_MSG_UPDATE_CONTACT_GROUP));
            }

            @Override
            public void onFailed(RespEntity resp) {
                dismissProcessDialog();
                if (resp != null) {
                    UIHelper.showToast(resp.getError_msg());
                }
            }
        });
    }


    @OnClick(R.id.rl_group_member)
    protected void onClickMember(){
        GroupMemberActivity.startAty(this, mEntity);
    }

    @OnClick(R.id.rl_my_group_card)
    protected void onClickMyGroupCard() {
        //GroupCardActivity.startAty(this, mEntity);
    }

    @OnCheckedChanged(R.id.tbtn_receive_msg)
    protected void setReceiveMsg() {
        onSetReceiveGroupMsg();
    }

    @OnClick(R.id.btn_group)
    protected void onGroupOpera() {
        if (mIsOwner) {
            EditGroupActivity.startAtyForEdit(this, REQUEST_CODE_EDIT_GROUP, mEntity);
        } else if (mIsMember){
            showExitGroupDialog();
        } else {
            onApplyForJoinGroup();
        }
    }

    private void showExitGroupDialog() {
        String msg_tip = getString(R.string.are_you_sure_to_exit_group);
        AlertDialog.Builder builder = DialogUtils.getConfirmDialog(this, msg_tip,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onExitGroup();
                    }
                }).setCancelable(false).setTitle(R.string.tips);
        Dialog d = builder.create();
        d.show();
    }
}
