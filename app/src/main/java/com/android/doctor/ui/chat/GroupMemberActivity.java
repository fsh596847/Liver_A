package com.android.doctor.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.GroupList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.GroupMemberListAdapter;
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
public class GroupMemberActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mFlContainer;
    @InjectView(R.id.tv_edit)
    protected TextView mTvEdit;
    @InjectView(R.id.tv_add)
    protected TextView mTvAdd;

    private FragmentGroupMemberList fragmentGroupMemberList;
    private GroupList.GroupsEntity mEntity;

    public static void startAty(Context context, Parcelable parcelable) {
        Intent intent = new Intent(context, GroupMemberActivity.class);
        intent.putExtra(GroupProfileActivity.ARG_ENTIT, parcelable);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.fragment_group_member);
    }

    @Override
    protected void initView() {
        super.initView();
        Intent in = getIntent();
        mEntity = in.getParcelableExtra(GroupProfileActivity.ARG_ENTIT);
        if (mEntity == null) return;
        setActionBar(mEntity.getName());
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ts = fm.beginTransaction();
        fragmentGroupMemberList = FragmentGroupMemberList.newInstance(mEntity.getGroupId(), mEntity.getOwner());
        ts.add(R.id.fl_container, fragmentGroupMemberList);
        ts.commitAllowingStateLoss();

        setIfMyGroupView();
    }

    private void setIfMyGroupView() {
        boolean isMine = GroupProfileActivity.judgeIamGroupOwner(mEntity.getOwner());
        mTvEdit.setVisibility(isMine ? View.VISIBLE : View.GONE);
        mTvAdd.setVisibility(isMine ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ContactActivity.REQUEST_CODE_FOR_ADD_MEMBER) {
            if (data != null) {
                Map<String, String> map = new HashMap<>();
                map.put("groupId", mEntity.getGroupId());
                map.put("ownername", mEntity.getOwnernickname());
                map.put("uid", data.getStringExtra("uid"));
                map.put("usertype", data.getStringExtra("usertype"));

                onAddMember(map);
            }
        }
    }

    private void onAddMember(Map<String, String> param) {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.inviteGroupMember(param);
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
                dismissProcessDialog();
                Log.d(AppConfig.TAG,"[GroupMemberActivity-> onAddMember-> onFailure]" + t.toString());
            }
        });
    }

    @OnClick(R.id.tv_add)
    protected void onAddMember() {
        ContactActivity.startAty(this, ContactActivity.REQUEST_CODE_FOR_ADD_MEMBER);
    }

    @OnClick(R.id.tv_edit)
    protected void onEditListMember() {
        String text = mTvEdit.getText().toString();
        mTvEdit.setText("编辑".equals(text) ? "完成" : "编辑");
        GroupMemberListAdapter adapter =  ((GroupMemberListAdapter) fragmentGroupMemberList.getmAdapter());
        if ("编辑".equals(text)) {
            adapter.notifySlideEvent(1);
        } else {
            adapter.notifySlideEvent(0);
        }
        adapter.notifyDataSetChanged();
    }

    public TextView getmTvEdit() {
        return mTvEdit;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(AppConfig.TAG, "onDestory.");
    }
}
