package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.GroupList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.GroupViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class GroupListAdapter extends BaseRecyViewAdapter {

    /**群组数据适配器**/

    private int mLayout;

    public GroupListAdapter(int layout) {
        this.mLayout = layout;
    }

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (GroupViewHolder.class.equals(viewHolder.getClass())) {
                GroupList.GroupsEntity obj = (GroupList.GroupsEntity) this.getItem(pos);
                GroupViewHolder holder = (GroupViewHolder) viewHolder;
                holder.setViewData(obj);
            }
        }
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(mLayout, viewGroup, false);
        GroupViewHolder viewHolder = new GroupViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }
}
