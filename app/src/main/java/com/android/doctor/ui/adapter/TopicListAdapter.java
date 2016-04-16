package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.TopicViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class TopicListAdapter extends BaseRecyViewAdapter {

    public TopicListAdapter() {}

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (TopicViewHolder.class.equals(viewHolder.getClass())) {
                Object obj = (Object) this.getItem(pos);
                TopicViewHolder holder = (TopicViewHolder) viewHolder;
                holder.setData(obj);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_topic, viewGroup, false);
        TopicViewHolder viewHolder = new TopicViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }
}
