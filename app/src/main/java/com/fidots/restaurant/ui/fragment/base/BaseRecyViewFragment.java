package com.fidots.restaurant.ui.fragment.base;

import java.io.Serializable;
import java.util.List;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.android.volley.VolleyError;
import com.fidots.restaurant.api.ApiClient;
import com.fidots.restaurant.api.NetworkResponseHandler;
import com.fidots.restaurant.app.AppContext;
import com.fidots.restaurant.interf.OnAdapterItemClickListener;
import com.fidots.restaurant.ui.adapter.base.BaseRecyViewAdapter;
import com.fidots.restaurant.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

@SuppressLint("NewApi")
public abstract class BaseRecyViewFragment<T>
        extends BaseFragment
        implements OnAdapterItemClickListener {

	protected final String TAG = BaseRecyViewFragment.class.getSimpleName();
	public static final int PAGE_SIZE = 20;
	protected static int RECY_VIEW_SPAN_1 = 1;
	protected static int RECY_VIEW_SPAN_2 = 2;
	protected static int RECY_VIEW_ITEM_SPACE_8 = 8;

	@InjectView(R.id.recyc_view)
	protected RecyclerView recyclerView;
	protected LayoutManager layoutManager;
	protected BaseRecyViewAdapter<T> adapter;
	protected int mCurPage = 0;
	protected View mRootView;

    @Override
    protected void initView(View view) {
        setupRecyclerView(view);
    }

    protected void setupRecyclerView(View view) {
		setLayoutManager();		
		setAdapter();
		setupListener();
	}
	
	protected void setRecyclerViewHeight(int cellLayout) {
		View view = LayoutInflater.from(getActivity()).inflate(cellLayout, null, false);
		int viewHeight = view.getMeasuredHeight() * adapter.getItemCount();
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
	
	protected void setViewState(int curState) {
		if (curState == PAGE_STATE_REFRESH) {
			setRefreshingState(false);
		}
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
			adapter.setState(BaseRecyViewAdapter.STATE_NETWORK_ERROR);
			return;
		}
		
		int adapterState = BaseRecyViewAdapter.STATE_EMPTY_ITEM;
        if ((data.size() + adapter.getItemCount()) == 0) {
            adapterState = BaseRecyViewAdapter.STATE_EMPTY_ITEM;
        } else if (data.size() == 0 || (data.size() < PAGE_SIZE)) {
            adapterState = BaseRecyViewAdapter.STATE_NO_MORE;
            adapter.setFooterViewText("");
        } else {
            adapterState = BaseRecyViewAdapter.STATE_LOAD_MORE;
        }
        adapter.setState(adapterState);
	}
	
	protected void setAdapterData(List<T> data, int curState) {
		if (data == null || adapter == null) {
			Log.e(TAG, "setAdapterData() data or adapter is null, pagestate: " + curState);
			return;
		}
		
		if (data.isEmpty()) {
			Log.e(TAG, "setAdapterData() data is Empty, pageState: " + curState);
		}
		
		if (curState == PAGE_STATE_REFRESH) {
			adapter.removeAll();			
			adapter.setData(data);
		} else if (curState == PAGE_STATE_LOADMORE) {
			List<T> list = adapter.getData();
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
			adapter.addData(data);
		} 
	}

    protected void onLoad(int pageNum, int limit) {
		String url = getURL(pageNum, limit);
		if (url != null) {
			if (mState == PAGE_STATE_REFRESH) {
				setRefreshingState(true);
			}
			ApiClient apiClient = ApiClient.getClient(AppContext.context());
			apiClient.get(url, TAG, getNetworkRespHandler());
		} else {
			setRefreshingState(false);
			mState = PAGE_STATE_NONE;
			Log.w(TAG, "url is null");
		}
	}
	
	protected void onLoadMore(int pageNum, int limit) {
		onLoad(pageNum, limit);
	}
	
	protected NetworkResponseHandler getNetworkRespHandler() {
		return new NetworkResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonObject) {
				List<?> list = parseResponse(jsonObject);					
				setAdapterData((List<T>) list, mState);		
				setAdapterState((List<T>) list);
				setViewState(mState);
			}
			@Override
			public void onFail(VolleyError error) {		
				processError(error);
				setAdapterState(null);
				setViewState(mState);	
			}
		};
	}
	
	protected void processError(VolleyError error) {}
	
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
		if (adapter == null || 
			adapter.getItemCount() == 0) {
            return;
        }
        
        if (mState == PAGE_STATE_LOADMORE || 
        	mState == PAGE_STATE_REFRESH) {
            return;
        }
        
        boolean scrollEnd = false;
        int lastPos = ((LinearLayoutManager)layoutManager).findLastVisibleItemPosition();
        View lastView = layoutManager.findViewByPosition(lastPos);
        if (lastView == adapter.getFooterView()) {
        	scrollEnd = true;
        }
        
		mCurPage = (lastPos / PAGE_SIZE -1);
		
        int adapterState = adapter.getState();
        
        if (mState == PAGE_STATE_NONE && scrollEnd) {
            if (adapterState == BaseRecyViewAdapter.STATE_LOAD_MORE
                    || adapterState == BaseRecyViewAdapter.STATE_NETWORK_ERROR) {

            	mCurPage++;
                mState = PAGE_STATE_LOADMORE;
                adapter.setFooterViewLoading();
                onLoadMore(mCurPage, PAGE_SIZE);
            }
        }
	}

    protected String getURL(int pageNum, int limit) {
		return null;
	}
	
	protected boolean compareTo(List<T> list, T e) {
		return false;
	}

    protected abstract void setLayoutManager();
	
	protected abstract void setAdapter(); 
	
	protected List<?> parseResponse(JSONObject jsonObject) {
		return null;
	};
}
