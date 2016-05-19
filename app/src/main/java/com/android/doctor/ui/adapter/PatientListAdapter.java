package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.model.PatientList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.DocPatientViewHolder;
import com.android.doctor.ui.viewholder.HosPatientViewHolder;
import com.android.doctor.ui.viewholder.PatientViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class PatientListAdapter extends BaseRecyViewAdapter {

    private int layout;

    public PatientListAdapter(int layout) {
        this.layout = layout;
    }

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM && viewHolder != null) {
            if (DocPatientViewHolder.class.equals(viewHolder.getClass())) {
                PatientList.DataEntity obj = (PatientList.DataEntity) this.getItem(pos);
                DocPatientViewHolder holder = (DocPatientViewHolder) viewHolder;
                holder.setViewData(obj);
            } else if (HosPatientViewHolder.class.equals(viewHolder.getClass())) {
                HosPaitentList.HosPatientEntity obj = (HosPaitentList.HosPatientEntity) this.getItem(pos);
                HosPatientViewHolder holder = (HosPatientViewHolder) viewHolder;
                holder.setViewData(obj);
            } else if (PatientViewHolder.class.equals(viewHolder.getClass())) {
                HosPaitentList.HosPatientEntity obj = (HosPaitentList.HosPatientEntity) this.getItem(pos);
                PatientViewHolder holder = (PatientViewHolder) viewHolder;
                holder.setViewData(obj);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(layout, viewGroup, false);
        if (layout == R.layout.item_patient) {
            DocPatientViewHolder viewHolder = new DocPatientViewHolder(view);
            viewHolder.setItemClickListener(itemOptionClickListener);
            return viewHolder;
        } else if (layout == R.layout.item_patient_invite) {
            HosPatientViewHolder viewHolder = new HosPatientViewHolder(view);
            viewHolder.setItemClickListener(itemOptionClickListener);
            return viewHolder;
        } else if (layout == R.layout.item_diag_record) {
            PatientViewHolder viewHolder = new PatientViewHolder(view);
            viewHolder.setItemClickListener(itemOptionClickListener);
            return viewHolder;
        }
        return null;
    }
}
