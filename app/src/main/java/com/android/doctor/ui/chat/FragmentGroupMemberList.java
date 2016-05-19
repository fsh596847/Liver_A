package com.android.doctor.ui.chat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.GroupMemberList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.GroupMemberListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentGroupMemberList extends BaseRecyViewFragment {

    private static final String ARG_GID = "ARG_GID";
    private static final String ARG_OWNER_ID = "ARG_OWNER_ID";
    private String mGid;
    private String mOwnerId;

    public static FragmentGroupMemberList newInstance(String id, String oid) {
        Bundle args = new Bundle();
        args.putString(ARG_GID, id);
        args.putString(ARG_OWNER_ID, oid);
        FragmentGroupMemberList fragment = new FragmentGroupMemberList();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void setAdapter() {
        mAdapter = new GroupMemberListAdapter((GroupMemberActivity)getActivity());
        mAdapter.setItemOptionClickListener(this);
        //((GroupMemberListAdapter)mAdapter).setOnListItemSlideListener((GroupMemberActivity)getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void init() {
        super.init();
        Bundle b = getArguments();
        mGid = (b == null ? null : b.getString(ARG_GID));
        mOwnerId = (b == null ? null : b.getString(ARG_OWNER_ID));
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        onLoadFromServer();
    }

    private void onLoadFromServer() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<GroupMemberList>> call = service.getGroupMemberList(mGid);
        call.enqueue(new RespHandler<GroupMemberList>() {
            @Override
            public void onSucceed(RespEntity<GroupMemberList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(null);
                } else {
                    onSuccess(resp.getResponse_params().getMembers());
                }
            }
            @Override
            public void onFailed(RespEntity<GroupMemberList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    @Override
    protected void onSuccess(List d) {
        if (d != null) {
            for (Object e : d) {
                GroupMemberList.GroupMemberEntity mem = (GroupMemberList.GroupMemberEntity) e;
                if (mOwnerId != null && mOwnerId.equals(mem.getOwneruid())) {
                    mem.setIsManager(true);
                } else {
                    mem.setIsManager(false);
                }
            }
        }
        super.onSuccess(d);
    }

    private void onDelGroupMember(GroupMemberList.GroupMemberEntity e) {
        Map<String,String> map = new HashMap<>();
        map.put("uid", e.getOwneruid());
        map.put("groupId", e.getGroupId());
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.deleteGroupMember(map);
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                RespEntity r = response.body();
                if (r != null) {
                    UIHelper.showToast(r.getError_msg());
                }
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                Log.d(AppConfig.TAG,"[FragmentGroupMemberList-> onDelGroupMember-> onFailure]" + t.toString());

            }
        });
    }

    private void showDeleteMemDialog(final GroupMemberList.GroupMemberEntity e) {
        String msg_tip = getString(R.string.are_you_sure_to_delete_member);
        AlertDialog.Builder builder = DialogUtils.getConfirmDialog(getActivity(), msg_tip,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        onDelGroupMember(e);
                    }
                }).setCancelable(false).setTitle(R.string.tips);
        Dialog d = builder.create();
        d.show();
    }

    @Override
    public void onItemClick(int position, View view) {
        GroupMemberList.GroupMemberEntity e = (GroupMemberList.GroupMemberEntity) mAdapter.getItem(position);
        int id = view.getId();
        if (id == R.id.tv_delete || id == R.id.img_delete) {
            showDeleteMemDialog(e);
        } else {
            GroupCardActivity.startAty(getActivity(), e);
        }
    }

}
