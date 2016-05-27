package com.android.doctor.ui.mine;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.ArticleList;
import com.android.doctor.model.Constants;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.SuggClassList;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.SeaKBaseListAdapter;
import com.android.doctor.ui.adapter.sticky_adapter.StickyRecyclerHeadersAdapter;
import com.android.doctor.ui.adapter.sticky_adapter.StickyRecyclerHeadersDecoration;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.android.doctor.app.DataCacheManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/8.
 */
public class FragmentSearchSubList extends BaseRecyViewFragment {

    public static final int TYPE_STATE_PERSONAL = 1;
    public static final int TYPE_STATE_ALL = 2;
    public static final int TYPE_STATE_ING = 3;
    public static final int TYPE_STATE_FINISHED = 4;
    public static final String EXTRA_PARAM = "type_state";
    private int mType = TYPE_STATE_PERSONAL;
    private String puid ;
    private String keywords;

    public static FragmentSearchSubList newInstance(String id) {
        FragmentSearchSubList f = new FragmentSearchSubList();
        Bundle b = new Bundle();
        b.putString("id", id);
        f.setArguments(b);
        return f;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    @Override
    protected void init() {
        super.init();
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt(EXTRA_PARAM);
            puid = b.getString("id");
        }
    }

    @Override
    protected void setAdapter() {
        mAdapter = new SeaKBaseListAdapter();
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration((StickyRecyclerHeadersAdapter)mAdapter);
        recyclerView.addItemDecoration(headersDecor);
        // setTouchHelper();
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                headersDecor.invalidateHeaders();
            }
        });
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void onSearch(String word) {
        this.setKeywords(word);
        onRefresh();
    }

    @Override
    protected void initData() {
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        if (pageNum == 0) {
            onLoadMoreSuggClass();
        } else {
            onLoadArticlesByClass(pageNum, limit);
        }
    }

    private void onLoadMoreSuggClass() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<SuggClassList>> call = service.getSearchSuggClassList(keywords);
        call.enqueue(new RespHandler<SuggClassList>() {
            @Override
            public void onSucceed(RespEntity<SuggClassList> resp) {
                if (resp == null || resp.getResponse_params() == null) {
                    onSuccess(new ArrayList());
                } else  {
                    List<SuggClassList.SuggEntity> ds = new ArrayList<SuggClassList.SuggEntity>();
                    List<SuggClassList.SuggEntity> data = resp.getResponse_params().getData();
                    for (int i = 0; i < data.size() && i < 5; ++i) {
                        ds.add(data.get(i));
                    }
                    onSuccess(ds);
                }
                onSuggClassLoaded();
            }

            @Override
            public void onFailed(RespEntity<SuggClassList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    private void onSuggClassLoaded() {
        mState = PAGE_STATE_LOADMORE;
        onLoadArticlesByClass(1, PAGE_SIZE);
    }

    private void onLoadArticlesByClass(int pageNum, int limit) {
        Map<String,String> param = genParam(pageNum, limit, keywords);
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ArticleList>> call = service.getArticleList(param);
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

    private Map<String,String> genParam(int pageNum, int limit, String keywords) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("page_size", "" + limit);
        if (!TextUtils.isEmpty(keywords)) {
            queryMap.put("keywords", "" + keywords);
        }
        if (pageNum == 0) {
            queryMap.put("page_value_max", "" + 0);
        } else {
            Object o = mAdapter.getLastItem();
            if (o != null) {
                if (ArticleList.SuggestsEntity.class.equals(o.getClass())) {
                    ArticleList.SuggestsEntity e = (ArticleList.SuggestsEntity)o;
                    queryMap.put("page_value_last", "" + e.getSuggid());

                } else if (SuggClassList.SuggEntity.class.equals(o.getClass())) {
                    SuggClassList.SuggEntity s = (SuggClassList.SuggEntity) o;
                    queryMap.put("page_value_last", "" + s.getCode());
                }
            }
        }
        return queryMap;
    }

    @Override
    public void onItemClick(int position, View view) {
        Object obj =  mAdapter.getItem(position);
        if (obj != null) {
            if (obj.getClass().equals(SuggClassList.SuggEntity.class)) {
                if (view.getId() == R.id.img_subs) {//subscribe or unsubscribe
                    Map<String,Object> param = getParam((int)view.getTag(), (SuggClassList.SuggEntity)obj);
                    if (param == null) return;
                    processSubs(param);
                } else {
                    SuggListActivity.startAty(getActivity(), (SuggClassList.SuggEntity) obj);
                }
            } else if (obj.getClass().equals(ArticleList.SuggestsEntity.class)) {
                SuggDetaActivity.startAty(getActivity(), (ArticleList.SuggestsEntity) obj);
            }
        }
    }

    protected void processSubs( Map<String,Object> param){

        ApiService apiService = RestClient.createService(ApiService.class);
        Call<RespEntity> call = apiService.subOrUnSub(param);
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                //UIHelper.showToast(status == 0 ? "收藏成功" : "取消收藏成功");
                DataCacheManager.getInstance().onLoadCollectArticles();
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                //UIHelper.showToast(status == 0 ? "收藏失败" : "取消收藏失败");
            }
        });
    }

    public Map<String,Object> getParam(int subState, SuggClassList.SuggEntity se) {

        Map<String, Object> param = new HashMap<>();
        List<String> codeList = new ArrayList<>();
        List<SuggClassList.SuggEntity> collects = DataCacheManager.getInstance().getSubjects();
        if (collects != null) {
            for (int i = 0; i < collects.size(); ++i) {
                codeList.add(collects.get(i).getCode());
            }
        }

        if (subState == Constants.KBASE_SUBJECT_STATE_UNSUBSCRIBED) {
            codeList.add(se.getCode());
        } else {
            if (collects == null) return null;
            for (int i = 0; i < collects.size(); ++i) {
                SuggClassList.SuggEntity sg = collects.get(i);
                if (sg.getCode().equals(se.getCode())) {
                    continue;
                }
                codeList.add(sg.getCode());
            }
        }
        User.UserEntity u = AppContext.context().getUser();
        param.put("uid", u.getDuid());
        param.put("classids", codeList);
        return param;
    }
}
