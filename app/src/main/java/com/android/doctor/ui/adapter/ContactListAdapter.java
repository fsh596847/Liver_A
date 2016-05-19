package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.Constants;
import com.android.doctor.model.ContactGroupList;
import com.android.doctor.model.ContactList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.ContactViewHolder;
import com.yuntongxun.kitsdk.ui.group.model.ECContacts;

/**
 * Created by Yong on 2016-02-14.
 */
public class ContactListAdapter extends BaseRecyViewAdapter {
    private int type;
    public ContactListAdapter(int type) {
        this.type = type;
    }

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (ContactViewHolder.class.equals(viewHolder.getClass())) {
                ContactViewHolder holder = (ContactViewHolder) viewHolder;
                Object object = this.getItem(pos);
                if (object == null) return;
                if (object.getClass().equals(ContactList.ContactEntity.class)) {
                    ContactList.ContactEntity obj = (ContactList.ContactEntity) this.getItem(pos);
                    holder.setViewData(obj);
                } else if (object.getClass().equals(ContactGroupList.GroupsEntity.class)) {
                    ContactGroupList.GroupsEntity obj = (ContactGroupList.GroupsEntity) this.getItem(pos);
                    holder.setViewData(obj);
                }
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_contact, viewGroup, false);
        int imgRes = type == Constants.CONTACT_TYPE_DOCTOR ? R.drawable.doctor_photo : type == Constants.CONTACT_TYPE_PATIENT ? R.drawable.patient_photo_m : R.drawable.group_photo;
        ((ImageView)view.findViewById(R.id.avatar_view)).setImageResource(imgRes);
        ContactViewHolder viewHolder = new ContactViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }
}
