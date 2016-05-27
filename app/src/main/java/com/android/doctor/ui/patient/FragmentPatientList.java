package com.android.doctor.ui.patient;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.interf.OnRefreshDataListener;
import com.android.doctor.interf.OnScrollChangedListener;
import com.android.doctor.model.Constants;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.model.PatientList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
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

    private OnRefreshDataListener refreshDataListener;

    private int mType = 1;
    private Map<String, String> queryParam = new HashMap<>();
    private String pname;

    public static FragmentPatientList newInstance(int type, String pname) {
        FragmentPatientList f = new FragmentPatientList();
        Bundle b = new Bundle();
        b.putString("pname", pname);
        b.putInt("type", type);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void init() {
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt("type");
            pname = b.getString("pname");
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
        //我的患者，诊断记录，邀请患者
        int layout = (mType == Constants.PATIENT_TYPE_IS_DOCTOR ? R.layout.item_patient : mType == Constants.PATIENT_TYPE_DIAG_RECORD ? R.layout.item_diag_record : R.layout.item_patient_invite);
        mAdapter = new PatientListAdapter(layout);
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
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
        genQueryParam(pageNum, limit);

        if (mType == Constants.PATIENT_TYPE_IS_DOCTOR) { //医生的
            onLoadDoctorPatientRequest(queryParam);
        } else {
            onLoadHosPatientRequest(queryParam);
        }
    }

    private void genQueryParam(int pageNum, int limit) {
        User.UserEntity u = AppContext.context().getUser();
        if (u == null) return;

        queryParam.put("duid", "" + u.getDuid());
        queryParam.put("page_size", "" + limit);
        if (mCurPage != 0) {
            Object obj = mAdapter.getLastItem();
            if (obj != null) {
                if (PatientList.DataEntity.class.equals(obj.getClass())) {
                    queryParam.put("page_value_max", "" + ((PatientList.DataEntity)obj).getRelationship());
                } else if (HosPaitentList.HosPatientEntity.class.equals(obj.getClass())) {
                    queryParam.put("page_value_max", "" + ((HosPaitentList.HosPatientEntity)obj).getHospitalPatientId());
                }
            }
        }
        if (mType != Constants.PATIENT_TYPE_IS_DOCTOR) { //我的患者
            queryParam.put("hosid", "" + u.getHosid());
            if (mType == Constants.PATIENT_TYPE_OUT_ZY) { //住院患者
                queryParam.put("type", "zy");
            } else if (mType == Constants.PATIENT_TYPE_MZ) { //门诊患者
                queryParam.put("type", "mz");
            } else if (mType == Constants.PATIENT_TYPE_DIAG_RECORD) {//诊断记录
                queryParam.put("type", "zy");
                queryParam.put("py", pname);
            }
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
        call.enqueue(new RespHandler<HosPaitentList>() {
            @Override
            public void onSucceed(RespEntity<HosPaitentList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    onSuccess(resp.getResponse_params().getData());
                } else {
                    onSuccess(new ArrayList());
                }
            }

            @Override
            public void onFailed(RespEntity<HosPaitentList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    /*
    @Override
    protected void onLoadMore(int pageNum, int limit) {
        List data = mAdapter.getData();
    }*/

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
        queryParam.put(key, keywords);
        onRefresh();
    }

    public void onFilter(List<User.UserEntity.GroupsEntity.ChildgroupsEntity> groups) {
        if (groups.isEmpty()) {
            queryParam.remove("groups");
        } else {
            queryParam.put("groups", new Gson().toJson(groups));
        }
        onRefresh();
    }

    public void clearOption(String key) {
        queryParam.remove(key);
        onRefresh();
    }

    @Override
    protected void onScrollChanged() {
        super.onScrollChanged();
        if (isScrollTop()) {}
    }

    @Override
    public void onItemClick(int position, View view) {
        if (mType == Constants.PATIENT_TYPE_IS_DOCTOR) {
            PatientList.DataEntity d = (PatientList.DataEntity) mAdapter.getItem(position);
            if (d == null) return;
            PatientProfileActivity.startAty(getActivity(), d.getPuid());
        } else if (mType == Constants.PATIENT_TYPE_OUT_ZY || mType == Constants.PATIENT_TYPE_MZ) {
            InvitePatientActivity.startAty(getActivity(), (HosPaitentList.HosPatientEntity) mAdapter.getItem(position), 1);
        } else if (mType == Constants.PATIENT_TYPE_DIAG_RECORD) {
            if (view.getId() == R.id.tv_import_state) {
                HosPaitentList.HosPatientEntity item = (HosPaitentList.HosPatientEntity) mAdapter.getItem(position);
                procImports(item);
            }
        }
    }

    private void procImports(HosPaitentList.HosPatientEntity item) {
        if (item.getPid() > 0) {
            execImports(item);
        } else {
            execBinding(item);
        }
    }

    public Map<String,Object> getImportParam(HosPaitentList.HosPatientEntity item) {
        User.UserEntity u = AppContext.context().getUser();
        Map<String,Object> map = new HashMap<>();
        map.put("hosname", u.getHosname());
        map.put("hosid", u.getHosid());
        map.put("pid", "" + item.getPid());
        map.put("pname", item.getName());
        map.put("cisuuid", item.getCisuuid());
        map.put("visitid", item.getVisitid());
        map.put("duid", u.getDuid());
        map.put("usertype", "0");
        return map;
    }

    public Map<String,String> getBindingParam(HosPaitentList.HosPatientEntity item) {
        User.UserEntity u = AppContext.context().getUser();
        Map<String,String> map = new HashMap<>();
        map.put("hosid", u.getHosname());
        map.put("puid", u.getHosid());
        map.put("type", "zy");
        map.put("puuid", item.getPuuid());
        return map;
    }

    private void execImports(HosPaitentList.HosPatientEntity item) {
        UIHelper.showToast("正在导入档案,请稍后查看");
        Map<String,Object> map = getImportParam(item);
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.archImport(map);
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                RespEntity r = response.body();
                if (r != null) {
                    UIHelper.showToast(r.getError_msg());
                    //onProResult(r);
                }
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                //dismissProcessDialog();
            }
        });
    }

    private void execBinding(final HosPaitentList.HosPatientEntity item) {
        Map<String,String> map = getBindingParam(item);
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.bindRecord(map);
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                RespEntity r = response.body();
                if (r != null) {
                    UIHelper.showToast(r.getError_msg());
                }
                execImports(item);
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
            }
        });
    }

    private void onProResult(RespEntity r) {
        if (r != null) {

        }
    }
}
