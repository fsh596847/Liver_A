package com.android.doctor.ui.plan;

import android.os.Bundle;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.model.Constants;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.PlanListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentDoctorPlanList extends BaseRecyViewFragment {

    public static final int TYPE_STATE_ALL = 0;
    public static final int TYPE_STATE_ING = 1;
    public static final int TYPE_STATE_FINISHED = 2;
    public static final String ARG_TYPE = "ARG_TYPE";
    public static final String ARG_ID = "ARG_ID";
    private int mStatus = TYPE_STATE_ALL;
    private Map<String, String> queryParam = new HashMap<>();

    private String uid ;
    public static FragmentDoctorPlanList newInstance(String id) {
        FragmentDoctorPlanList f = new FragmentDoctorPlanList();
        Bundle b = new Bundle();
        b.putString(ARG_ID, id);
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
            mStatus = b.getInt(ARG_TYPE);
            uid = b.getString(ARG_ID);
        }
    }

    @Override
    protected void setAdapter() {
        mAdapter = new PlanListAdapter();
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        queryParam.put("page_size", "" + limit);
        if (pageNum == 0) {
            queryParam.put("page_value_max", "" + 0);
        } else {
            List<PlanList.PlanBaseEntity> data = mAdapter.getData();
            if (data != null) {
                PlanList.PlanBaseEntity de = data.get(data.size()-1);
                if (de != null) {
                    queryParam.put("page_value_max", "" + de.getDuid());
                }
            }
        }
        queryParam.put("duid",uid);
        if (mStatus != 0) queryParam.put("status",String.valueOf(mStatus));
        onLoadDoctorPlans();
    }

    private void onLoadDoctorPlans() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanList>> call = service.getPlanList(getQueryParam());
        call.enqueue(new RespHandler<PlanList>() {
            @Override
            public void onSucceed(RespEntity<PlanList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(null);
                } else  {
                    onSuccess(resp.getResponse_params().getData());
                }
            }

            @Override
            public void onFailed(RespEntity<PlanList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    public Map<String,String> getQueryParam() {
        return queryParam;
    }

    public void onSearch(String keywords) {
        queryParam.put("pname", keywords);
        onRefresh();
    }

    public void onClearSearch() {
        queryParam.remove("pname");
        onRefresh();
    }

    @Override
    public void onItemClick(int position, View view) {
        PlanList.PlanBaseEntity pb = (PlanList.PlanBaseEntity) mAdapter.getItem(position);
        if (pb != null) {
            int stat = pb.getStatus();
            if (stat == Constants.PLAN_STATUS_NOT_CREATE || stat == -200) {
                AddPlanActivity.startAty(getActivity(), pb);
            } else {
                PlanDetaActivity.startAty(getActivity(), "" + pb.getPid(), pb.getStatus(), pb);
            }
        }
    }

}
