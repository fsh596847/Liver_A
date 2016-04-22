package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.PlanRecordList;
import com.android.doctor.ui.adapter.TimeLineAdapter;
import com.android.doctor.ui.widget.TimeLineMarker;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by qiujuer
 * on 15/8/23.
 */
public class TimeLineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @InjectView(R.id.tv_1)
    protected TextView mTvTitle;
    @InjectView(R.id.tv_2)
    protected TextView mTvDesc;
    @InjectView(R.id.tv_3)
    protected TextView mTvState;
    @InjectView(R.id.tv_4)
    protected TextView mTvContent;
    private OnListItemClickListener itemClickListener;

    public TimeLineViewHolder(View itemView, int type) {
        super(itemView);
        ButterKnife.inject(this, itemView);

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

    public void setData(PlanRecordList.RecordEntity data) {
       if (data == null) return;
        mTvTitle.setText(DateUtils.getDateString_(data.getCreatetime()));
        StringBuffer buffer = new StringBuffer();
        if (!TextUtils.isEmpty(data.getContnent())) {
            buffer.append(data.getContnent());
            buffer.append("\n");
        }
        List<String> changes = data.getChangecontnent();
        if (changes != null) {
            for (String str : changes) {
                buffer.append(str);
                buffer.append("\n");
            }
        }
        mTvDesc.setText(buffer.toString());
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
