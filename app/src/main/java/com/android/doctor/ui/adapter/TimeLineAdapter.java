package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.ui.viewholder.TimeLineViewHolder;

import java.util.List;

/**
 *
 */
public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {
    public final static int NORMAL = 0;
    public final static int HEADER = 1;
    public final static int FOOTER = 2;
    public final static int START = 4;
    public final static int END = 8;
    public final static int ATOM = 16;

    private List<String> mDataSet;
    private OnListItemClickListener itemClickListener;

    public void setItemClickListener(OnListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TimeLineAdapter(List<String> models) {
        mDataSet = models;
    }

    @Override
    public int getItemViewType(int position) {
        final int size = mDataSet.size() - 1;
        if (size == 0)
            return ATOM;
        else if (position == 0)
            return START;
        else if (position == size)
            return END;
        else return NORMAL;
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_timeline, viewGroup, false);
        TimeLineViewHolder vh = new TimeLineViewHolder(v, viewType);
        vh.setItemClickListener(this.itemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder timeLineViewHolder, int i) {
        timeLineViewHolder.setData("");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
