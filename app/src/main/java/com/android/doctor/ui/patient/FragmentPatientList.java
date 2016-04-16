package com.android.doctor.ui.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.PatientList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.patient.PatientInfoActivity;
import com.android.doctor.ui.adapter.PatientListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/9.
 */
public class FragmentPatientList extends BaseRecyViewFragment {
    public static final int TYPE_MY_PATIENT = 1;
    public static final int TYPE_LEAVE_HOSPITAL_PATIENT = 2;
    public static final int TYPE_CLINIC_PATIENT = 3;

    private int mType = 1;
    private Map<String, String> option = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void init() {
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt("type");
        }
    }

    @Override
    protected void setLayoutManager() {
        layoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        ((LinearLayoutManager) layoutManager).setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addItemDecoration(new DividerLineItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
    }

    @Override
    protected void setAdapter() {
        adapter = new PatientListAdapter(mType == TYPE_MY_PATIENT ? R.layout.item_patient : R.layout.item_patient_invite);
        adapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    @Override
    protected void onLoad(int pageNum, int limit) {
        Log.d("FragmentPatientList", new Gson().toJson(option));
        User u = AppContext.context().getUser();
        if (u == null || u.getUser() == null) {
            return;
        }
        option.put("duid", "" + u.getUser().getDuid());
        option.put("page_size", "" + limit);
        if (mCurPage != 0) {
            option.put("page_value_max", "" + pageNum);
        }
        onExecRequest(option);
    }

    private void onExecRequest(Map<String, String> option) {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PatientList>> call = service.getPatientList(option);
        call.enqueue(new Callback<RespEntity<PatientList>>() {
            @Override
            public void onResponse(Call<RespEntity<PatientList>> call, Response<RespEntity<PatientList>> response) {
                RespEntity<PatientList> data = response.body();
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
            public void onFailure(Call<RespEntity<PatientList>> call, Throwable t) {
                onFail("加载失败");
            }
        });
    }

    @Override
    protected void onLoadMore(int pageNum, int limit) {
        List<PatientList.DataEntity> data = adapter.getData();
        if (data != null) {
            int pos = data.size() - 1;
            if (0 <= pos) {
                PatientList.DataEntity last = data.get(pos);
                onLoad(last.getRelationship(), PAGE_SIZE);
            }
        }
    }

    public void onSearch(String keywords) {
        option.put("keywords", keywords);
        onRefresh();
    }

    public void onFilter(List<User.UserEntity.GroupsEntity.ChildgroupsEntity> groups) {
        if (groups.isEmpty()) {
            option.remove("groups");
        } else {
            option.put("groups", new Gson().toJson(groups));
        }
        onRefresh();
    }

    public void clearOption(String key) {
        option.remove(key);
        onRefresh();
    }

    @Override
    public void onItemClick(int position, View view) {
        if (mType == TYPE_MY_PATIENT) {
            PatientInfoActivity.startAty(getActivity(), (PatientList.DataEntity)adapter.getItem(position));
        }
    }
}
