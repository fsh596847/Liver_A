package com.android.doctor.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.model.MsgUserData;
import com.android.doctor.ui.adapter.ContactListAdapter;
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
public class FragmentTabContactList extends BaseRecyViewFragment {

    public static String EXTRA_TYPE = "type";
    private int mType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void setAdapter() {
        adapter = new ContactListAdapter();
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
            if (mType == 0) {
                e.setContactid("patient " + i);
            } else if (mType == 1) {
                e.setContactid("group " + i);
            } else {
                e.setContactid("doctor " + i);
            }
            list.add(e);
        }
        return list;
    }

    @Override
    public void onItemClick(int position, View view) {
        ECContacts e = (ECContacts)adapter.getItem(position);
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
        to.setType("" +mType);
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
