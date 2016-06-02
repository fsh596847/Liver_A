package com.android.doctor.ui.topic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.Constants;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.TopicList;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.TopicListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.android.doctor.ui.widget.DividerLineItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Yong on 2016/3/9.
 */
public class FragmentTopicList extends BaseRecyViewFragment {

    private int mType = 1;
    private String mTopicBarId;

    public static FragmentTopicList newInstance(int tp) {
        Bundle b = new Bundle();
        b.putInt("type", tp);
        FragmentTopicList frag = new FragmentTopicList();
        frag.setArguments(b);
        return frag;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void init() {
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt("type");
        }
    }

    @Override
    protected void initData() {
        super.initData();
        if (mType != Constants.TOPIC_TYPE_LIST_BY_BARID) {
            onRefresh();
        }
    }

    @Override
    protected void setAdapter() {
        mAdapter = new TopicListAdapter(R.layout.item_topic);
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setLayoutManager() {
        super.setLayoutManager();
        recyclerView.addItemDecoration(new DividerLineItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
    }

    public void setmTopicBarId(String mTopicBarId) {
        this.mTopicBarId = mTopicBarId;
    }

    protected void onLoad(int pageNum, int limit) {
        onLoadTopics(pageNum, limit);
    }

    private Map<String, String> getQueryParam(int pageNum, int limit) {
        Map<String, String> queryMap = new HashMap<>();
        User.UserEntity u = AppContext.context().getUser();
        if (u == null) return null;

        queryMap.put("page_size", "" + limit);
        if (mAdapter != null && mState == PAGE_STATE_LOADMORE) {
            TopicList.TopicEntity lItem = (TopicList.TopicEntity) mAdapter.getLastItem();
            if (lItem != null)
                queryMap.put("page_value_min", "" + lItem.getLastreplytime());
        }
        if (mType == Constants.TOPIC_TYPE_LIST_BY_BARID) {
            queryMap.put("topicbarid", mTopicBarId);
        } else if (mType == Constants.TOPIC_TYPE_I_PUB
                || mType == Constants.TOPIC_TYPE_I_INVOVLE) {
            queryMap.put("uid",  u.getDuid());
        }
        return queryMap;
    }


    private void onLoadTopics(int pageNum, int limit) {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<TopicList>> call = service.getTopicList(getQueryParam(pageNum, limit));
        call.enqueue(new RespHandler<TopicList>() {
            @Override
            public void onSucceed(RespEntity<TopicList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(new ArrayList());
                } else  {
                    onSuccess(resp.getResponse_params().getTopics());
                }
            }

            @Override
            public void onFailed(RespEntity<TopicList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    @Override
    public void onItemClick(int position, View view) {
        TopicList.TopicEntity topic = (TopicList.TopicEntity) mAdapter.getItem(position);
        TopicDetaActivity.startAty(getActivity(), "" + topic.getTopicid());
    }

}
