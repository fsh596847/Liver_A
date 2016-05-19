package com.android.doctor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.TempPlanList;
import com.android.doctor.ui.adapter.sticky_adapter.StickyRecyclerHeadersAdapter;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.TPlanViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class TPlanListAdapter extends BaseRecyViewAdapter implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private Context context;
    public TPlanListAdapter(Context c) {
        this.context = c;
    }

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (TPlanViewHolder.class.equals(viewHolder.getClass())) {
                TempPlanList.TplsEntity obj = (TempPlanList.TplsEntity) this.getItem(pos);
                TPlanViewHolder holder = (TPlanViewHolder) viewHolder;
                holder.setViewData(obj);
            }
        }
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_tvisit_plan, viewGroup, false);
        TPlanViewHolder viewHolder = new TPlanViewHolder(view, context);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }


    @Override
    public long getHeaderId(int position) {
        Object obj = getItem(position);
        if (obj != null) {
            TempPlanList.TplsEntity tpls = (TempPlanList.TplsEntity) obj;
            return tpls.getIssys();
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header, parent, false);
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TempPlanList.TplsEntity item = (TempPlanList.TplsEntity) getItem(position);
        if (item == null) return;
        TextView textView = (TextView) holder.itemView;
        textView.setText(item.getIssys() == 1 ? "系统方案" : "我的方案");
    }
}
