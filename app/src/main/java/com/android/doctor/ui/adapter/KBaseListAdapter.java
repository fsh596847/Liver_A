package com.android.doctor.ui.adapter;

import com.android.doctor.R;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.model.SuggClassList;
import com.android.doctor.ui.adapter.sticky_adapter.StickyRecyclerHeadersAdapter;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.KSubjectViewHolder;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

public class KBaseListAdapter<T> extends BaseRecyViewAdapter<T> implements StickyRecyclerHeadersAdapter<ViewHolder> {

	private int mItemHeight;
	private int itemLayout;

	public KBaseListAdapter(int layout) {
		int paddingPx = (int) DeviceHelper.dpToPixels(24);
		int screenWidth = (int) DeviceHelper.getScreenWidth() - paddingPx;
		mItemHeight = screenWidth / 2;
        this.itemLayout = layout;
	}
	
	@Override
	public int getItemViewType(int position) {
		int viewType = super.getItemViewType(position);
		if (viewType == VIEW_TYPE_ITEM) {
			Object item = data.get(position);
		}
		return viewType;
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {			
		int viewType = holder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (KSubjectViewHolder.class.equals(holder.getClass())) {
                Object obj = this.getItem(position);
                KSubjectViewHolder vh = (KSubjectViewHolder) holder;
                if (obj != null) {
                    if (obj.getClass().equals(SuggClassList.SuggEntity.class)) {
                        vh.setViewData((SuggClassList.SuggEntity) obj);
                    }
                }
            }
        }
	}

	@Override
	protected ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {

        View view5 = inflater.inflate(itemLayout, viewGroup, false);
        if (itemLayout == R.layout.item_subject) {
            View v = view5.findViewById(R.id.fl_subject);
            LayoutParams lParams = v.getLayoutParams();
            lParams.height = mItemHeight;
            v.setLayoutParams(lParams);
            v.invalidate();
        }
        KSubjectViewHolder vh = new KSubjectViewHolder(view5);
        vh.setItemClickListener(itemOptionClickListener);
		return vh;
	}

    @Override
    public long getHeaderId(int position) {
        return 0;
    }

    @Override
    public ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder, int position) {

    }
}
