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
import com.android.doctor.model.TopicList;
import com.android.doctor.model.TopicReplyList;
import com.android.doctor.ui.widget.treeview.model.TreeNode;

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

        setViewData(obj);
    }

    public void setViewData(TopicList.TopicEntity tpc) {
        mTvTpcTitle.setText(tpc.getTitle());
        mTvTpcAuthor.setText(tpc.getCreatenickname());
        mTvTpcDate.setText(tpc.getCreatetime());
        mTvTpcContent.setText(tpc.getContent());
    }

    @Override
    public void toggle(boolean active) {
    }

    @Override
    public int getContainerStyle() {
        return R.style.TreeNodeStyleCustom;
    }

}
