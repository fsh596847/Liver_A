package com.android.doctor.ui.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.HandlerHelper;
import com.android.doctor.model.TopicReplyList;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.widget.treeview.model.TreeNode;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yuntongxun.kitsdk.utils.EmoticonUtil;

import java.util.Date;
import java.util.List;

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
    private ViewGroup actionView;
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
        initReplyOperView(view);
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
        actionView = (ViewGroup) view.findViewById(R.id.include_reply_action);
        mLlReplyContent.setOnClickListener(this);
        setViewData(obj);
    }

    private void initReplyOperView(View view) {
        /*View popupView = LayoutInflater.from(context).inflate(R.layout.topic_reply_oper, null);

        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));*/

        view.findViewById(R.id.tv_oper1).setOnClickListener(this);
        view.findViewById(R.id.tv_oper2).setOnClickListener(this);
        view.findViewById(R.id.tv_oper3).setOnClickListener(this);
        view.findViewById(R.id.tv_oper4).setOnClickListener(this);
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
            for (final TopicReplyList.AttachmentsEntity att: atcList) {
                if (att == null) return;

                HandlerHelper.postRunnOnUI(new Runnable() {
                    @Override
                    public void run() {
                        final ImageView image = (ImageView) LayoutInflater.from(context).inflate(R.layout.imageview, null);
                        int wdp = (int)DeviceHelper.pixelsToDp(att.getWidth());
                        int hdp = (int)DeviceHelper.pixelsToDp(att.getHeight());
                        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(att.getWidth(), att.getHeight());
                        image.setLayoutParams(lp);
                        ImageLoader.getInstance().displayImage(RestClient.getImgURL(att.getAttachurl(), att.getWidth(), att.getHeight()), image);
                        mLlImages.addView(image);
                    }
                });
            }
        }
    }

    @Override
    public void toggle(boolean active) {
        hideActionView();
        mIcon.setImageResource(active ? R.drawable.tri_arrow_down : R.drawable.tri_arrow_right);
        int themeColor = context.getResources().getColor(R.color.appThemePrimary);
        int defColor = context.getResources().getColor(R.color.app_theme_secondary_textcolor);
        mTvReplyUser.setTextColor(active ? themeColor : defColor);
        //mLlReplyContent.setVisibility(active ? View.VISIBLE : View.GONE);
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
            if (actionView.getVisibility() == View.VISIBLE) {
                hideActionView();
            } else {
                showActionView();
            }
        }
        else if (itemClickListener != null) {
            itemClickListener.onContentClick(v, mData);
            hideActionView();
        }
    }

    private void showActionView() {
        actionView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.common_slide_right_in));
        actionView.setVisibility(View.VISIBLE);
    }

    private void hideActionView() {
        actionView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.common_slide_right_out));
        actionView.setVisibility(View.GONE);
    }

    public interface OnReplyContentClickListener {
        void onContentClick(View v, Object obj);
    }
}
