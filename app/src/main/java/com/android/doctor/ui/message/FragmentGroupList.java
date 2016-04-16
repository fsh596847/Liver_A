package com.android.doctor.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.MsgUserData;
import com.android.doctor.ui.adapter.GroupListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.google.gson.Gson;
import com.yuntongxun.kitsdk.core.ECKitConstant;
import com.yuntongxun.kitsdk.ui.ECChattingActivity;
import com.yuntongxun.kitsdk.ui.group.model.ECContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentGroupList extends BaseRecyViewFragment {

    public static String EXTRA_TYPE = "type";
    public static final int LIST_GROUP_TYPE_NORMAL = 0;
    public static final int LIST_GROUP_TYPE_SEARCH = 1;
    private int mType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void setAdapter() {
        int layout = (mType == LIST_GROUP_TYPE_NORMAL ? R.layout.group_item :R.layout.group_search_item);
        adapter = new GroupListAdapter(layout);
        adapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(getTestData());
    }

    @Override
    protected void init() {
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt(EXTRA_TYPE);
        }
    }

    protected void onLoad(int pageNum, int limit) {
        if (mState == PAGE_STATE_REFRESH) {
            //setRefreshingState(true);
        }
        onLoad();
    }

    private void onLoad() {
        /*ApiService service = RestClient.createService(ApiService.class, null, new ProjectTypeAdapterFactory());
        Call<List<CharityProjectItem>> call = service.getProjList("on_going");
        call.enqueue(new Callback<List<CharityProjectItem>>() {
            @Override
            public void onResponse(Call<List<CharityProjectItem>> call, Response<List<CharityProjectItem>> response) {
                if (response.isSuccess()) {
                    onSuccess(response.body());
                } else {
                    onFail("加载失败");
                }
            }

            @Override
            public void onFailure(Call<List<CharityProjectItem>> call, Throwable t) {
                onFail("加载失败");
            }
        });*/
    }

    private List getTestData(){
        List list = new ArrayList();
        for (int i = 0; i < 6; ++i) {
            ECContacts e = new ECContacts();
            e.setContactid("郭小强患者群 " + i);

            list.add(e);
        }
        return list;
    }

    @Override
    public void onItemClick(int position, View view) {
        ECContacts e = (ECContacts)adapter.getItem(position);
        if (mType == LIST_GROUP_TYPE_NORMAL) {
            startChat(e);
        } else if (mType == LIST_GROUP_TYPE_SEARCH) {
            showGroupProfile();
        }
    }

    private void showGroupProfile() {
        UIHelper.showtAty(getActivity(), GroupProfileActivity.class);
    }

    private void startChat(ECContacts e) {
        //ECDeviceKit.getIMKitManager().startConversationActivity("test");
        //CCPAppManager.startChattingAction(getActivity(), "test");
        Intent intent = new Intent(getActivity(), ECChattingActivity.class);
        intent.putExtra(ECKitConstant.KIT_CONVERSATION_TARGET, e.getContactid());
        intent.putExtra(ECChattingActivity.CONTACT_USER, e.getContactid());
        MsgUserData data = new MsgUserData();
        data.setType("" + 0);

        MsgUserData.ToEntity to = new MsgUserData.ToEntity();
        to.setId(e.getContactid());
        to.setName(e.getContactid());
        to.setType("" +1);
        to.setUuid("uuid");
        MsgUserData.FromEntity from = new MsgUserData.FromEntity();
        from.setId(e.getContactid());
        from.setName(e.getContactid());
        from.setType("" + 0);
        from.setUuid("uuid");
        data.setTo(to);
        data.setFrom(from);
        Gson g = new Gson();
        intent.putExtra("userdata", g.toJson(data));
        getActivity().startActivity(intent);
    }
}
