package com.android.doctor.ui.chat;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.android.doctor.R;
import com.android.doctor.model.DoctorList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.DoctorListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentDoctorList extends BaseRecyViewFragment {
    @InjectView(R.id.fl_container)
    protected FrameLayout mFrameLayout;

    private Map<String, String> queryMap = new HashMap<>();
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact_list;
    }

    @Override
    protected void setAdapter() {
        mAdapter = new DoctorListAdapter();
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        Log.d("FragmentDoctorList", new Gson().toJson(queryMap));
        queryMap.put("page_size", "" + limit);
        if (pageNum == 0) {
            queryMap.put("page_value_max", "" + 0);
        } else if (mState == PAGE_STATE_LOADMORE) {
           List<DoctorList.DoctorEntity> data = mAdapter.getData();
            if (data != null) {
               DoctorList.DoctorEntity de = data.get(data.size()-1);
                if (de != null) {
                    queryMap.put("page_value_max", "" + de.getDuid());
                }
            }
        }

        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<DoctorList>> call = service.getDoctorList(queryMap);
        call.enqueue(new Callback<RespEntity<DoctorList>>() {
            @Override
            public void onResponse(Call<RespEntity<DoctorList>> call, Response<RespEntity<DoctorList>> response) {
                RespEntity<DoctorList> data = response.body();
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
            public void onFailure(Call<RespEntity<DoctorList>> call, Throwable t) {
                Log.d(TAG, t.toString());
                onFail("加载失败");
            }
        });
    }

    public void setQueryParam(String k, String v) {
        queryMap.put(k, v);
    }

    public void onFilter(Map<String, String> map ) {
        if (map == null) return;
        if (map.isEmpty()) {
            queryMap.clear();
        } else {
            queryMap.putAll(map);
        }
        onRefresh();
    }

    public void onSearch(DoctorList.ColGLEntity glEntity) {
        Gson  gson = new Gson();
        String v = queryMap.get("keywords");
        if (TextUtils.isEmpty(v)) {
            List<DoctorList.ColGLEntity> list = new ArrayList<>();
            list.add(glEntity);
            JSONArray jsArray = new JSONArray(list);
            queryMap.put("keywords", jsArray.toString());
        } else {
            List<DoctorList.ColGLEntity> colList = gson.fromJson(v,
                    new TypeToken<List<DoctorList.ColGLEntity>>() {}.getType());
            int idx = findColIndex(colList, glEntity.getColname());
            if (idx != -1) {
                colList.remove(idx);
            }
            colList.add(glEntity);
            JSONArray jsArray = new JSONArray(colList);
            queryMap.put("keywords", jsArray.toString());
        }
        onRefresh();
    }

   /* public void onFilter(List<User.UserEntity.GroupsEntity.ChildgroupsEntity> groups) {
        if (groups.isEmpty()) {
            option.remove("groups");
        } else {
            option.put("groups", new Gson().toJson(groups));
        }
        onRefresh();
    }*/

    public void clearOption(String colname) {
        String v = queryMap.get("keywords");
        if (TextUtils.isEmpty(v)) return;
        Gson  gson = new Gson();
        List<DoctorList.ColGLEntity> colList = gson.fromJson(v, new TypeToken<List<DoctorList.ColGLEntity>>(){}.getType());
        int idx = findColIndex(colList, colname);
        if (idx != -1) {
            colList.remove(idx);
        }
        if (colList.isEmpty()) {
            queryMap.remove("keywords");
        } else {
            JSONArray jsArray = new JSONArray(colList);
            queryMap.put("keywords", jsArray.toString());
        }
        onRefresh();
    }

    private int findColIndex(List<DoctorList.ColGLEntity> col, String colname) {

        if (col == null) return -1;
        for (int i = 0; i < col.size(); ++i) {
            DoctorList.ColGLEntity gl = col.get(i);
            if (gl == null) continue;
            if (gl.getColname().equals(colname)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onItemClick(int position, View view) {
        DoctorList.DoctorEntity doctorEntity = (DoctorList.DoctorEntity) mAdapter.getItem(position);
        if (doctorEntity != null) {
            DoctorProfileActivity.startAty(getActivity(), doctorEntity.getDuid());
        }
    }

}
