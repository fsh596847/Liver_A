package com.yuntongxun.kitsdk.custom;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.BaseAdapter;

import com.google.gson.Gson;
import com.yuntongxun.kitsdk.core.CCPAppManager;
import com.yuntongxun.kitsdk.db.ConversationSqlManager;
import com.yuntongxun.kitsdk.db.GroupSqlManager;
import com.yuntongxun.kitsdk.db.OnMessageChange;
import com.yuntongxun.kitsdk.ui.chatting.model.ECConversation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yong on 2016/4/27.
 */
public class ConversationDataPool implements OnMessageChange {

    private static ConversationDataPool instance;

    private List<ECConversation> mConversations = new ArrayList<ECConversation>();

    private OnDataChangeListener onDataChangeListener;

    private ConversationDataPool() {
        super();
    }

    public synchronized static ConversationDataPool getInstance() {
        if (instance == null) {
            instance = new ConversationDataPool();
        }
        return instance;
    }

    @Override
    public void onChanged(String sessionId) {
        notifyChange();
    }

    public com.yuntongxun.kitsdk.ui.chatting.model.ECConversation getItem(Cursor cursor) {
        com.yuntongxun.kitsdk.ui.chatting.model.ECConversation conversation = new com.yuntongxun.kitsdk.ui.chatting.model.ECConversation();
        conversation.setCursor(cursor);

        if(conversation.getSessionId().equals("10089")){
            conversation.setUsername("系统通知");
            return conversation;
        }

        if(conversation.getUsername() != null && conversation.getUsername().endsWith("@priategroup.com")) {
            conversation.setUsername(conversation.getSessionId());//??
        } else if(conversation.getUsername() != null && conversation.getUsername().toUpperCase().startsWith("G")){
            if(GroupSqlManager.getECGroup(conversation.getUsername())!=null){
                conversation.setUsername(GroupSqlManager.getECGroup(conversation.getUsername()).getName());
            }else{
                conversation.setUsername("未知");
            }
        } else {
            conversation.setUsername(conversation.getSessionId());
        }
        return conversation;
    }

    public void notifyChange() {
        clearData();
        Cursor cursor = ConversationSqlManager.getConversationCursor();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                ECConversation e = getItem(cursor);
                getInstance().mConversations.add(e);
                Log.d("TAG", "[ConverstaionDataPool-> notifyChange]:" + e.toString());
            } while (cursor.moveToNext());
        }

        if(cursor != null) {
            cursor.close();
        }
        Log.d("TAG", "[ConversationPool-> notifyChange]");

        if(onDataChangeListener != null) {
            onDataChangeListener.onDataChange();
        }
        LocalBroadcastManager.getInstance(CCPAppManager.getContext()).sendBroadcast(new Intent("DATA_CHANGE"));
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }

    public synchronized List<ECConversation> getConversationByType(int type) {
        List<ECConversation> rdata = new ArrayList<ECConversation>();
        List<ECConversation> conversations = getInstance().mConversations;
        for (ECConversation e : conversations) {
            String udata = e.getUserdata();
            if (udata == null) {
                continue;
            }
            Object obj = MsgUserDataUtil.getUserData(udata);
            if (obj == null) continue;
            if (obj.getClass().equals(CommonUserData.class)) {
                CommonUserData cud = (CommonUserData) obj;
                switch (type) {
                    case 0:
                        if (MsgUserDataUtil.isPatient(cud)) {
                            rdata.add(e);
                        }
                        break;
                    case 1:
                        if (MsgUserDataUtil.isGroup(cud)) {
                            rdata.add(e);
                        }
                        break;
                    case 2:
                        if (MsgUserDataUtil.isDoctor(cud)) {
                            rdata.add(e);
                        }
                        break;
                }
            } else if (MsgUserDataUtil.isNotice(obj) && type == 3) {
                rdata.add(e);
            }
        }
        Log.d("TAG", "[ConversationPool-> getConversationByType],type " + type + ", " + new Gson().toJson(rdata));
        return rdata;
    }

    public static int getConversationUnreadCount(int type) {
        int count = 0;
        List<ECConversation> conversations = getInstance().mConversations;
        for (ECConversation e : conversations) {
            String udata = e.getUserdata();
            if (udata == null) {
                continue;
            }
            Object obj = MsgUserDataUtil.getUserData(udata);
            if (obj == null) continue;
            if (obj.getClass().equals(CommonUserData.class)) {
                CommonUserData cud = (CommonUserData) obj;
                switch (type) {
                    case 0:
                        if (MsgUserDataUtil.isPatient(cud)) {
                            count += e.getUnreadCount();
                        }
                        break;
                    case 1:
                        if (MsgUserDataUtil.isGroup(cud)) {
                            count += e.getUnreadCount();
                        }
                        break;
                    case 2:
                        if (MsgUserDataUtil.isDoctor(cud)) {
                            count += e.getUnreadCount();
                        }
                        break;
                }
            } else if (MsgUserDataUtil.isNotice(obj) && type == 3) {
                count += e.getUnreadCount();
            }
        }
        Log.d("TAG", "[ConversationPool-> getConversationUnreadCount] type, unread " + type + ", " + count);
        return count;
    }

    public interface OnDataChangeListener {
        void onDataChange();
    }

    public void clearData() {
        mConversations.clear();
    }
}
