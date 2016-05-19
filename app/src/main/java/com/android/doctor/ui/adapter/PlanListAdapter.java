package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.PlanList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.VisitPlanViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class PlanListAdapter extends BaseRecyViewAdapter {

    public PlanListAdapter() {}

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (VisitPlanViewHolder.class.equals(viewHolder.getClass())) {
                PlanList.PlanBaseEntity obj = (PlanList.PlanBaseEntity) this.getItem(pos);
                VisitPlanViewHolder holder = (VisitPlanViewHolder) viewHolder;
                holder.setViewData(obj);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_visit_plan, viewGroup, false);
        VisitPlanViewHolder viewHolder = new VisitPlanViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }
}
