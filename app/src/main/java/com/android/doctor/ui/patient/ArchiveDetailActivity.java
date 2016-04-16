package com.android.doctor.ui.patient;

import android.widget.ExpandableListView;

import com.android.doctor.R;
import com.android.doctor.ui.adapter.ListExpandableAdapter;
import com.android.doctor.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Yong on 2016/4/6.
 */
public class ArchiveDetailActivity extends BaseActivity {
    @InjectView(R.id.expandableListView)
    protected ExpandableListView mExpandListView;
    private ListExpandableAdapter adapter;

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
        ArrayList p = new ArrayList();
        List<List<String>> c = new ArrayList();
        for (int i = 0 ; i < 3; ++i) {
            p.add("");
            List l = new ArrayList<String>();
            l.add("test");
            l.add("bbn");
            l.add("ccv");
            c.add(l);
        }
        adapter.setData(p, c);
    }
}
