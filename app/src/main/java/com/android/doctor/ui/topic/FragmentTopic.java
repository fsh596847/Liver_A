package com.android.doctor.ui.topic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.ui.adapter.TopicListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.android.doctor.ui.widget.DividerLineItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;

/**
 * Created by Yong on 2016/3/9.
 */
public class FragmentTopic extends BaseRecyViewFragment {

    private int mType = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic;
    }

    @Override
    protected void init() {
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt("type");
        }
    }


    @Override
    protected void setAdapter() {
        adapter = new TopicListAdapter();
        adapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(adapter);
        adapter.setData(getTestData());
    }

    @Override
    protected void setLayoutManager() {
        super.setLayoutManager();
        recyclerView.addItemDecoration(new DividerLineItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
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

    @Override
    public void onItemClick(int position, View view) {
        Intent intent = new Intent();
        intent.putExtra("data", "test");
        getActivity().setResult(Activity.RESULT_OK, intent);
        UIHelper.showtAty(getActivity(), TopicDetailActivity.class);
    }

    @OnClick(R.id.img_pub)
    protected void onPub() {
        UIHelper.showtAty(getActivity(), PubTopicActivity.class);
    }
}
