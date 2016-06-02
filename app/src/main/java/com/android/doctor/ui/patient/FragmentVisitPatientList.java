package com.android.doctor.ui.patient;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.app.AppManager;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.PatientListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentVisitPatientList extends BaseRecyViewFragment {

    private Map<String, Object> queryParam = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void setAdapter() {
        mAdapter = new PatientListAdapter(R.layout.item_patient_invite);
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        Log.d("FragmentPatientList", new Gson().toJson(queryParam));

        User.UserEntity u = AppContext.context().getUser();
        if (u == null) return;
        queryParam.put("hosid", "" + u.getHosid());
        queryParam.put("duid", "" + u.getDuid());
        queryParam.put("page_size", "" + limit);
        Object o = mAdapter.getItem(mAdapter.getItemCount()-1);
        if (o == null) {
            queryParam.put("page_value_max", "0");
        } else {
            int hosPatientId = ((HosPaitentList.HosPatientEntity)o).getHospitalPatientId();
            queryParam.put("page_value_max", String.valueOf(hosPatientId));
        }
        queryParam.put("isplan", "0");
        queryParam.put("type", "zy");

        onLoadRequest();
    }

    public void setQueryParam(String k, String v) {
        queryParam.put(k, v);
    }

    public Map<String,Object> getQueryParam() {
        return queryParam;
    }

    private void onLoadRequest() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<HosPaitentList>> call = service.getHosPatientByDuid(getQueryParam());
        call.enqueue(new RespHandler<HosPaitentList>() {
            @Override
            public void onSucceed(RespEntity<HosPaitentList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(null);
                } else {
                    onSuccess(resp.getResponse_params().getData());
                }
            }

            @Override
            public void onFailed(RespEntity<HosPaitentList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    public void onSearch(String key, String keywords) {
        queryParam.put(key, keywords);
        onRefresh();
    }

    public void onClearSearch(String key) {
        queryParam.remove(key);
        onRefresh();
    }

    public void onFilter(Map<String, String> map ) {
        if (map == null || map.isEmpty()){
            queryParam.clear();
        } else {
            queryParam.putAll(map);
        }
        onRefresh();
    }

    @Override
    public void onItemClick(int position, View view) {
        HosPaitentList.HosPatientEntity hosEntity = (HosPaitentList.HosPatientEntity) mAdapter.getItem(position);
        if (hosEntity != null) {
            Activity activity = getActivity();
            Intent i = new Intent();
            i.putExtra("data", hosEntity);
            activity.setResult(Activity.RESULT_OK, i);
            AppManager.getAppManager().finishActivity(activity);
        }
    }

}
