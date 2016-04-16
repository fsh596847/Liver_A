package com.android.doctor.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.android.doctor.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentClinicPatient extends BaseRecyViewFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void setLayoutManager() {
        layoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        ((LinearLayoutManager) layoutManager).setSmoothScrollbarEnabled(true);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.addItemDecoration(new LinearSpacingItemDecoration(3, LinearLayoutManager.HORIZONTAL));
    }

    @Override
    protected void setAdapter() {
        //adapter = new PatientListAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setData(getTestData());
    }

    @Override
    public void onItemClick(int position, View view) {

    }

    private List getTestData(){
        List list = new ArrayList();
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        list.add(new Object());
        return list;
    }
}
