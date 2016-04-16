package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.ui.base.BaseRecyViewAdapter;

/**
 * Created by Yong on 2016-02-14.
 */
public class RestaurantContentAdapter extends BaseRecyViewAdapter {

    private int mCellLayout;
    public RestaurantContentAdapter(int layout) {
        this.mCellLayout = layout;
    }

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();

    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(mCellLayout, viewGroup, false);
        return null;
    }
}
