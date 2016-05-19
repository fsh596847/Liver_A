package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.DoctorList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.DoctorViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class DoctorListAdapter extends BaseRecyViewAdapter {

    public DoctorListAdapter() {}

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (DoctorViewHolder.class.equals(viewHolder.getClass())) {
                DoctorList.DoctorEntity obj = (DoctorList.DoctorEntity) this.getItem(pos);
                DoctorViewHolder holder = (DoctorViewHolder) viewHolder;
                holder.setViewData(obj);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_doctor, viewGroup, false);
        DoctorViewHolder viewHolder = new DoctorViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }
}
