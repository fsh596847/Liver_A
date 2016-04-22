package com.android.doctor.ui.chat;

import android.view.View;
import android.widget.FrameLayout;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.adapter.DoctorListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.android.doctor.ui.widget.SideBar;
import com.yuntongxun.kitsdk.ui.group.model.ECContacts;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentDoctorList extends BaseRecyViewFragment {
    @InjectView(R.id.fl_container)
    protected FrameLayout mFrameLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact_list;
    }

    @Override
    protected void setAdapter() {
        adapter = new DoctorListAdapter();
        adapter.setItemOptionClickListener(this);
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
        for (int i = 0; i < 16; ++i) {
            ECContacts e = new ECContacts();
            e.setContactid("郭小强患者群 " + i);

            list.add(e);
        }
        return list;
    }

    @Override
    public void onItemClick(int position, View view) {
        ECContacts e = (ECContacts)adapter.getItem(position);
        UIHelper.showtAty(getActivity(), DoctorProfileActivity.class);
    }

    private void showGroupProfile() {
        UIHelper.showtAty(getActivity(), GroupProfileActivity.class);
    }

    private void startChat(ECContacts e) {

    }
}
