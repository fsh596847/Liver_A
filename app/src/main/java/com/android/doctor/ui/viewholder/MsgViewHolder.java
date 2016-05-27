package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.AskMangeMsgList;
import com.android.doctor.model.Constants;
import com.android.doctor.model.ContactAssistantMsgList;
import com.android.doctor.model.GroupNoticeMsgList;
import com.android.doctor.model.PatientAskMsgList;
import com.android.doctor.model.PatientReportMsgList;

import java.util.Date;

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
     * status
     *  0:   未操作 需要显示  操作按钮
     *  1:   已同意
     *  2:   已拒绝
     * -1:  只读消息  表示未读
     *  5:  只读消息  表示已读
     * @param o
     */
    public void setViewData(GroupNoticeMsgList.MsgEntity o) {
        if (o == null) return;
        setViewData(o.getMsgcontent(), o.getLastmsgtime(), o.getStatus());
    }

    private void setViewData(String strContent, String lastTime, String stat) {
        content.setText(strContent);
        Date date = DateUtils.toDate(lastTime);
        String strDate = String.format("%tm", date) + "-" + String.format("%td", date);

        if ("0".equals(stat)) {
            tvOper.setVisibility(View.VISIBLE);
            date_tv.setText(strDate);
        } else {
            String ort = "1".equals(stat) ? "已同意" : "2".equals(stat) ? "已拒绝" : "";
            String state = strDate + "  " + ort;
            date_tv.setText(state);
        }

        if ("-1".equals(stat) || "0".equals(stat)) {
            imgTip.setVisibility(View.VISIBLE);
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

    public void setViewData(ContactAssistantMsgList.ContactAssistMsgEntity e) {
        if (e == null) return;
        setViewData(e.getMsgcontent(), e.getLastmsgtime(),  e.getStatus());
    }

    public void setViewData(PatientAskMsgList.PAskMsgEntity e) {
        if (e == null) return;
        setViewData(e.getMsgcontent(), e.getLastmsgtime(), e.getStatus());
    }

    public void setViewData(AskMangeMsgList.AskManageMsgEntity e) {
        if (e == null) return;
        setViewData(e.getMsgcontent(), e.getLastmsgtime(), e.getStatus());
    }

    public void setViewData(PatientReportMsgList.ReportMsgEntity e) {
        if (e == null) return;
        setViewData(e.getMsgcontent(), e.getLastmsgtime(), e.getStatus());
    }
}
