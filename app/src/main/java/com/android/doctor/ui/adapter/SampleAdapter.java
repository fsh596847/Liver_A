package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.ui.base.BaseRecyViewAdapter;

/**
 * Created by Yong on 2016-02-14.
 */
public class SampleAdapter extends BaseRecyViewAdapter {
    public static final int VIEW_TYPE_BASE = 0;
    public static final int VIEW_TYPE_PRODUCT = 1;

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    public int getItemViewType(int position) {
        int viewType = super.getItemViewType(position);
        if (viewType == VIEW_TYPE_ITEM) {
            Object item = data.get(position);
            if (position == 0) {
                viewType = VIEW_TYPE_BASE;
            } else {
                viewType = VIEW_TYPE_PRODUCT;
            }
        }
        return viewType;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_BASE) {

        } else if (viewType == VIEW_TYPE_PRODUCT) {
            
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;

        return null;
    }

}
