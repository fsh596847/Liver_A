package com.android.doctor.ui.plan;

import android.os.Bundle;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.PlanListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.yuntongxun.kitsdk.ui.group.model.ECContacts;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentPlanList extends BaseRecyViewFragment {

    public static final int TYPE_STATE_PERSONAL = 1;
    public static final int TYPE_STATE_ALL = 2;
    public static final int TYPE_STATE_ING = 3;
    public static final int TYPE_STATE_FINISHED = 4;
    public static final String EXTRA_PARAM = "type_state";
    private int mType = 1;
    private String puid ;
    public static FragmentPlanList newInstance(String id) {
        FragmentPlanList f = new FragmentPlanList();
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
            mType = b.getInt(EXTRA_PARAM);
            puid = b.getString("id");
        }
    }

    @Override
    protected void setAdapter() {
        adapter = new PlanListAdapter();
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
        Call<RespEntity<PlanList>> call = service.getDoctorPatientRecord("" + puid);
        call.enqueue(new Callback<RespEntity<PlanList>>() {
            @Override
            public void onResponse(Call<RespEntity<PlanList>> call, Response<RespEntity<PlanList>> response) {
                RespEntity<PlanList> data = response.body();
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
            public void onFailure(Call<RespEntity<PlanList>> call, Throwable t) {
                onFail("加载失败");
            }
        });
    }

    @Override
    public void onItemClick(int position, View view) {
        PlanSchemeActivity.startAty(getActivity(), (PlanList.DataEntity) adapter.getItem(position));
    }

}
