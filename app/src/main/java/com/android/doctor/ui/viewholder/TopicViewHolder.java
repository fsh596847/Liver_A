package com.android.doctor.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.TopicList;
import com.android.doctor.rest.RestClient;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;
import java.util.List;

/**
 * Created by Yong on 2016-02-14.
 */
public class TopicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    /**图片**/
    ImageView mImg;
    /**标题**/
    TextView name_tv;
    /**内容**/
    TextView content;
    /**用户**/
    TextView name_user;
    /**评论数**/
    TextView comment_num;
    /**评论日期**/
    TextView date_tv;

    private OnListItemClickListener itemClickListener;

    public TopicViewHolder(View view){
        super(view);
        initView(view);
        view.setOnClickListener(this);
    }

    public void initView(View view) {
        mImg = (ImageView) view.findViewById(R.id.img);
        name_tv = (TextView) view.findViewById(R.id.tv_title);
        content = (TextView) view.findViewById(R.id.tv_content);
        name_user = (TextView) view.findViewById(R.id.tv_user);
        comment_num = (TextView) view.findViewById(R.id.tv_comment_num);
        date_tv = (TextView) view.findViewById(R.id.tv_date);
    }

    public void setData(TopicList.TopicEntity topic) {
        if (topic == null) return;
        name_tv.setText(topic.getTitle());
        content.setText(topic.getContent());
        name_user.setText(topic.getCreatenickname());
        comment_num.setText(String.valueOf(topic.getReplies()));
        String lastRep = topic.getLastreplytime();
        Date date = DateUtils.toDate(lastRep);
        date_tv.setText(String.format("%tm", date) + "-" + String.format("%td", date));
        List<TopicList.TopicEntity.AttachmentsEntity> atts = topic.getAttachments();
        if (atts != null && !atts.isEmpty()) {
            mImg.setVisibility(View.VISIBLE);
            TopicList.TopicEntity.AttachmentsEntity att = atts.get(0);
            ImageLoader.getInstance().displayImage(RestClient.getImgURL(att.getAttachurl(), att.getWidth(), att.getHeight()), mImg);
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
}
