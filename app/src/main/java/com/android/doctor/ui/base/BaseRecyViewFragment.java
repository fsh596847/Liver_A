package com.android.doctor.ui.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.interf.OnScrollChangedListener;
import com.android.doctor.ui.widget.EmptyLayout;
import com.yuntongxun.kitsdk.utils.LogUtil;

import org.json.JSONObject;

import java.util.List;

import butterknife.InjectView;

@SuppressLint("NewApi")
public abstract class BaseRecyViewFragment<T>
        extends BaseFragment
        implements OnListItemClickListener {

	protected final String TAG = BaseRecyViewFragment.class.getSimpleName();
	public static final int PAGE_SIZE = 20;
	@InjectView(R.id.recyc_view)
	protected RecyclerView recyclerView;
	protected LayoutManager layoutManager;
	protected BaseRecyViewAdapter<T> mAdapter;
	protected int mCurPage = 0;
	protected OnScrollChangedListener scrollChangedListener;

    @Override
    protected void initView(View view) {
		super.initView(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyc_view);
		setupRecyclerView(view);
    }

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	protected void setupRecyclerView(View view) {
		setLayoutManager();		
		setAdapter();
		setupListener();
	}
	
	protected void setRecyclerViewHeight(int cellLayout) {
		View view = LayoutInflater.from(getActivity()).inflate(cellLayout, null, false);
		int viewHeight = view.getMeasuredHeight() * mAdapter.getItemCount();
		ViewGroup.LayoutParams lp = recyclerView.getLayoutParams();
		lp.height = viewHeight;
		recyclerView.setLayoutParams(lp);
		recyclerView.invalidate();
	}
	
	protected void setupListener() {
		if (recyclerView != null) {
			recyclerView.addOnScrollListener(new RecycViewOnScrollListener(layoutManager));
		}
	}
	
	protected void setViewStateNone(int curState) {
		if (curState == PAGE_STATE_REFRESH) {
			setRefreshState(false);
		}
		setLoaded();
		mState = PAGE_STATE_NONE;
	}
	
	protected void setAdapterState(List<T> data) {
		if (data == null) {			
			Log.w(TAG, "data is null");
			if (mState == PAGE_STATE_LOADMORE) {
				if (mCurPage > 0) {
					mCurPage--;
				}
			}
			mAdapter.setState(BaseRecyViewAdapter.STATE_NETWORK_ERROR);
			return;
		}
		
		int adapterState = BaseRecyViewAdapter.STATE_EMPTY_ITEM;
        if ((data.size() + mAdapter.getItemCount()) == 0) {
            adapterState = BaseRecyViewAdapter.STATE_EMPTY_ITEM;
        } else if (data.size() == 0 || (data.size() != PAGE_SIZE)) {
            adapterState = BaseRecyViewAdapter.STATE_NO_MORE;
            mAdapter.setFooterViewText("");
        } else {
            adapterState = BaseRecyViewAdapter.STATE_LOAD_MORE;
        }
        mAdapter.setState(adapterState);
	}
	
	protected void setAdapterData(List<T> data, int curState) {
		if (data == null || mAdapter == null) {
			Log.e(TAG, "setAdapterData() data or madapter is null, pagestate: " + curState);
			return;
		}
		
		if (curState == PAGE_STATE_REFRESH) {
			if (data.isEmpty()) {
				setMaskLayout(View.VISIBLE, EmptyLayout.OTHER_ERROR, "");
				Log.e(TAG, "setAdapterData() data is Empty, pageState: " + curState);
			}
			mAdapter.removeAll();
			mAdapter.setData(data);
		} else if (curState == PAGE_STATE_LOADMORE) {
			List<T> list = mAdapter.getData();
			for (int i = 0; i < data.size(); ++i) {
				T e = data.get(i);
				if (compareTo(list, e)) {
					data.remove(i);
					i--;
				}
			}
			if (data.isEmpty()) {
				Log.w(TAG, "data is empty");
				return;
			}
			mAdapter.addData(data);
		} 
	}

	protected void onLoad(int pageNum, int limit) {}

	protected void onLoadMore(int pageNum, int limit) {
		onLoad(pageNum, limit);
	}

	public BaseRecyViewAdapter<T> getmAdapter() {
		return mAdapter;
	}

	@Override
	public void onRefresh() {
		if (mState == PAGE_STATE_REFRESH || mState == PAGE_STATE_LOADMORE) {
			Log.i(TAG, "fragment is already in refrshing");
			return;
		}
		mState = PAGE_STATE_REFRESH;
		mCurPage = 0;
		onLoad(0, PAGE_SIZE);
	}

	protected void onSuccess(List<T> d) {
		int iState = mState;
		setAdapterState(d);
		setViewStateNone(iState);
		setAdapterData(d, iState);
	}

	protected void onFail(String msg) {
		setAdapterState(null);
		setViewStateNone(mState);
        setMaskLayout(View.VISIBLE, EmptyLayout.OTHER_ERROR, msg);
		LogUtil.d(LogUtil.getLogUtilsTag(BaseRecyViewFragment.class), msg);
	}

	public void setScrollChangedListener(OnScrollChangedListener scrollChangedListener) {
		this.scrollChangedListener = scrollChangedListener;
	}

	class RecycViewOnScrollListener extends RecyclerView.OnScrollListener {
		
		private LinearLayoutManager lManager;
		
		public RecycViewOnScrollListener(LayoutManager lm) {
			lManager = (LinearLayoutManager) lm;
		}
		
		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);	        
		}
		
		@Override
		public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
			super.onScrollStateChanged(recyclerView, newState);
			onScrollChanged();
		}
	}
	
	protected void onScrollChanged() {

        if (scrollChangedListener != null) {
            scrollChangedListener.onScrollChanged(recyclerView);
        }

		if (mAdapter == null ||
			mAdapter.getItemCount() == 0) {
            return;
        }
        
        if (mState == PAGE_STATE_LOADMORE || 
        	mState == PAGE_STATE_REFRESH) {
            return;
        }
        
        boolean scrollEnd = false;
        int lastPos = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
        View lastView = layoutManager.findViewByPosition(lastPos);
        if (lastView == mAdapter.getFooterView()) {
        	scrollEnd = true;
        }
        
		mCurPage = (lastPos / PAGE_SIZE -1);
		
        int adapterState = mAdapter.getState();
        
        if (mState == PAGE_STATE_NONE && scrollEnd) {
            if (adapterState == BaseRecyViewAdapter.STATE_LOAD_MORE
                    || adapterState == BaseRecyViewAdapter.STATE_NETWORK_ERROR) {

            	mCurPage++;
                mState = PAGE_STATE_LOADMORE;
                mAdapter.setFooterViewLoading();
                onLoadMore(mCurPage, PAGE_SIZE);
            }
        }
	}
	
	protected boolean compareTo(List<T> list, T e) {
		return false;
	}

    protected void setLayoutManager() {
		layoutManager = new LinearLayoutManager(getActivity());
		((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
		((LinearLayoutManager) layoutManager).setSmoothScrollbarEnabled(true);
		recyclerView.setLayoutManager(layoutManager);
		//recyclerView.addItemDecoration(new LinearSpacingItemDecoration(8, LinearLayoutManager.VERTICAL));
	}
	
	protected abstract void setAdapter(); 
	
	protected List<?> parseResponse(JSONObject jsonObject) {
		return null;
	};

	public boolean isScrollTop() {
        LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
        View firstView = lm.findViewByPosition(lm.findFirstVisibleItemPosition());
        if (firstView == null || (firstView.getTop() == 0 && lm.findFirstVisibleItemPosition()==0 )) {
            return true;
        }
        if (emptyLayout != null && emptyLayout.getVisibility() == View.VISIBLE) {
            return true;
        }
        return false;
	}
}
