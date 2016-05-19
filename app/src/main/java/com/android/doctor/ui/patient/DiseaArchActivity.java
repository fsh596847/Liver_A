package com.android.doctor.ui.patient;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.HealthRecordList;
import com.android.doctor.model.PatientBaseInfo;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.TimeLineAdapter;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.widget.EmptyLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Yong on 2016/4/4.
 */
public class DiseaArchActivity extends BaseActivity implements OnListItemClickListener{
    @InjectView(R.id.recyc_view)
    protected RecyclerView mRecycler;
    private TimeLineAdapter adapter;
    private PatientBaseInfo.PatientEntity patientEntity;

    public static void startAty(Context c, PatientBaseInfo.PatientEntity info) {
        Intent intent = new Intent(c, DiseaArchActivity.class);
        intent.putExtra("base", info);
        c.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_dis_archive);
    }

    @Override
    protected void init() {
        super.init();
        patientEntity = getIntent().getParcelableExtra("base");
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(R.string.disease_archive);
        initRecyView();
    }

    private void initRecyView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        adapter = new TimeLineAdapter();
        adapter.setItemClickListener(this);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        super.initData();
        getData();
    }

    private void getData() {
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("card", patientEntity.getCard());
        queryParam.put("recordtype", "10");
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<HealthRecordList>> call = service.getHealthRecordData(queryParam);
        call.enqueue(new RespHandler<HealthRecordList>() {
            @Override
            public void onSucceed(RespEntity<HealthRecordList> resp) {
                if (resp != null && resp.getResponse_params() != null){
                    adapter.setData(resp.getResponse_params().getHealthrecords());
                }
                setMaskLayout(View.GONE, EmptyLayout.HIDE_LAYOUT, "");
            }

            @Override
            public void onFailed(RespEntity<HealthRecordList> resp) {
                setMaskLayout(View.GONE, EmptyLayout.HIDE_LAYOUT, "");
            }
        });
    }

    @Override
    public void onItemClick(int position, View view) {
        HealthRecordList.HealthrecordEntity record = (HealthRecordList.HealthrecordEntity) adapter.getItem(position);
        ArchDetaActivity.startAty(this, "" + record.getPid(), record.getPrid());
    }

    @OnClick(R.id.tv_import)
    protected void onImports() {
        ArcImportListActivity.startAty(this, patientEntity.getName());
    }

    @OnClick(R.id.tv_statistic)
    protected void statLook() {
        if (patientEntity != null)
        LaborStatActivity.startAty(this, patientEntity.getPuid());
    }
}
