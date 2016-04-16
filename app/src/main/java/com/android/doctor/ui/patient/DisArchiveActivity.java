package com.android.doctor.ui.patient;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.ui.adapter.TimeLineAdapter;
import com.android.doctor.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Yong on 2016/4/4.
 */
public class DisArchiveActivity extends BaseActivity implements OnListItemClickListener{
    @InjectView(R.id.recyc_view)
    protected RecyclerView mRecycler;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_dis_archive);
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
        adapter.setItemClickListener(this);
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

    @Override
    public void onItemClick(int position, View view) {
        UIHelper.showtAty(this, ArchiveActivity.class);
    }
}
