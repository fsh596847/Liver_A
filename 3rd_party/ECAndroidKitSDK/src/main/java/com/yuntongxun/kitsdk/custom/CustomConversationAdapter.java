package com.yuntongxun.kitsdk.custom;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.kitsdk.beans.CommonUserData;
import com.yuntongxun.kitsdk.beans.NoticeUserData;
import com.yuntongxun.kitsdk.db.ConversationSqlManager;
import com.yuntongxun.kitsdk.db.GroupNoticeSqlManager;
import com.yuntongxun.kitsdk.db.GroupSqlManager;
import com.yuntongxun.kitsdk.db.OnMessageChange;
import com.yuntongxun.kitsdk.group.GroupNoticeHelper;
import com.yuntongxun.kitsdk.ui.chatting.model.ECConversation;
import com.yuntongxun.kitsdk.ui.chatting.view.CCPTextView;
import com.yuntongxun.kitsdk.utils.DateUtil;
import com.yuntongxun.kitsdk.utils.DemoUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/3/24.
 */
public class CustomConversationAdapter extends BaseAdapter implements OnMessageChange {
    /**患者**/
    private static final int CONV_TYPE_PATIENT = 0;
    /**群组**/
    private static final int CONV_TYPE_GROUP = 1;
    /**医生**/
    private static final int CONV_TYPE_DOCTOR = 2;
    /**通知**/
    private static final int CONV_TYPE_NOTICE = 3;
    /**数据Cursor*/
    private Cursor mCursor;
    /**数据缓存*/
    private List<ECConversation> mDataDoctor = new ArrayList<ECConversation>();
    private List<ECConversation> mDataPatient = new ArrayList<ECConversation>();
    private List<ECConversation> mDataGroup = new ArrayList<ECConversation>();
    private List<ECConversation> mDataNotice = new ArrayList<ECConversation>();
    /**适配器使用数据类型*/
    protected ECConversation t;
    /**上下文对象*/
    protected Context mContext;
    /**数据总数*/
    protected int mCount;
    /**会话类型*/
    private int mConvType;
    /**
     * 构造方法
     * @param ctx
     * @param type
     */
    public CustomConversationAdapter(Context ctx, int type) {
        mContext = ctx;
        this.mCount = -1;
        this.mConvType = type;
        notifyChange();
    }


    @Override
    public int getCount() {
        if(mCount < 0) {
            switch (mConvType) {
                case CONV_TYPE_PATIENT:
                    return mDataPatient == null ? 0 : mDataPatient.size();
                case CONV_TYPE_GROUP:
                    return mDataGroup == null ? 0 : mDataGroup.size();
                case CONV_TYPE_DOCTOR:
                    return mDataDoctor == null ? 0 : mDataDoctor.size();
                case CONV_TYPE_NOTICE:
                    return mDataNotice == null ? 0 : mDataNotice.size();
            }
        }
        return mCount;
    }

