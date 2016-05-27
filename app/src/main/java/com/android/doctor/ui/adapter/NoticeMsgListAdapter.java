package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.AskMangeMsgList;
import com.android.doctor.model.ContactAssistantMsgList;
import com.android.doctor.model.GroupNoticeMsgList;
import com.android.doctor.model.PatientAskMsgList;
import com.android.doctor.model.PatientReportMsgList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.MsgViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class NoticeMsgListAdapter extends BaseRecyViewAdapter {

    private int layout;

    public NoticeMsgListAdapter(int layout) {
        this.layout = layout;
    }

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (MsgViewHolder.class.equals(viewHolder.getClass())) {
                MsgViewHolder holder = (MsgViewHolder) viewHolder;
                Object obj = this.getItem(pos);
                if(ContactAssistantMsgList.ContactAssistMsgEntity.class.equals(obj.getClass())) {
                    ContactAssistantMsgList.ContactAssistMsgEntity e = (ContactAssistantMsgList.ContactAssistMsgEntity) obj;
                    holder.setViewData(e);
                } else if (PatientAskMsgList.PAskMsgEntity.class.equals(obj.getClass())) {
                    PatientAskMsgList.PAskMsgEntity e = (PatientAskMsgList.PAskMsgEntity) obj;
                    holder.setViewData(e);
                } else if (AskMangeMsgList.AskManageMsgEntity.class.equals(obj.getClass())) {
                    AskMangeMsgList.AskManageMsgEntity e = (AskMangeMsgList.AskManageMsgEntity) obj;
                    holder.setViewData(e);
                } else if (GroupNoticeMsgList.MsgEntity.class.equals(obj.getClass())) {
                    GroupNoticeMsgList.MsgEntity e = (GroupNoticeMsgList.MsgEntity)obj;
                    holder.setViewData(e);
                } else if (PatientReportMsgList.ReportMsgEntity.class.equals(obj.getClass())) {
                    PatientReportMsgList.ReportMsgEntity e = (PatientReportMsgList.ReportMsgEntity) obj;
                    holder.setViewData(e);
                }
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(layout, viewGroup, false);
        MsgViewHolder viewHolder = new MsgViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }
}
