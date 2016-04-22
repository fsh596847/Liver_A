package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.PlanRecordList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.TimeLineViewHolder;

/**
 *
 */
public class TimeLineAdapter extends BaseRecyViewAdapter<TimeLineViewHolder> {
    public final static int NORMAL = 0;
    public final static int HEADER = 1;
    public final static int FOOTER = 2;
    public final static int START = 4;
    public final static int END = 8;
    public final static int ATOM = 16;

    private OnListItemClickListener itemClickListener;

    public void setItemClickListener(OnListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TimeLineAdapter() {
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = super.getItemViewType(position);
        if (viewType == VIEW_TYPE_ITEM) {
            final int size = data.size() - 1;
            if (size == 0)
                return ATOM;
            else if (position == 0)
                return START;
            else if (position == size)
                return END;
            else return NORMAL;
        }
        return viewType;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup view, int viewType) {
        if (viewType != VIEW_TYPE_FOOTER) {
            View v = inflater.inflate(R.layout.item_timeline_base, view, false);
            TimeLineViewHolder vh = new TimeLineViewHolder(v, viewType);
            vh.setItemClickListener(this.itemClickListener);
            return vh;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder != null && TimeLineViewHolder.class.equals(holder.getClass())) {
            TimeLineViewHolder vh = (TimeLineViewHolder) holder;
            Object obj = getItem(position);
            if (obj != null && obj.getClass().equals(PlanRecordList.RecordEntity.class)) {
                PlanRecordList.RecordEntity re = (PlanRecordList.RecordEntity) obj;
                vh.setData(re);
            }
        }
    }

}
