package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.ContactList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.ContactViewHolder;
import com.yuntongxun.kitsdk.ui.group.model.ECContacts;

/**
 * Created by Yong on 2016-02-14.
 */
public class ContactListAdapter extends BaseRecyViewAdapter {

    public ContactListAdapter() {}

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (ContactViewHolder.class.equals(viewHolder.getClass())) {
                ContactList.ContactEntity obj = (ContactList.ContactEntity) this.getItem(pos);
                ContactViewHolder holder = (ContactViewHolder) viewHolder;
                holder.setViewData(obj);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_contact, viewGroup, false);
        ContactViewHolder viewHolder = new ContactViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }
}
