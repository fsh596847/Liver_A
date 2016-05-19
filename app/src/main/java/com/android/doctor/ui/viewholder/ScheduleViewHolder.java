package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.RemindResultList;

/**
 * Created by Yong on 2016-02-14.
 */
public class ScheduleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView date_tv;
    /**内容**/
    TextView tv_content;

    private OnListItemClickListener itemClickListener;

    public ScheduleViewHolder(View view){
        super(view);
        initView(view);
    }

    public void initView(View view) {
        date_tv = (TextView) view.findViewById(R.id.tv_label);
        tv_content = (TextView) view.findViewById(R.id.tv_text);
        view.findViewById(R.id.ll_remind_content).setOnClickListener(this);
    }

    public void setData(boolean showLabel, RemindResultList.RemindResultEntity obj) {
        date_tv.setText(obj.getTimestr());
        date_tv.setVisibility(showLabel ? View.VISIBLE : View.GONE);
        String content = obj.getPname() + "    " + obj.getContent();
        tv_content.setText(content);
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
