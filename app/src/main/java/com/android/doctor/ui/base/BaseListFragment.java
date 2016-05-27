package com.android.doctor.ui.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.ui.widget.EmptyLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

@SuppressLint("NewApi")
public abstract class BaseListFragment<T> extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener, OnItemClickListener,
        OnScrollListener {

    public static final String BUNDLE_KEY_CATALOG = "BUNDLE_KEY_CATALOG";


    @InjectView(R.id.listView)
    protected ListView mListView;

    protected BaseListAdapter<T> mAdapter;

    @InjectView(R.id.error_layout)
    protected EmptyLayout mErrorLayout;

    protected int mStoreEmptyState = -1;

    protected int mCurrentPage = 0;

    protected int mCatalog = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mCatalog = args.getInt(BUNDLE_KEY_CATALOG, 0);
        }
    }

    @Override
    public void initView(View view) {

        mErrorLayout.setOnLayoutClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mCurrentPage = 0;
                mState = PAGE_STATE_REFRESH;
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                onLoadData(mCurrentPage, getPageSize());
            }
        });

        mListView.setOnItemClickListener(this);
        mListView.setOnScrollListener(this);

        if (mAdapter != null) {
            mListView.setAdapter(mAdapter);
            mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        } else {
            mAdapter = getListAdapter();
            mListView.setAdapter(mAdapter);

            if (requestDataIfViewCreated()) {
                mErrorLayout.setErrorType(EmptyLayout.NETWORK_LOADING);
                mState = PAGE_STATE_NONE;
                onLoadData(mCurrentPage, getPageSize());
            } else {
                mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
            }

        }
        if (mStoreEmptyState != -1) {
            mErrorLayout.setErrorType(mStoreEmptyState);
        }
    }

    @Override
    public void onDestroyView() {
        mStoreEmptyState = mErrorLayout.getErrorState();
        super.onDestroyView();
    }


    protected abstract BaseListAdapter<T> getListAdapter();

    // 下拉刷新数据
    @Override
    public void onRefresh() {
        if (mState == PAGE_STATE_REFRESH || mState == PAGE_STATE_LOADMORE) {
            return;
        }
        // 设置顶部正在刷新
        mListView.setSelection(0);
        mCurrentPage = 0;
        mState = PAGE_STATE_REFRESH;
        onLoadData(0, getPageSize());
    }

    protected boolean requestDataIfViewCreated() {
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
            long id) {}

    // 是否需要自动刷新
    protected boolean needAutoRefresh() {
        return true;
    }

    /***
     * 获取列表数据
     *
     * @return void
     */
    protected void onLoadData(int pageNum, int limit) {}


    protected void onLoadDataSuccess(List<T> data) {
        if (data == null) {
            data = new ArrayList<T>();
        }

        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        if (mCurrentPage == 0) {
            mAdapter.clear();
        }

        for (int i = 0; i < data.size(); i++) {
            if (compareTo(mAdapter.getData(), data.get(i))) {
                data.remove(i);
                i--;
            }
        }
        int adapterState = BaseListAdapter.STATE_EMPTY_ITEM;
        if ((mAdapter.getCount() + data.size()) == 0) {
            adapterState = BaseListAdapter.STATE_EMPTY_ITEM;
        } else if (data.size() == 0
                || (data.size() < getPageSize() && mCurrentPage == 0)
                || data.size() != getPageSize()) {
            adapterState = BaseListAdapter.STATE_NO_MORE;
            mAdapter.notifyDataSetChanged();
        } else {
            adapterState = BaseListAdapter.STATE_LOAD_MORE;
        }
        mAdapter.setState(adapterState);
        mAdapter.addData(data);

        // 判断等于是因为最后有一项是listview的状态
        /*if (mAdapter.getCount() == 1) {
            if (needShowEmptyNoData()) {
                mErrorLayout.setErrorType(EmptyLayout.NODATA);
            } else {
                mAdapter.setState(BaseListAdapter.STATE_EMPTY_ITEM);
                mAdapter.notifyDataSetChanged();
            }
        }*/
    }

    /**
     * 是否需要隐藏listview，显示无数据状态
     *
     */
    protected boolean needShowEmptyNoData() {
        return true;
    }

    protected boolean compareTo(List<T> data, T enity) {
        int s = data.size();
        if (enity != null) {
            for (int i = 0; i < s; i++) {
                /*if (enity.getId() == data.get(i).getId()) {
                    return true;
                }*/
            }
        }
        return false;
    }

    protected int getPageSize() {
        return AppConfig.PAGE_SIZE;
    }

    protected void onRefreshNetworkSuccess() {}

    protected void onLoadDataError(String error) {
        if (mState == PAGE_STATE_LOADMORE) {
            if (mCurrentPage > 0) {
                mCurrentPage--;
            }
        }
        setLoaded();
        mErrorLayout.setErrorType(EmptyLayout.HIDE_LAYOUT);
        mAdapter.setState(BaseListAdapter.STATE_NETWORK_ERROR);
        mAdapter.notifyDataSetChanged();
    }

    // 完成刷新
    protected void onLoadFinish() {
        mState = PAGE_STATE_NONE;
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mAdapter == null || mAdapter.getCount() == 0) {
            return;
        }
        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        if (mState == PAGE_STATE_LOADMORE || mState == PAGE_STATE_REFRESH) {
            return;
        }
        // 判断是否滚动到底部
        boolean scrollEnd = false;
        try {
            if (view.getPositionForView(mAdapter.getFooterView()) == view
                    .getLastVisiblePosition())
                scrollEnd = true;
        } catch (Exception e) {
            scrollEnd = false;
        }

        if (mState == PAGE_STATE_NONE && scrollEnd) {
            if (mAdapter.getState() == BaseListAdapter.STATE_LOAD_MORE
                    || mAdapter.getState() == BaseListAdapter.STATE_NETWORK_ERROR) {
                mCurrentPage++;
                mState = PAGE_STATE_LOADMORE;
                onLoadData(mCurrentPage, getPageSize());
                mAdapter.setFooterViewLoading();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        // 数据已经全部加载，或数据为空时，或正在加载，不处理滚动事件
        // if (mState == STATE_NOMORE || mState == STATE_LOADMORE
        // || mState == STATE_REFRESH) {
        // return;
        // }
        // if (mAdapter != null
        // && mAdapter.getDataSize() > 0
        // && mListView.getLastVisiblePosition() == (mListView.getCount() - 1))
        // {
        // if (mState == STATE_NONE
        // && mAdapter.getState() == ListBaseAdapter.STATE_LOAD_MORE) {
        // mState = STATE_LOADMORE;
        // mCurrentPage++;
        //
        // }
        // }
    }

}
