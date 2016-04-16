package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.ui.adapter.TimeLineAdapter;
import com.android.doctor.ui.widget.TimeLineMarker;


/**
 * Created by qiujuer
 * on 15/8/23.
 */
public class TimeLineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView mName;
    private OnListItemClickListener itemClickListener;

    public TimeLineViewHolder(View itemView, int type) {
        super(itemView);

        TimeLineMarker mMarker = (TimeLineMarker) itemView.findViewById(R.id.item_time_line_mark);
        if (type == TimeLineAdapter.ATOM) {
            mMarker.setBeginLine(null);
            mMarker.setEndLine(null);
        } else if (type == TimeLineAdapter.START) {
            mMarker.setBeginLine(null);
        } else if (type == TimeLineAdapter.END) {
            mMarker.setEndLine(null);
        }
        itemView.setOnClickListener(this);
    }

    public void setData(String data) {
       //mName.setText("Name:");
    }

    public void setItemClickListener(OnListItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.onItemClick(getAdapterPosition(), v);
        }
    }
}
