package com.android.doctor.ui.chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.ContactList;
import com.android.doctor.model.MsgUserData;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.ContactListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.google.gson.Gson;
import com.yuntongxun.kitsdk.core.ECKitConstant;
import com.yuntongxun.kitsdk.ui.ECChattingActivity;
import com.yuntongxun.kitsdk.ui.group.model.ECContacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentContactList extends BaseRecyViewFragment {
    @InjectView(R.id.fl_container)
    protected FrameLayout mFrameLayout;

    public static final String EXTRA_TYPE = "type";
    private int mType;
    private Map<String, String> queryMap = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact_list;
    }

    @Override
    protected void setAdapter() {
        adapter = new ContactListAdapter();
        adapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void init() {
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt(EXTRA_TYPE);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        User u = AppContext.context().getUser();
        if (u == null || u.getUser() == null) {
            return;
        }

        queryMap.put("page_size", "9999");
        queryMap.put("uid", "" + u.getUser().getDuid());
        queryMap.put("utype","" + mType);
        queryMap.put("linktype", mType == 0 ? "医生" : "患者");

        onLoadRequest();
    }

    private void onLoadRequest() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ContactList>> call = service.getMyContactList(queryMap);
        call.enqueue(new Callback<RespEntity<ContactList>>() {
            @Override
            public void onResponse(Call<RespEntity<ContactList>> call, Response<RespEntity<ContactList>> response) {
                RespEntity<ContactList> data = response.body();
                if (response.isSuccessful()) {
                    if (data == null) {
                        onSuccess(new ArrayList());
                        return;
                    } else if (data.getResponse_params() != null) {
                        onSuccess(data.getResponse_params().getData());
                    }
                } else {
                    String errMsg = "";
                    if (data != null) {
                        errMsg = data.getError_msg();
                    }
                    onFail(errMsg);
                }
            }

            @Override
            public void onFailure(Call<RespEntity<ContactList>> call, Throwable t) {
                onFail("加载失败");
            }
        });
    }

    public void onSearch(String keywords) {
        queryMap.put("keywords", keywords);
        onRefresh();
    }

    public void onClearSearch() {
        queryMap.remove("keywords");
        onRefresh();
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
