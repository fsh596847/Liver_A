package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.interf.OnListItemSlideListener;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.chat.GroupMemberActivity;
import com.android.doctor.ui.viewholder.GroupMemberViewHolder;
import com.yuntongxun.kitsdk.ui.group.model.ECContacts;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * Created by Yong on 2016-02-14.
 */
public class GroupMemberListAdapter extends BaseRecyViewAdapter {

    /**群组成员数据适配器**/

    private Vector resp = new Vector();
    private GroupMemberActivity aty;

    @Override
    public void setData(List data) {
        resp.clear();
        super.setData(data);
    }

    public GroupMemberListAdapter(GroupMemberActivity activity) {
        this.aty = activity;
    }

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (GroupMemberViewHolder.class.equals(viewHolder.getClass())) {
                ECContacts obj = (ECContacts) this.getItem(pos);
                GroupMemberViewHolder holder = (GroupMemberViewHolder) viewHolder;
                holder.fillUI(obj);
            }
        }
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.group_member_item, viewGroup, false);
        GroupMemberViewHolder viewHolder = new GroupMemberViewHolder(view, aty);
        resp.addElement(viewHolder);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }

    public void notifySlideEvent(int state) {
        Enumeration e = resp.elements();
        while (e.hasMoreElements()) {
            ((OnListItemSlideListener)e.nextElement()).onItemSlide(state);
        }
    }

}
