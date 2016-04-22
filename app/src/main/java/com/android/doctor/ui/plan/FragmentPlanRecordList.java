package com.android.doctor.ui.plan;

import android.os.Bundle;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.PlanRecordList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.TimeLineAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentPlanRecordList extends BaseRecyViewFragment {
    public static final String ARG_PARAM = "ARG_ID";
    public static final String EXTRA_PARAM = "type_state";
    private String mPid;

    public static FragmentPlanRecordList newInstance(String id) {
        FragmentPlanRecordList f = new FragmentPlanRecordList();
        Bundle b = new Bundle();
        b.putString(ARG_PARAM, id);
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
            mPid = b.getString(ARG_PARAM);
        }
    }

    @Override
    protected void setAdapter() {
        adapter = new TimeLineAdapter();
        adapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        onLoad();
    }

    private void onLoad() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanRecordList>> call = service.getPlanRecordList(mPid);
        call.enqueue(new Callback<RespEntity<PlanRecordList>>() {
            @Override
            public void onResponse(Call<RespEntity<PlanRecordList>> call, Response<RespEntity<PlanRecordList>> response) {
                RespEntity<PlanRecordList> body = response.body();
                if (response.isSuccessful()) {
                    if (body == null || body.getResponse_params() == null) {
                        onSuccess(new ArrayList());
                    } else {
                        onSuccess(body.getResponse_params().getData());
                    }
                } else {
                    String errMsg = "";
                    if (body != null) {
                        errMsg = body.getError_msg();
                    }
                    onFail(errMsg);
                }
            }

            @Override
            public void onFailure(Call<RespEntity<PlanRecordList>> call, Throwable t) {
                onFail("加载失败");
            }
        });
    }

    @Override
    public void onItemClick(int position, View view) {
    }

}
