package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.DoctorList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.DoctorViewHolder;
import com.android.doctor.ui.viewholder.VisitPatientViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class VisitPatientListAdapter extends BaseRecyViewAdapter {

    public VisitPatientListAdapter() {}

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (VisitPatientViewHolder.class.equals(viewHolder.getClass())) {
                DoctorList.DoctorEntity obj = (DoctorList.DoctorEntity) this.getItem(pos);
                VisitPatientViewHolder holder = (VisitPatientViewHolder) viewHolder;
                holder.setViewData(obj);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_doctor, viewGroup, false);
        VisitPatientViewHolder viewHolder = new VisitPatientViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }
}
