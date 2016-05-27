package com.yuntongxun.kitsdk.custom;

import android.app.Activity;
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
import com.yuntongxun.eckitsdk.R;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.kitsdk.adapter.CCPListAdapter;
import com.yuntongxun.kitsdk.core.ECAsyncTask;
import com.yuntongxun.kitsdk.db.GroupNoticeSqlManager;
import com.yuntongxun.kitsdk.group.GroupNoticeHelper;
import com.yuntongxun.kitsdk.ui.chatting.model.ECConversation;
import com.yuntongxun.kitsdk.ui.chatting.model.ImgInfo;
import com.yuntongxun.kitsdk.ui.chatting.view.CCPTextView;
import com.yuntongxun.kitsdk.utils.DateUtil;
import com.yuntongxun.kitsdk.utils.DemoUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yong on 2016/3/24.
 */
public class CustomConversationAdapter extends BaseAdapter {
    /**
     * 患者
     **/
    private static final int CONV_TYPE_PATIENT = 0;
    /**
     * 群组
     **/
    private static final int CONV_TYPE_GROUP = 1;
    /**
     * 医生
     **/
    private static final int CONV_TYPE_DOCTOR = 2;
    /**
     * 通知
     **/
    private static final int CONV_TYPE_NOTICE = 3;
    /**
     * 数据Cursor
     */
    private Cursor mCursor;
    /**
     * 数据缓存
     */
    private List<ECConversation> mConversations = new ArrayList<ECConversation>();

    private CCPListAdapter.OnListAdapterCallBackListener mCallBackListener;
    /**
     * 适配器使用数据类型
     */
    protected ECConversation t;
    /**
     * 上下文对象
     */
    protected Context mContext;
    /**
     * 数据总数
     */
    protected int mCount;
    /**
     * 会话类型
     */
    private int mConvType;

    /**
     * 构造方法
     *
     * @param ctx
     */
    public CustomConversationAdapter(Context ctx, int type, CCPListAdapter.OnListAdapterCallBackListener listener) {
        mContext = ctx;
        this.mCount = -1;
        this.mConvType = type;
        this.mCallBackListener = listener;
        notifyChange();
    }


    @Override
    public int getCount() {
        return mConversations == null ? 0 : mConversations.size();
    }

    @Override
    public ECConversation getItem(int position) {
        if (position < 0 || mConversations.size() <= position) {
            return null;
        }
        ECConversation _t = mConversations.get(Integer.valueOf(position));
        return _t;
    }


    /**
     * 会话时间
     *
     * @param conversation
     * @return
     */
    protected final CharSequence getConversationTime(com.yuntongxun.kitsdk.ui.chatting.model.ECConversation conversation) {
        if (conversation.getSendStatus() == ECMessage.MessageStatus.SENDING.ordinal()) {
            return mContext.getString(com.yuntongxun.eckitsdk.R.string.conv_msg_sending);
        }
        if (conversation.getDateTime() <= 0) {
            return "";
        }
        return DateUtil.getDateString(conversation.getDateTime(),
                DateUtil.SHOW_TYPE_CALL_LOG).trim();
    }

    /**
     * 根据消息类型返回相应的主题描述
     *
     * @param conversation
     * @return
     */
    protected final CharSequence getConversationSnippet(com.yuntongxun.kitsdk.ui.chatting.model.ECConversation conversation) {
        if (conversation == null) {
            return "";
        }
        if (GroupNoticeSqlManager.CONTACT_ID.equals(conversation.getSessionId())) {
            return GroupNoticeHelper.getNoticeContent(conversation.getContent());
        }
        if (conversation.getMsgType() == ECMessage.Type.VOICE.ordinal()) {
            return mContext.getString(com.yuntongxun.eckitsdk.R.string.app_voice);
        } else if (conversation.getMsgType() == ECMessage.Type.FILE.ordinal()) {
            return mContext.getString(com.yuntongxun.eckitsdk.R.string.app_file);
        } else if (conversation.getMsgType() == ECMessage.Type.IMAGE.ordinal()) {
            return mContext.getString(com.yuntongxun.eckitsdk.R.string.app_pic);
        } else if (conversation.getMsgType() == ECMessage.Type.VIDEO.ordinal()) {
            return mContext.getString(com.yuntongxun.eckitsdk.R.string.app_video);
        }
        return conversation.getContent();
    }

    /**
     * 根据消息发送状态处理
     *
     * @param context
     * @param conversation
     * @return
     */
    public static Drawable getChattingSnippentCompoundDrawables(Context context, com.yuntongxun.kitsdk.ui.chatting.model.ECConversation conversation) {
        if (conversation.getSendStatus() == ECMessage.MessageStatus.FAILED.ordinal()) {
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
        if (convertView == null || convertView.getTag() == null) {
            view = View.inflate(mContext, com.yuntongxun.eckitsdk.R.layout.ytx_conversation_item, null);

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

        com.yuntongxun.kitsdk.ui.chatting.model.ECConversation conversation = getItem(position);
        if (conversation != null) {

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
            if (conversation.getSessionId().toUpperCase().startsWith("G")) {
                mViewHolder.user_avatar.setImageResource(R.drawable.select_group_head);

            } else {
                mViewHolder.user_avatar.setImageResource(R.drawable.select_account_photo_two);
                if (conversation.getSessionId().equals(GroupNoticeSqlManager.CONTACT_ID)) {
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


    public synchronized void notifyChange() {
        Log.d("TAG", "[ConversationAdapter-> notifyChange] " + mConvType);
        new LoadDataAsyncTask(mContext).execute(mConvType);
    }

    public class LoadDataAsyncTask extends ECAsyncTask {

        /**
         * @param context
         */
        public LoadDataAsyncTask(Context context) {
            super(context);
        }

        @Override
        protected Object doInBackground(Object... params) {
            return ConversationDataPool.getInstance().getConversationByType((Integer) params[0]);
        }

        @Override
        protected void onPostExecute(Object result) {
            if(result instanceof List) {
                mConversations = (List<ECConversation>) result;
                if (mCallBackListener != null) {
                    mCallBackListener.OnListAdapterCallBack();
                }
                notifyDataSetChanged();
            }
        }

    }
}
