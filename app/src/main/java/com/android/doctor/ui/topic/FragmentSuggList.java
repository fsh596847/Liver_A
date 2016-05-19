package com.android.doctor.ui.topic;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.model.ArticleList;
import com.android.doctor.model.Constants;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.TopicListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.android.doctor.ui.mine.SuggDetaActivity;
import com.android.doctor.ui.widget.DividerLineItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Yong on 2016/3/9.
 */
public class FragmentSuggList extends BaseRecyViewFragment {

    private int mType = 1;
    private String keywords;//用户收藏的duid || 类别之下的类别名

    public static FragmentSuggList newInstance(int tp) {
        Bundle b = new Bundle();
        b.putInt("type", tp);
        FragmentSuggList frag = new FragmentSuggList();
        frag.setArguments(b);
        return frag;
    }

    public static FragmentSuggList newInstance(int tp, String kw) {
        Bundle b = new Bundle();
        b.putInt("type", tp);
        b.putString("keywords", kw);
        FragmentSuggList frag = new FragmentSuggList();
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
            keywords = b.getString("keywords");
        }
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    @Override
    protected void setAdapter() {
        mAdapter = new TopicListAdapter(R.layout.item_article);
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setLayoutManager() {
        super.setLayoutManager();
        recyclerView.addItemDecoration(new DividerLineItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
    }

    protected void onLoad(int pageNum, int limit) {
        if (mType == Constants.USER_COLLECTED_ARTICLE) {
            onLoadCollectedArticles(pageNum, limit);
        } else if (mType == Constants.ARTICLE_LIST_UNDER_CLASS) {
            onLoadArticlesByClass(pageNum, limit);
        }
    }

    private void onLoadCollectedArticles(int pageNum, int limit) {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ArticleList>> call = service.getSuggList(keywords);
        call.enqueue(new RespHandler<ArticleList>() {
            @Override
            public void onSucceed(RespEntity<ArticleList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(new ArrayList());
                } else  {
                    onSuccess(resp.getResponse_params().getSuggests());
                }
            }

            @Override
            public void onFailed(RespEntity<ArticleList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    private void onLoadArticlesByClass(int pageNum, int limit) {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ArticleList>> call = service.getArticleList(genParam(pageNum, limit));
        call.enqueue(new RespHandler<ArticleList>() {
            @Override
            public void onSucceed(RespEntity<ArticleList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(new ArrayList());
                } else  {
                    onSuccess(resp.getResponse_params().getSuggests());
                }
            }

            @Override
            public void onFailed(RespEntity<ArticleList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    private Map<String,String> genParam(int pageNum, int limit) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("page_size", "" + limit);
        queryMap.put("keywords", "" + keywords);
        if (pageNum == 0) {
            queryMap.put("page_value_max", "" + 0);
        } else {
            ArticleList.SuggestsEntity e = (ArticleList.SuggestsEntity) mAdapter.getLastItem();
            if (e != null)
            queryMap.put("page_value_last", "" + e.getSuggid());
        }
        return queryMap;
    }


    @Override
    public void onItemClick(int position, View view) {
        ArticleList.SuggestsEntity article = (ArticleList.SuggestsEntity) mAdapter.getItem(position);
        SuggDetaActivity.startAty(getActivity(), article);
    }

}
