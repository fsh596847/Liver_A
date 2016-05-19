package com.android.doctor.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.Constants;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.SuggClassList;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.KBaseListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.android.doctor.ui.widget.GridSpacingItemDecoration;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentSubjectList extends BaseRecyViewFragment {

    private int mType = 1;

    public static FragmentSubjectList newInstance(String id) {
        FragmentSubjectList f = new FragmentSubjectList();
        Bundle b = new Bundle();
        b.putString("id", id);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void init() {
        super.init();
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt("param");
        }
    }

    @Override
    protected void setLayoutManager() {
        layoutManager = new GridLayoutManager(getActivity(), 2);
        ((GridLayoutManager)layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 3, 0));
    }

    @Override
    protected void setAdapter() {
        mAdapter = new KBaseListAdapter(R.layout.item_subject);
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        onLoadSuggClassList();
    }

    private void onLoadSuggClassList() {
        User.UserEntity userEntity = AppContext.context().getUser();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<SuggClassList>> call = service.getSuggClassList(userEntity.getDuid());
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

    @Override
    public void onItemClick(int position, View view) {
        SuggClassList.SuggEntity sugg = (SuggClassList.SuggEntity) mAdapter.getItem(position);
        if (sugg != null)
        SuggListActivity.startAty(getActivity(), sugg);
    }
}
