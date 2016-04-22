package com.android.doctor.ui.chat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.helper.DialogHelper;
import com.android.doctor.ui.adapter.GroupMemberListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.yuntongxun.kitsdk.ui.group.model.ECContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentGroupMemberList extends BaseRecyViewFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void setAdapter() {
        adapter = new GroupMemberListAdapter((GroupMemberActivity)getActivity());
        adapter.setItemOptionClickListener(this);
        //((GroupMemberListAdapter)adapter).setOnListItemSlideListener((GroupMemberActivity)getActivity());
        recyclerView.setAdapter(adapter);
        adapter.setData(getTestData());
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
        String msg_tip = getString(R.string.are_you_sure_to_delete_member);
        AlertDialog.Builder builder = DialogHelper.getConfirmDialog(getActivity(), msg_tip,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false).setTitle(R.string.tips);
        Dialog d = builder.create();
        d.show();
    }

}
