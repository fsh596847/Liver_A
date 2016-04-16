package com.android.doctor.ui.activity;

import android.view.View;
import android.widget.ExpandableListView;

import com.android.doctor.ui.adapter.ListExpandableAdapter;
import com.android.doctor.R;
import com.android.doctor.ui.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-18.
 */
public class MyFavoriteActivity extends BaseActivity implements ExpandableListView.OnGroupClickListener{
    @InjectView(R.id.expandableListView)
    protected ExpandableListView mExpandListView;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.fragment_my_favorite);
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
        setActionBar("");
        final ListExpandableAdapter adapter = new ListExpandableAdapter(this);
        mExpandListView.setAdapter(adapter);
        mExpandListView.setOnGroupClickListener(this);
        mExpandListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {

                for (int i = 0; i < adapter.getGroupCount(); i++) {
                    if (groupPosition != i)
                        mExpandListView.collapseGroup(i);
                    }
                }
            });
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
        //parent.expandGroup(groupPosition);
        return false;
    }
}
