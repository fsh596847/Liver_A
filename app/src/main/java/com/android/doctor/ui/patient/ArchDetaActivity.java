package com.android.doctor.ui.patient;

import android.content.Context;
import android.content.Intent;
import android.widget.ExpandableListView;

import com.android.doctor.R;
import com.android.doctor.model.LaborArchList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.ListExpandableAdapter;
import com.android.doctor.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit2.Call;

/**
 * Created by Yong on 2016/4/6.
 */
public class ArchDetaActivity extends BaseActivity {
    @InjectView(R.id.expandableListView)
    protected ExpandableListView mExpandListView;
    private ListExpandableAdapter adapter;
    private String pid;
    private String prid;

    public static void startAty(Context c, String pid, String prid) {
        Intent intent = new Intent(c, ArchDetaActivity.class);
        intent.putExtra("pid", pid);
        intent.putExtra("prid", prid);
        c.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_archive_detail);
    }

    @Override
    protected void initView() {
        setActionBar("");
        adapter = new ListExpandableAdapter(this);
        mExpandListView.setAdapter(adapter);
        for(int i = 0; i < adapter.getGroupCount(); i++){
            mExpandListView.expandGroup(i);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        prid = intent.getStringExtra("prid");
        getData();
    }

    private void getData() {
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("pid", pid);
        queryParam.put("prid", prid);
        queryParam.put("recordtype", "20");
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<LaborArchList>> call = service.getLaborArchData(queryParam);
        call.enqueue(new RespHandler<LaborArchList>() {
            @Override
            public void onSucceed(RespEntity<LaborArchList> resp) {
                if (resp == null || resp.getResponse_params() == null){

                } else {
                    adapter.setmData(resp.getResponse_params().getHealthrecords());
                }
            }

            @Override
            public void onFailed(RespEntity<LaborArchList> resp) {
            }
        });
    }
}
