package com.android.doctor.ui.plan;

import android.os.Bundle;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.PlanListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;

import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentPlanList extends BaseRecyViewFragment {

    public static final int TYPE_STATE_PERSONAL = 1;
    public static final int TYPE_STATE_ALL = 2;
    public static final int TYPE_STATE_ING = 3;
    public static final int TYPE_STATE_FINISHED = 4;
    public static final String EXTRA_PARAM = "type_state";
    private int mType = TYPE_STATE_PERSONAL;
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
        mAdapter = new PlanListAdapter();
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        onLoadPatientPlans();
    }

    private void onLoadPatientPlans() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PlanList>> call = service.getDoctorPatientRecord("" + puid);
        call.enqueue(new RespHandler<PlanList>() {
            @Override
            public void onSucceed(RespEntity<PlanList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(new ArrayList());
                    return;
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

    @Override
    public void onItemClick(int position, View view) {
        PlanList.PlanBaseEntity pb = (PlanList.PlanBaseEntity) mAdapter.getItem(position);
        PlanDetaActivity.startAty(getActivity(), "" + pb.getPid(),pb.getStatus(),pb );
    }

}
