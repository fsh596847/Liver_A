package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.RemindResultList;
import com.android.doctor.ui.adapter.sticky_adapter.StickyRecyclerHeadersAdapter;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.ScheduleViewHolder;

import java.util.Date;

/**
 * Created by Yong on 2016-02-14.
 */
public class ScheduleListAdapter extends BaseRecyViewAdapter implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    public ScheduleListAdapter() {}

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public long getHeaderId(int position) {
        Object obj = getItem(position);
        RemindResultList.RemindResultEntity pre = (RemindResultList.RemindResultEntity) getItem(position - 1);
        if (obj != null && pre != null) {
            RemindResultList.RemindResultEntity remind= (RemindResultList.RemindResultEntity) obj;
            if (remind.getTimestr().equals(pre.getTimestr())) {
                return -1;
            }
        }
        return position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header, parent, false);
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

        TextView textView = (TextView) holder.itemView;
        RemindResultList.RemindResultEntity remind= (RemindResultList.RemindResultEntity) getItem(position);
        if (remind != null) {
            Date date = DateUtils.toDate(remind.getTimestr(), DateUtils.dateFormater3.get());
            String strDate = String.format("%tm", date) + "月" + String.format("%td", date)  + "日";
            textView.setText(strDate);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (ScheduleViewHolder.class.equals(viewHolder.getClass())) {
                RemindResultList.RemindResultEntity cur = (RemindResultList.RemindResultEntity) this.getItem(pos);
                ScheduleViewHolder holder = (ScheduleViewHolder) viewHolder;
                /*if (pos != 0) {
                    RemindResultList.RemindResultEntity pre = (RemindResultList.RemindResultEntity) getItem(pos -1);
                    if (pre.getTimestr().equals(cur.getTimestr())) {
                        holder.setData(false, cur);
                        return;
                    }
                }*/
                holder.setData(false, cur);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_remind_result, viewGroup, false);
        ScheduleViewHolder viewHolder = new ScheduleViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }
}
