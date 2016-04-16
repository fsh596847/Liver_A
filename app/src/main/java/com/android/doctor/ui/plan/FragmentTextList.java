package com.android.doctor.ui.plan;

import android.os.Bundle;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.ui.adapter.TextListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentTextList extends BaseRecyViewFragment {

    public static final String EXTRA_PARAM = "type_state";
    private String mType;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_list;
    }

    @Override
    protected void init() {
        super.init();
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getString(EXTRA_PARAM);
        }
    }

    @Override
    protected void setAdapter() {
        adapter = new TextListAdapter();
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
            list.add("抗病毒");
        }
        return list;
    }

    @Override
    public void onItemClick(int position, View view) {
        //ECContacts e = (ECContacts)adapter.getItem(position);
        //UIHelper.showtAty(getActivity(), PlanSchemeActivity.class);
    }

}
