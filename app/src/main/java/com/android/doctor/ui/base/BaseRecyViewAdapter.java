package com.android.doctor.ui.base;

import java.util.ArrayList;
import java.util.List;

import com.android.doctor.app.AppContext;
import com.android.doctor.helper.StringHelper;
import com.android.doctor.R;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.interf.OnListItemClickListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public abstract class BaseRecyViewAdapter<T> extends RecyclerView.Adapter<ViewHolder>{
	
	public static enum ITEM_STYLE {GRID, LIST};
	
    public static final int STATE_EMPTY_ITEM = 0;
    public static final int STATE_LOAD_MORE = 1;
    public static final int STATE_NO_MORE = 2;
    public static final int STATE_NO_DATA = 3;
    public static final int STATE_LESS_ONE_PAGE = 4;
    public static final int STATE_NETWORK_ERROR = 5;
    public static final int STATE_OTHER = 6;
    
    protected static final int VIEW_TYPE_FOOTER = 100;
    protected static final int VIEW_TYPE_ITEM = 101;
    protected int state = STATE_EMPTY_ITEM;

    protected int loadmoreText;
    protected int loadFinishText;
    protected int noDataText;
    protected int mScreenWidth;	
	protected List<T> data = new ArrayList<T>();		
	protected LayoutInflater inflater;	
	protected ImageLoader mImageLoader;	
	protected int menuItemLayout;	
	private LinearLayout mFooterView;
	private boolean isFooterVisible = true;
	
	protected OnListItemClickListener itemOptionClickListener;
	protected ITEM_STYLE itemStyle = ITEM_STYLE.GRID;
		
	public void setItemOptionClickListener(
			OnListItemClickListener itemOptionClickListener) {
		this.itemOptionClickListener = itemOptionClickListener;
	}

	public BaseRecyViewAdapter() {
		super();
		inflater = LayoutInflater.from(AppContext.context());
		loadmoreText = R.string.loading;
	    loadFinishText = R.string.loading_no_more;
	    noDataText = R.string.no_data;
	}
	
	@Override
	public int getItemViewType(int position) {
		 if (isFooterVisible && position == getItemCount() - 1) {
			 if (state == STATE_LOAD_MORE || 
					 state == STATE_NO_DATA ||
					 state == STATE_NO_MORE || 
					 state == STATE_EMPTY_ITEM || 
					 state == STATE_NETWORK_ERROR ) {
				 
				 return VIEW_TYPE_FOOTER;
			 }
		 }
		 return VIEW_TYPE_ITEM;
	};
	
	public void setFooterViewLoading(String loadMsg) {
		if (mFooterView == null) {
			return;
		}
		
        ProgressBar progress = (ProgressBar) mFooterView
                .findViewById(R.id.progressbar);
        TextView text = (TextView) mFooterView.findViewById(R.id.text);
        mFooterView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.VISIBLE);
        text.setVisibility(View.VISIBLE);
        if (StringHelper.isEmpty(loadMsg)) {
            text.setText(loadmoreText);
        } else {
            text.setText(loadMsg);
        }
    }

    public void setFooterViewLoading() {
        setFooterViewLoading("");
    }

    public void setFooterViewText(String msg) {
    	if (mFooterView == null) {
			return;
		}
    	
        ProgressBar progress = (ProgressBar) mFooterView
                .findViewById(R.id.progressbar);
        TextView text = (TextView) mFooterView.findViewById(R.id.text);
        mFooterView.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
        text.setVisibility(View.VISIBLE);
        text.setText(msg);
    }
	
	@SuppressLint("InflateParams")
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
		
		if (VIEW_TYPE_FOOTER == viewType) {
			this.mFooterView = (LinearLayout) inflater.inflate(R.layout.list_cell_footer, null);
            fillFooterView();   
            return new ViewHolder(mFooterView) {};
		}
				
		return getViewHolder(viewGroup, viewType);
	}
	
    private void fillFooterView() {
    	ProgressBar progress = (ProgressBar) mFooterView.findViewById(R.id.progressbar);
        TextView text = (TextView) mFooterView.findViewById(R.id.text);
    	switch (getState()) {
        case STATE_LOAD_MORE:
            setFooterViewLoading();
            break;
        case STATE_NO_MORE:
            mFooterView.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
            text.setText(loadFinishText);
            break;
        case STATE_EMPTY_ITEM:
            progress.setVisibility(View.GONE);
            mFooterView.setVisibility(View.VISIBLE);
            text.setText(noDataText);
            break;
        case STATE_NETWORK_ERROR:
            mFooterView.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
            text.setVisibility(View.VISIBLE);
            if (DeviceHelper.hasInternet()) {
                text.setText(R.string.generic_server_down);
            } else {
                text.setText(R.string.no_network_connection);
            }
            break;
        default:
            progress.setVisibility(View.GONE);
            mFooterView.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
            break;
        }
	}

	protected abstract ViewHolder getViewHolder(ViewGroup view, int viewType);

	public View getFooterView() {
        return this.mFooterView;
    }
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setData(List<T> data) {
		this.data.clear();
		this.data.addAll(data);
		notifyDataSetChanged();
	}
	
	public List<T> getData() {
		return data;
	}

	public void addData(List<T> data) {	
		//Log.i(BaseRecyViewAdapter.class.getSimpleName(), data.toString());
		int position = this.data.size();
		this.data.addAll(data);
		this.notifyItemRangeInserted(position, data.size());
	}
	
		
	public void setItemStyle(ITEM_STYLE itemStyle) {
		this.itemStyle = itemStyle;
	}

	@Override
	public int getItemCount() {
		if (isFooterVisible) {
			switch (getState()) {
	        case STATE_EMPTY_ITEM:
	            return data.size();
	        case STATE_NETWORK_ERROR:
	        case STATE_LOAD_MORE:
	            return data.size() + 1;
	        case STATE_NO_DATA:
	            return 1;
	        case STATE_NO_MORE:
	            return data.size() + 1;
	        case STATE_LESS_ONE_PAGE:
	            return data.size();
	        default:
	            break;
	        }
		}
		return data.size();
	}
	
	public T getItem(int pos) {
		if (data == null) return null;
		if (0 <= pos  && pos < data.size()) {
			return data.get(pos);
		}
		return null;
	}

	public T getLastItem() {
		if (data == null) return null;
		int pos = data.size() - 1;
		if (0 <= pos  && pos < data.size()) {
			return data.get(pos);
		}
		return null;
	}

	public void removeItem(int pos) {
		data.remove(pos);
		notifyItemRemoved(pos);
		notifyItemRangeChanged(pos, getItemCount());
	}
	
	public void removeAll() {
		data.clear();
		notifyDataSetChanged();
	}
	
	public void setImageLoader(ImageLoader imageLoader) {
		mImageLoader = imageLoader;
	}
	
	public void setFooterVisible(boolean isFooterVisible) {
		this.isFooterVisible = isFooterVisible;
	}
}
