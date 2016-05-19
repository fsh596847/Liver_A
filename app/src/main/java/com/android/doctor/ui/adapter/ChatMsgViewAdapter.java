package com.android.doctor.ui.adapter;

/**
 * Created by Yong on 2016/5/19.
 */

import android.R.integer;
import android.content.Context;
import android.database.DataSetObserver;

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
import com.android.doctor.model.ChatMsgEntity;

import java.util.ArrayList;
import java.util.List;

public class ChatMsgViewAdapter extends BaseAdapter {

    //ListView视图的内容由IMsgViewType决定
    public static interface IMsgViewType
    {
        //对方发来的信息
        int IMVT_COM_MSG = 0;
        //自己发出的信息
        int IMVT_TO_MSG = 1;
    }

    private List<ChatMsgEntity> data;
    private Context context;
    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

    //获取ListView的项个数
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    //获取项
    public Object getItem(int position) {
        if (data == null) return null;
        if (position < 0 || data.size() <= position) return null;
        return data.get(position);
    }

    //获取项的ID
    public long getItemId(int position) {
        return position;
    }

    //获取项的类型
    public int getItemViewType(int position) {
        ChatMsgEntity entity = data.get(position);
        if (entity.getMsgType()) {
            return IMsgViewType.IMVT_COM_MSG;
        }else{
            return IMsgViewType.IMVT_TO_MSG;
        }

    }

    //获取项的类型数
    public int getViewTypeCount() {
        return 2;
    }

    //获取View
    public View getView(int position, View convertView, ViewGroup parent) {

        ChatMsgEntity entity = data.get(position);
        boolean isComMsg = entity.getMsgType();

        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            if (isComMsg)
            {
                //如果是对方发来的消息，则显示的是左气泡
                convertView = mInflater.inflate(R.layout.chat_item_msg_left, null);
            }else{
                //如果是自己发出的消息，则显示的是右气泡
                convertView = mInflater.inflate(R.layout.chat_item_msg_right, null);
            }

            viewHolder = new ViewHolder();
            viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
            viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chat_content);
            viewHolder.isComMsg = isComMsg;

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvSendTime.setText(entity.getDate());
        viewHolder.tvUserName.setText(entity.getName());
        viewHolder.tvContent.setText(entity.getText());

        return convertView;
    }

    //通过ViewHolder显示项的内容
    static class ViewHolder {
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }

}
