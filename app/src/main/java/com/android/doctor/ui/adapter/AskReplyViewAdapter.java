package com.android.doctor.ui.adapter;

/**
 * Created by Yong on 2016/5/19.
 */

import android.R.integer;
import android.content.Context;
import android.database.DataSetObserver;

import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.helper.ParseEmojiUtil;
import com.android.doctor.model.AskList;
import com.android.doctor.model.AskReplyList;
import com.android.doctor.model.ChatMsgEntity;
import com.android.doctor.ui.base.BaseListAdapter;
import com.yuntongxun.kitsdk.ui.chatting.view.CCPTextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AskReplyViewAdapter extends BaseListAdapter {

    public static interface ItemViewType
    {
        int ITEM_ASK_INFO = 0;
        //对方发来的信息
        int ITEM_COM_MSG = 1;
        //自己发出的信息
        int ITEM_TO_MSG = 2;
    }

    //获取项的ID
    public long getItemId(int position) {
        return position;
    }

    //获取项的类型
    public int getItemViewType(int position) {
        Object obj = getItem(position);
        if (obj != null) {
            if (obj.getClass().equals(AskList.AsksEntity.class)) {
                return ItemViewType.ITEM_ASK_INFO;
            } else if (obj.getClass().equals(AskReplyList.RepliesEntity.class)) {
                AskReplyList.RepliesEntity reply = (AskReplyList.RepliesEntity) obj;
                if (reply.getReplyrole() == 1) {
                    return ItemViewType.ITEM_COM_MSG;
                } else {
                    return ItemViewType.ITEM_TO_MSG;
                }
            }
        }
        return 0;
    }

    //获取项的类型数
    public int getViewTypeCount() {
        return 3;
    }

    //获取View
    @Override
    public View getRealView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == ItemViewType.ITEM_ASK_INFO) {
            return getBaseView(convertView, position);
        } else {
            return getMsgView(viewType, convertView, position);
        }
    }

    @Override
    protected boolean hasFooterView() {
        return false;
    }

    private View getBaseView(View view, int position) {
        BaseViewHolder viewHolder = null;
        if (view == null) {
            view = mInflater.inflate(R.layout.item_ask_info, null);
            viewHolder = new BaseViewHolder();
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tvContent = (TextView) view.findViewById(R.id.tv_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (BaseViewHolder) view.getTag();
        }
        if (viewHolder == null) return view;
        AskList.AsksEntity e = (AskList.AsksEntity) getItem(position);
        String text1 = e.getNickname() + ":" + e.getAskclass() + ":" + e.getTitle();
        String text2 = "病情描述:" + e.getContent();
        viewHolder.tvTitle.setText(text1);
        viewHolder.tvContent.setText(text2);
        return view;
    }

    private View getMsgView(int viewType, View view, int position) {
        MsgViewHolder msgViewHolder = null;
        if (view == null) {
            if (viewType == ItemViewType.ITEM_COM_MSG){
                //如果是对方发来的消息，则显示的是左气泡
                view = mInflater.inflate(R.layout.chat_item_msg_left, null);
            } else {
                //如果是自己发出的消息，则显示的是右气泡
                view = mInflater.inflate(R.layout.chat_item_msg_right, null);
            }

            msgViewHolder = new MsgViewHolder();
            msgViewHolder.tvSendTime = (TextView) view.findViewById(R.id.tv_sendtime);
            msgViewHolder.tvUserName = (TextView) view.findViewById(R.id.tv_username);
            msgViewHolder.tvContent = (CCPTextView) view.findViewById(R.id.tv_chatcontent);
            view.setTag(msgViewHolder);
        } else {
            msgViewHolder = (MsgViewHolder) view.getTag();
        }
        if (msgViewHolder == null) return view;
        AskReplyList.RepliesEntity e = (AskReplyList.RepliesEntity) getItem(position);
        Date date = DateUtils.toDate(e.getReplytime());
        msgViewHolder.tvSendTime.setText(String.format("%tm", date) + "-" + String.format("%td", date));
        msgViewHolder.tvUserName.setText(e.getReplynickname());
        msgViewHolder.tvContent.setText(e.getContent());

        return view;
    }

    static class MsgViewHolder {
        public TextView tvSendTime;
        public TextView tvUserName;
        public CCPTextView tvContent;
        public boolean isComMsg = true;
    }

    static class BaseViewHolder {
        public TextView tvTitle;
        public TextView tvContent;
    }
}