    @Override
    public ECConversation getItem(int position) {
        switch (mConvType) {
            case CONV_TYPE_PATIENT:
                if(position < 0 || mDataPatient.size() <= position) {
                    return null;
                }
                ECConversation _t1 = mDataPatient.get(Integer.valueOf(position));
                return _t1;
            case CONV_TYPE_GROUP:
                if(position < 0 || mDataGroup.size() <= position) {
                    return null;
                }
                ECConversation _t2 = mDataGroup.get(Integer.valueOf(position));
                return _t2;
            case CONV_TYPE_DOCTOR:
                if(position < 0 || mDataDoctor.size() <= position) {
                    return null;
                }
                ECConversation _t3 = mDataDoctor.get(Integer.valueOf(position));
                return _t3;
            case CONV_TYPE_NOTICE:
                if(position < 0 || mDataNotice.size() <= position) {
                    return null;
                }
                ECConversation _t4 = mDataNotice.get(Integer.valueOf(position));
                return _t4;
        }
        return null;
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

    /**
     * 会话时间
     * @param conversation
     * @return
     */
    protected final CharSequence getConversationTime(com.yuntongxun.kitsdk.ui.chatting.model.ECConversation conversation) {
        if(conversation.getSendStatus() == ECMessage.MessageStatus.SENDING.ordinal()) {
            return mContext.getString(com.yuntongxun.eckitsdk.R.string.conv_msg_sending);
        }
        if(conversation.getDateTime() <= 0) {
            return "";
        }
        return DateUtil.getDateString(conversation.getDateTime(),
                DateUtil.SHOW_TYPE_CALL_LOG).trim();
    }

    /**
     * 根据消息类型返回相应的主题描述
     * @param conversation
     * @return
     */
    protected final CharSequence getConversationSnippet(com.yuntongxun.kitsdk.ui.chatting.model.ECConversation conversation) {
        if(conversation == null) {
            return "";
        }
        if(GroupNoticeSqlManager.CONTACT_ID.equals(conversation.getSessionId())) {
            return GroupNoticeHelper.getNoticeContent(conversation.getContent());
        }
        if(conversation.getMsgType() == ECMessage.Type.VOICE.ordinal()) {
            return mContext.getString(com.yuntongxun.eckitsdk.R.string.app_voice);
        } else if(conversation.getMsgType() == ECMessage.Type.FILE.ordinal()) {
            return mContext.getString(com.yuntongxun.eckitsdk.R.string.app_file);
        } else if(conversation.getMsgType() == ECMessage.Type.IMAGE.ordinal()) {
            return mContext.getString(com.yuntongxun.eckitsdk.R.string.app_pic);
        } else if(conversation.getMsgType() == ECMessage.Type.VIDEO.ordinal()) {
            return mContext.getString(com.yuntongxun.eckitsdk.R.string.app_video);
        }
        return conversation.getContent();
    }

    /**
     * 根据消息发送状态处理
     * @param context
     * @param conversation
     * @return
     */
    public static Drawable getChattingSnippentCompoundDrawables(Context context , com.yuntongxun.kitsdk.ui.chatting.model.ECConversation conversation) {
        if(conversation.getSendStatus() == ECMessage.MessageStatus.FAILED.ordinal()) {
            return DemoUtils.getDrawables(context, com.yuntongxun.eckitsdk.R.drawable.msg_state_failed);
        } else if (conversation.getSendStatus() == ECMessage.MessageStatus.SENDING.ordinal()) {
            return DemoUtils.getDrawables(context, com.yuntongxun.eckitsdk.R.drawable.msg_state_sending);
        } else {
            return null;
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        ViewHolder mViewHolder;
        if(convertView == null || convertView.getTag() == null) {
            view = View.inflate(mContext , com.yuntongxun.eckitsdk.R.layout.ytx_conversation_item, null);

            mViewHolder = new ViewHolder();
            mViewHolder.user_avatar = (ImageView) view.findViewById(com.yuntongxun.eckitsdk.R.id.avatar_iv);
            mViewHolder.prospect_iv = (ImageView) view.findViewById(com.yuntongxun.eckitsdk.R.id.avatar_prospect_iv);
            mViewHolder.nickname_tv = (TextView) view.findViewById(com.yuntongxun.eckitsdk.R.id.nickname_tv);
            mViewHolder.tipcnt_tv = (TextView) view.findViewById(com.yuntongxun.eckitsdk.R.id.tipcnt_tv);
            mViewHolder.update_time_tv = (TextView) view.findViewById(com.yuntongxun.eckitsdk.R.id.update_time_tv);
            mViewHolder.last_msg_tv = (CCPTextView) view.findViewById(com.yuntongxun.eckitsdk.R.id.last_msg_tv);
            mViewHolder.image_input_text = (ImageView) view.findViewById(com.yuntongxun.eckitsdk.R.id.image_input_text);
            view.setTag(mViewHolder);
        } else {
            view = convertView;
            mViewHolder = (ViewHolder) view.getTag();
        }

        com.yuntongxun.kitsdk.ui.chatting.model.ECConversation conversation = (com.yuntongxun.kitsdk.ui.chatting.model.ECConversation)getItem(position);
        if(conversation != null) {

            Gson g = new Gson();
            String userName = null;
            String userdata = conversation.getUserdata();
            if (userdata != null) {
                if (mConvType == CONV_TYPE_NOTICE) {
                    NoticeUserData uEntity = g.fromJson(userdata, NoticeUserData.class);
                    userName = uEntity.getName();
                } else {
                    CommonUserData uEntity = g.fromJson(userdata, CommonUserData.class);
                    userName = uEntity.getFrom().getName();
                }
            }
            if (!TextUtils.isEmpty(userName)) {
                mViewHolder.nickname_tv.setText(userName);
            }

            mViewHolder.last_msg_tv.setEmojiText(getConversationSnippet(conversation));
            mViewHolder.last_msg_tv.setCompoundDrawables(getChattingSnippentCompoundDrawables(mContext, conversation), null, null, null);
            String msgCount = conversation.getUnreadCount() > 100 ? "..." : String.valueOf(conversation.getUnreadCount());
            mViewHolder.tipcnt_tv.setText(msgCount);
            mViewHolder.tipcnt_tv.setVisibility(conversation.getUnreadCount() == 0 ? View.GONE : View.VISIBLE);
            mViewHolder.image_input_text.setVisibility(View.GONE);
            mViewHolder.update_time_tv.setText(getConversationTime(conversation));
            if(conversation.getSessionId().toUpperCase().startsWith("G")) {
                //mViewHolder.user_avatar.setImageResource(com.yuntongxun.eckitsdk.R.drawable.group_head);

            } else {
                //mViewHolder.user_avatar.setImageResource(com.yuntongxun.eckitsdk.R.drawable.default_avatar);
                if(conversation.getSessionId().equals(GroupNoticeSqlManager.CONTACT_ID)) {
                } else {
                }
            }
        }

        return view;
    }

    static class ViewHolder {
        ImageView user_avatar;
        TextView tipcnt_tv;
        ImageView prospect_iv;
        TextView nickname_tv;
        TextView update_time_tv;
        CCPTextView last_msg_tv;
        ImageView image_input_text;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public void onChanged(String sessionId) {
        notifyChange();
    }

    public void notifyChange() {
        clearData();
        Cursor cursor = ConversationSqlManager.getConversationCursor();
        if(cursor!=null && cursor.moveToFirst()) {
            do {
                ECConversation e = (ECConversation)getItem(cursor);
                Log.d("ConversationAdapter", e.toString());
                try {
                    String udata = e.getUserdata();
                    if (udata == null) continue;
                    JSONObject jsonObject = new JSONObject(udata);
                    String itype = "" + jsonObject.get("type");
                    if ("0".equals(itype)) {
                        JSONObject jsonObject1 = (JSONObject)jsonObject.get("to");
                        String id = "" + jsonObject1.get("id");
                        String typec = "" + jsonObject1.get("type");
                        if (id != null && id.toUpperCase().startsWith("G")) {
                            mDataGroup.add(e);
                        } else if ("2".equals(typec)) {
                            mDataDoctor.add(e);
                        } else if ("0".equals(typec)) {
                            mDataPatient.add(e);
                        }
                    } else {
                        mDataNotice.add(e);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        closeCursor();
        super.notifyDataSetChanged();
    }

    public void clearData() {
        mCount = -1;
        switch (mConvType) {
            case CONV_TYPE_PATIENT:
                 mDataPatient.clear();
                break;
            case CONV_TYPE_GROUP:
                mDataGroup.clear();
                break;
            case CONV_TYPE_DOCTOR:
                mDataDoctor.clear();
                break;
            case CONV_TYPE_NOTICE:
                mDataNotice.clear();
                break;
        }
    }

    public void closeCursor() {
        if(mCursor != null) {
            mCursor.close();
        }
    }
}
