package com.android.doctor.ui.plan;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.adapter.TimeLineAdapter;
import com.android.doctor.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Yong on 2016/4/2.
 */
public class DiagRecordListActivity extends BaseActivity {

    @InjectView(R.id.recyc_view)
    protected RecyclerView mRecycler;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_record_list);
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar("");
        initRecycler();
    }

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        TimeLineAdapter adapter = new TimeLineAdapter(getData());
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(adapter);
    }

    private List<String> getData() {
        List<String> models = new ArrayList<String>();

        models.add("XiaoMing");
        models.add("XiaoMing");
        models.add("XiaoMing");
        models.add("XiaoMing");
        models.add("XiaoMing");
        models.add("XiaoMing");

        return models;
    }

    @OnClick(R.id.img_add)
    protected void onAdd() {
        UIHelper.showtAty(this, DiagRecordActivity.class);
    }
}
