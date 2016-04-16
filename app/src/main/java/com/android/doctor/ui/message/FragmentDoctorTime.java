package com.android.doctor.ui.message;

import android.view.View;
import android.widget.ListView;

import com.android.doctor.R;
import com.android.doctor.ui.adapter.DoctorTimeListAdapter;
import com.android.doctor.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Yong on 2016/3/30.
 */
public class FragmentDoctorTime extends BaseFragment {

    @InjectView(R.id.listview)
    protected ListView mListView;

    private DoctorTimeListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_doctor_time;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mAdapter = new DoctorTimeListAdapter(getActivity(), R.layout.item_doctor_time);
        mListView.setAdapter(mAdapter);
        mAdapter.setmData(getTestData());
    }

    private List getTestData() {
        List l = new ArrayList();
        l.add("a");l.add("a");l.add("a");l.add("a");l.add("a");
        return l;
    }
}
