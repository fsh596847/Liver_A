package com.android.doctor.ui.plan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.model.MedicInfo;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.SimpleTextListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentMedicInfoList extends BaseRecyViewFragment {

    public static final String ARGS_PARAM = "code";
    private String mCode;

    public static FragmentMedicInfoList newInstance(String arg) {
        Bundle args = new Bundle();
        args.putString(ARGS_PARAM, arg);
        FragmentMedicInfoList fragment = new FragmentMedicInfoList();
        fragment.setArguments(args);
        return fragment;
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
            mCode = b.getString(ARGS_PARAM);
        }
    }

    @Override
    protected void setAdapter() {
        adapter = new SimpleTextListAdapter();
        adapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        onLoad();
    }

    private void onLoad() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<MedicInfo>> call = service.getMedicInfo(mCode);
        call.enqueue(new Callback<RespEntity<MedicInfo>>() {
            @Override
            public void onResponse(Call<RespEntity<MedicInfo>> call, Response<RespEntity<MedicInfo>> response) {
                RespEntity<MedicInfo> body = response.body();
                if (response.isSuccessful()) {
                    if (body == null || body.getResponse_params() == null) {
                        onSuccess(new ArrayList());
                    } else {
                        onSuccess(body.getResponse_params().getList());
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
            public void onFailure(Call<RespEntity<MedicInfo>> call, Throwable t) {
                onFail("加载失败");
            }
        });
    }

    @Override
    public void onItemClick(int position, View view) {
        Activity aty = getActivity();
        if (aty != null) {
            MedicInfo.MedicInfoEntity infoEntity = (MedicInfo.MedicInfoEntity) adapter.getItem(position);
            Intent intent = new Intent();
            intent.putExtra("data", infoEntity);
            aty.setResult(Activity.RESULT_OK, intent);
            aty.finish();
        }
    }

}
