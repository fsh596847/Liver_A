package com.android.doctor.ui.mine;

import android.os.Bundle;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.SuggClassList;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.KBaseListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.android.doctor.ui.plan.PlanDetaActivity;

import java.util.ArrayList;

import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentSubsMoreList extends BaseRecyViewFragment {

    public static final int TYPE_STATE_PERSONAL = 1;
    public static final int TYPE_STATE_ALL = 2;
    public static final int TYPE_STATE_ING = 3;
    public static final int TYPE_STATE_FINISHED = 4;
    public static final String EXTRA_PARAM = "type_state";
    private int mType = TYPE_STATE_PERSONAL;
    private String puid ;

    public static FragmentSubsMoreList newInstance(String id) {
        FragmentSubsMoreList f = new FragmentSubsMoreList();
        Bundle b = new Bundle();
        b.putString("id", id);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_search_list;
    }

    @Override
    protected void init() {
        super.init();
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt(EXTRA_PARAM);
            puid = b.getString("id");
        }
    }

    @Override
    protected void setAdapter() {
        mAdapter = new KBaseListAdapter(R.layout.item_subject_more);
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        onLoadMoreSuggClass();
    }

    private void onLoadMoreSuggClass() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<SuggClassList>> call = service.getMoreSuggClassList();
        call.enqueue(new RespHandler<SuggClassList>() {
            @Override
            public void onSucceed(RespEntity<SuggClassList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(new ArrayList());
                } else  {
                    onSuccess(resp.getResponse_params().getData());
                }
            }

            @Override
            public void onFailed(RespEntity<SuggClassList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    @OnClick(R.id.edt_search_box)
    protected void onSearchClick() {
        SearchSubjectActivity.startAty(getActivity());
    }

    @Override
    public void onItemClick(int position, View view) {
        SuggListActivity.startAty(getActivity(), (SuggClassList.SuggEntity) mAdapter.getItem(position));
    }

}
