package com.android.doctor.ui.chat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.ChatUtils;
import com.android.doctor.model.GroupList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.GroupListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 群组列表
 * Created by Yong on 2016/3/8.
 */
public class FragmentGroupList extends BaseRecyViewFragment {

    public static String ARG_TYPE = "ARG_TYPE";
    public static final int LIST_GROUP_TYPE_MINE = 0;
    public static final int LIST_GROUP_TYPE_SEARCH = 1;
    private int mType;
    private Map<String, String> queryMap = new HashMap<>();
    private String mOwnerId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    public static FragmentGroupList newInstance(int tp) {
        Bundle b = new Bundle();
        b.putInt(FragmentGroupList.ARG_TYPE, tp);
        if (tp == LIST_GROUP_TYPE_MINE) {
            User.UserEntity u = AppContext.context().getUser();
            if (u != null) {
                b.putString("owner_id", u.getDuid());
            }
        }
        FragmentGroupList f = new FragmentGroupList();
        f.setArguments(b);
        return f;
    }

    @Override
    protected void setAdapter() {
        int layout = (mType == LIST_GROUP_TYPE_MINE ? R.layout.item_mine_group :R.layout.item_search_group);
        mAdapter = new GroupListAdapter(layout);
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void init() {
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt(ARG_TYPE);
            mOwnerId = b.getString("owner_id");
        }
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {

        if (mType == LIST_GROUP_TYPE_MINE) {
            onLoadMineGroups();
            return;
        }

        queryMap.put("page_size", "" + limit);
        if (pageNum == 0) {
            queryMap.put("page_value_max", "" + 0);
        } else {
            List<GroupList.GroupsEntity> data = mAdapter.getData();
            if (data != null) {
                GroupList.GroupsEntity de = data.get(data.size()-1);
                if (de != null) {
                    queryMap.put("page_value_max", "" + de.getGroupId());
                }
            }
        }

        onLoadPubGroups();
    }

    private void onLoadPubGroups() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<GroupList>> call = service.searchGroupList(queryMap);
        call.enqueue(new RespHandler<GroupList>() {
            @Override
            public void onSucceed(RespEntity<GroupList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    onSuccess(resp.getResponse_params().getGroups());
                } else {
                    onSuccess(null);
                }
            }
            @Override
            public void onFailed(RespEntity<GroupList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    private void onLoadMineGroups() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<GroupList>> call = service.getMineGroupList(mOwnerId);
        call.enqueue(new RespHandler<GroupList>() {
            @Override
            public void onSucceed(RespEntity<GroupList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    onSuccess(resp.getResponse_params().getGroups());
                } else {
                    onSuccess(null);
                }
            }
            @Override
            public void onFailed(RespEntity<GroupList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    public void onSearch(String word) {
        queryMap.put("groupclass", word);
        onRefresh();
    }

    public void onClearSearch() {
        queryMap.remove("groupclass");
        onRefresh();
    }

    @Override
    public void onItemClick(int position, View view) {
        Object obj = mAdapter.getItem(position);
        if (obj != null && obj.getClass().equals(GroupList.GroupsEntity.class)) {
            GroupList.GroupsEntity e = (GroupList.GroupsEntity) obj;
            if (mType == LIST_GROUP_TYPE_MINE) {
                chat2(e);
            } else {
                GroupProfileActivity.startAty(getActivity(), e.getGroupId());
            }
        }
    }

    private void chat2(GroupList.GroupsEntity groupsEntity) {
        Log.d(TAG, "[FragmentGroupList-> chat2] " + new Gson().toJson(groupsEntity));
        ChatUtils.chat2(getActivity(), groupsEntity.getGroupId(),groupsEntity.getGroupId(),
                groupsEntity.getName(),groupsEntity.getUuid(), String.valueOf(0));
    }
}
