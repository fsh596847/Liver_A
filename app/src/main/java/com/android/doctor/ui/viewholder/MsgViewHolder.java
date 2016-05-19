package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.NoticeMsgList;

/**
 * Created by Yong on 2016-02-14.
 */
public class MsgViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    ImageView imgTip;
    TextView content;
    TextView tvOper;
    TextView date_tv;

    private OnListItemClickListener itemClickListener;

    public MsgViewHolder(View view){
        super(view);
        initView(view);
        view.setOnClickListener(this);
    }

    public void initView(View view) {
        imgTip = (ImageView) view.findViewById(R.id.img_tip);
        content = (TextView) view.findViewById(R.id.tv_content);
        date_tv = (TextView) view.findViewById(R.id.tv_date);
        tvOper = (TextView) view.findViewById(R.id.tv_oper);
        tvOper.setOnClickListener(this);
    }

    /***
     * status{0=需要操作,1=已同意,2=已拒绝,-1=已读}
     * @param o
     */
    public void setViewData(NoticeMsgList.MsgEntity o) {
        if (o == null) return;
        content.setText(o.getMsgcontent());
        String date = DateUtils.friendly_time(o.getLastmsgtime());
        String tp = o.getMsgtype();
        if ("700".equals(tp)) {
            String stat = o.getStatus();
            if ("0".equals(stat)) {
                imgTip.setVisibility(View.VISIBLE);
                tvOper.setVisibility(View.VISIBLE);
                date_tv.setText(date);
            } else {
                String ort = "1".equals(stat) ? "已同意" : "2".equals(stat) ? "已拒绝" : "已读";
                String state = date + "  " + ort;
                date_tv.setText(state);
            }
        } else {
            date_tv.setText(date);
        }
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
