package com.android.doctor.ui.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.TopicReplyList;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.widget.treeview.model.TreeNode;
import com.yuntongxun.kitsdk.utils.EmoticonUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 *
 */
public class TopicReplyViewHolder extends TreeNode.BaseNodeViewHolder<TopicReplyList.TopicRepliesEntity> implements View.OnClickListener{

    private ImageView mIcon;
    private TextView mTvReplyUser;
    private TextView mTvReplyTime;
    private TextView mTvReplyContent;
    private LinearLayout mLlImages;
    private LinearLayout mLlReplyContent;
    private PopupWindow mPopupWindow;
    private TopicReplyList.TopicRepliesEntity mData;
    private OnReplyContentClickListener itemClickListener;

    public TopicReplyViewHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, TopicReplyList.TopicRepliesEntity value) {
        this.mData = value;
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_topic_comment, null, false);
        initView(view, value);
        initReplyOperView();
        return view;
    }


    private void initView(View view, TopicReplyList.TopicRepliesEntity obj) {
        if (obj == null) return;
        mIcon = (ImageView) view.findViewById(R.id.arrow_icon);
        mTvReplyUser = (TextView) view.findViewById(R.id.tv_reply_user);
        mTvReplyTime = (TextView) view.findViewById(R.id.tv_reply_time);
        mTvReplyContent = (TextView) view.findViewById(R.id.tv_reply_content);
        mLlImages = (LinearLayout) view.findViewById(R.id.ll_images);
        mLlReplyContent = (LinearLayout) view.findViewById(R.id.ll_reply_content);
        mLlReplyContent.setOnClickListener(this);
        setViewData(obj);
    }

    private void initReplyOperView() {
        View popupView = LayoutInflater.from(context).inflate(R.layout.topic_reply_oper, null);

        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));

        popupView.findViewById(R.id.tv_oper1).setOnClickListener(this);
        popupView.findViewById(R.id.tv_oper2).setOnClickListener(this);
        popupView.findViewById(R.id.tv_oper3).setOnClickListener(this);
        popupView.findViewById(R.id.tv_oper4).setOnClickListener(this);
    }

    public void setViewData(TopicReplyList.TopicRepliesEntity obj) {
        if (obj == null) return;
        mTvReplyUser.setText(obj.getReplynickname());
        String lastRep = obj.getReplytime();
        Date date = DateUtils.toDate(lastRep);
        mTvReplyTime.setText(String.format("%tm", date) + "-" + String.format("%td", date));
        //mTvReplyTime.setText(obj.getReplytime());
        //EmoticonUtil.emoji2CharSequence(getContext(), text, (int) getTextSize(), false)
        mTvReplyContent.setText(EmoticonUtil.emoji2CharSequence(context, obj.getContent(), (int) mTvReplyContent.getTextSize(), false));
        List<TopicReplyList.AttachmentsEntity> atcList = obj.getAttachments();
        if (atcList != null) {
            for (TopicReplyList.AttachmentsEntity att: atcList) {
                if (att == null) return;
                ImageView image = new ImageView(context);
                ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(att.getWidth(),att.getHeight());
                image.setLayoutParams(lp);
                image.setImageResource(R.drawable.ic_picture_occupy);
                mLlImages.addView(image);
            }
        }
    }

    @Override
    public void toggle(boolean active) {
        mIcon.setImageResource(active ? R.drawable.tri_arrow_down : R.drawable.tri_arrow_right);
        int themeColor = context.getResources().getColor(R.color.appThemePrimary);
        int defColor = context.getResources().getColor(R.color.app_theme_secondary_textcolor);
        mTvReplyUser.setTextColor(active ? themeColor : defColor);
        mLlReplyContent.setVisibility(active ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getContainerStyle() {
        return R.style.TreeNodeStyleCustom;
    }

    public void setItemClickListener(OnReplyContentClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }



    @Override
    public void onClick(View v) {
        //Log.d(AppConfig.TAG, "[TopicReplyViewHolder-> onClick] " + v.getId());
        if (v.getId() == R.id.ll_reply_content) {
            if (mPopupWindow != null) {
                mPopupWindow.showAsDropDown(v);
            }
        }
        else if (itemClickListener != null) {
            itemClickListener.onContentClick(v, mData);
            mPopupWindow.dismiss();
        }
    }


    public interface OnReplyContentClickListener {
        void onContentClick(View v, Object obj);
    }
}
