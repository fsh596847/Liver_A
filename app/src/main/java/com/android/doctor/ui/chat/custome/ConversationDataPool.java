package com.android.doctor.ui.chat.custome;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.yuntongxun.kitsdk.core.CCPAppManager;
import com.yuntongxun.kitsdk.db.ConversationSqlManager;
import com.yuntongxun.kitsdk.db.GroupSqlManager;
import com.yuntongxun.kitsdk.db.OnMessageChange;
import com.yuntongxun.kitsdk.ui.chatting.model.ECConversation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/4/27.
 */
public class ConversationDataPool implements OnMessageChange {

    private static ConversationDataPool instance;

    private List<ECConversation> mConversations = new ArrayList<ECConversation>();

    private List<ECConversation> mPConversations = new ArrayList<ECConversation>();

    private List<ECConversation> mDConversations = new ArrayList<ECConversation>();

    private List<ECConversation> mGConversations = new ArrayList<ECConversation>();

    private List<ECConversation> mNConversations = new ArrayList<ECConversation>();

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

    public ECConversation getItem(Cursor cursor) {
        ECConversation conversation = new ECConversation();
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

        initConversations();

        Log.d("TAG", "[ConversationPool-> notifyChange]");

        if(onDataChangeListener != null) {
            onDataChangeListener.onDataChange();
        }
        LocalBroadcastManager.getInstance(CCPAppManager.getContext()).sendBroadcast(new Intent("DATA_CHANGE"));
    }

    private void initConversations() {
        List<ECConversation> conversations = getInstance().mConversations;
        for (ECConversation e : conversations) {
            String udata = e.getUserdata();
            if (udata == null) {
                continue;
            }
            Object obj = UserDataUtil.getUserData(udata);
            if (obj == null) continue;
            if (obj.getClass().equals(CommonUserData.class)) {
                CommonUserData cud = (CommonUserData) obj;
                if (UserDataUtil.isPatient(cud)) {
                    mPConversations.add(e);
                } else if (UserDataUtil.isGroup(cud)) {
                    mGConversations.add(e);
                } else if (UserDataUtil.isDoctor(cud)) {
                    mDConversations.add(e);
                }
            } else if (UserDataUtil.isNotice(obj)) {
                mNConversations.add(e);
            }
        }
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        this.onDataChangeListener = onDataChangeListener;
    }

    public synchronized List<ECConversation> getConversationByType(int type) {
        switch (type) {
            case 0:
                return mPConversations;
            case 1:
                return mGConversations;
            case 2:
                return mDConversations;
            case 3:
                return mNConversations;
        }
        return null;
    }

    public static int getConversationUnreadCount(int type) {
        int count = 0;

        switch (type) {
            case 0:
                for (ECConversation e : getInstance().mPConversations) {
                    count += e.getUnreadCount();
                }
                break;
            case 1:
                for (ECConversation e : getInstance().mGConversations) {
                    count += e.getUnreadCount();
                }
                break;
            case 2:
                for (ECConversation e : getInstance().mDConversations) {
                    count += e.getUnreadCount();
                }
                break;
            case 3:
                for (ECConversation e : getInstance().mNConversations) {
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
        mPConversations.clear();
        mDConversations.clear();
        mGConversations.clear();
        mNConversations.clear();
    }
}
