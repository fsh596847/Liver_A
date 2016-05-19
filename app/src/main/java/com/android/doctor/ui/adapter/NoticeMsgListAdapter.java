package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.model.NoticeMsgList;
import com.android.doctor.model.PatientList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.DocPatientViewHolder;
import com.android.doctor.ui.viewholder.HosPatientViewHolder;
import com.android.doctor.ui.viewholder.MsgViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class NoticeMsgListAdapter extends BaseRecyViewAdapter {

    private int layout;

    public NoticeMsgListAdapter(int layout) {
        this.layout = layout;
    }

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (MsgViewHolder.class.equals(viewHolder.getClass())) {
                NoticeMsgList.MsgEntity obj = (NoticeMsgList.MsgEntity) this.getItem(pos);
                MsgViewHolder holder = (MsgViewHolder) viewHolder;
                holder.setViewData(obj);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(layout, viewGroup, false);
        MsgViewHolder viewHolder = new MsgViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }
}
