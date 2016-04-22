package com.android.doctor.ui.patient;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.LogUtil;
import com.android.doctor.interf.OnRefreshDataListener;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.model.PatientList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
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
    public static final int TYPE_DOCTOR_PATIENT = 1;
    public static final int TYPE_OUT_HOSPITAL_PATIENT = 2;
    public static final int TYPE_CLINIC_PATIENT = 3;

    private OnRefreshDataListener refreshDataListener;
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
        adapter = new PatientListAdapter(mType == TYPE_DOCTOR_PATIENT ? R.layout.item_patient : R.layout.item_patient_invite);
        adapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    public void setRefreshDataListener(OnRefreshDataListener refreshDataListener) {
        this.refreshDataListener = refreshDataListener;
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
        if (mType == TYPE_DOCTOR_PATIENT) {
            onLoadDoctorPatientRequest(option);
        } else {
            option.put("hosid", "" + u.getUser().getHosid());
            if (mType == TYPE_OUT_HOSPITAL_PATIENT) {
                option.put("type", "zy");
            } else if (mType == TYPE_CLINIC_PATIENT) {
                option.put("type", "mz");
            }
            onLoadHosPatientRequest(option);
        }
    }

    /**
     * 获取医生的患者
     * @param option
     */
    private void onLoadDoctorPatientRequest(Map<String, String> option) {
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

    /**
     * 获取待邀请的患者信息
     * @param option
     */
    private void onLoadHosPatientRequest(Map<String, String> option) {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<HosPaitentList>> call = service.getHosPatientByDuid(option);
        call.enqueue(new Callback<RespEntity<HosPaitentList>>() {
            @Override
            public void onResponse(Call<RespEntity<HosPaitentList>> call, Response<RespEntity<HosPaitentList>> response) {
                RespEntity<HosPaitentList> data = response.body();
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
            public void onFailure(Call<RespEntity<HosPaitentList>> call, Throwable t) {
                Log.d("onLoadHosPatientRequest", t.toString());
                onFail("加载失败");
            }
        });
    }

    @Override
    protected void onLoadMore(int pageNum, int limit) {
        List data = adapter.getData();
        LogUtil.d(LogUtil.getLogUtilsTag(FragmentPatientList.class), "onLoadMore, pn, limit " + pageNum +", " + limit);
        if (data != null) {
            int pos = data.size() - 1;
            if (0 <= pos) {
                Object obj = data.get(pos);
                if (obj != null) {
                    if (obj.getClass().equals(PatientList.DataEntity.class)) {
                        PatientList.DataEntity last = (PatientList.DataEntity) obj;
                        onLoad(last.getRelationship(), PAGE_SIZE);
                    } else if (obj.getClass().equals(HosPaitentList.HosPatientEntity.class)){
                        HosPaitentList.HosPatientEntity entity = (HosPaitentList.HosPatientEntity) obj;
                        onLoad(entity.getHospitalPatientId(), PAGE_SIZE);
                    }
                }
            }
        }
    }

    @Override
    protected void onSuccess(List d) {
        super.onSuccess(d);
        if (refreshDataListener != null) {
            refreshDataListener.onRefreshComplete("success");
        }
    }

    @Override
    protected void onFail(String msg) {
        super.onFail(msg);
        if (refreshDataListener != null) {
            refreshDataListener.onRefreshComplete(msg);
        }
    }

    public void onSearch(String key, String keywords) {
        option.put(key, keywords);
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
        if (mType == TYPE_DOCTOR_PATIENT) {
            PatientInfoActivity.startAty(getActivity(), (PatientList.DataEntity)adapter.getItem(position));
        } else if (mType == TYPE_OUT_HOSPITAL_PATIENT || mType == TYPE_CLINIC_PATIENT) {
            InvitePatientActivity.startAty(getActivity(), (HosPaitentList.HosPatientEntity)adapter.getItem(position), 1);
        }
    }
}
