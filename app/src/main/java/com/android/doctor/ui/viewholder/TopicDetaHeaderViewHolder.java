package com.android.doctor.ui.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.helper.DateUtils;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.HandlerHelper;
import com.android.doctor.model.TopicList;
import com.android.doctor.model.TopicReplyList;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.widget.treeview.model.TreeNode;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.Date;
import java.util.List;

/**
 *
 */
public class TopicDetaHeaderViewHolder extends TreeNode.BaseNodeViewHolder<TopicList.TopicEntity> {


    protected TextView mTvTpcTitle;

    protected TextView mTvTpcAuthor;

    protected TextView mTvTpcDate;

    protected TextView mTvTpcContent;

    protected ViewGroup images;

    public TopicDetaHeaderViewHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, TopicList.TopicEntity value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_tpdeta_header, null, false);
        initView(view, value);
        return view;
    }


    public void initView(View view, TopicList.TopicEntity obj) {
        if (obj == null) return;
        mTvTpcTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvTpcAuthor = (TextView) view.findViewById(R.id.tv_name);
        mTvTpcDate = (TextView) view.findViewById(R.id.tv_date);
        mTvTpcContent = (TextView) view.findViewById(R.id.tv_content);
        images = (ViewGroup) view.findViewById(R.id.ll_images);
        setViewData(obj);
    }

    public void setViewData(TopicList.TopicEntity tpc) {
        mTvTpcTitle.setText(tpc.getTitle());
        mTvTpcAuthor.setText(tpc.getCreatenickname());
        mTvTpcDate.setText(tpc.getCreatetime());
        mTvTpcContent.setText(tpc.getContent());
        List<TopicList.TopicEntity.AttachmentsEntity> atcList = tpc.getAttachments();
        if (atcList != null) {
            for (final TopicList.TopicEntity.AttachmentsEntity att: atcList) {
                if (att == null) return;

                HandlerHelper.postRunnOnUI(new Runnable() {
                    @Override
                    public void run() {
                        final ImageView image = (ImageView) LayoutInflater.from(context).inflate(R.layout.imageview, null);
                        int wdp = (int) DeviceHelper.pixelsToDp(att.getWidth());
                        int hdp = (int)DeviceHelper.pixelsToDp(att.getHeight());
                        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(att.getWidth(), att.getHeight());
                        image.setLayoutParams(lp);
                        ImageLoader.getInstance().displayImage(RestClient.getImgURL(att.getAttachurl(), att.getWidth(), att.getHeight()), image);
                        images.addView(image);
                    }
                });
            }
        }
    }

    @Override
    public void toggle(boolean active) {
    }

    @Override
    public int getContainerStyle() {
        return R.style.TreeNodeStyleCustom;
    }

}
