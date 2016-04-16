package com.android.doctor.ui.viewholder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;


import com.android.doctor.R;
import com.android.doctor.model.Comment;
import com.android.doctor.ui.widget.treeview.model.TreeNode;

/**
 *
 */
public class TopicCommentViewHolder extends TreeNode.BaseNodeViewHolder<Comment> {

    private ImageView mIcon;

    public TopicCommentViewHolder(Context context) {
        super(context);
    }

    @Override
    public View createNodeView(TreeNode node, Comment value) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.item_topic_comment, null, false);
        mIcon = (ImageView) view.findViewById(R.id.arrow_icon);
        return view;
    }

    @Override
    public void toggle(boolean active) {
        mIcon.setImageResource(active ? R.drawable.tri_arrow_down : R.drawable.tri_arrow_right);
    }

    @Override
    public int getContainerStyle() {
        return R.style.TreeNodeStyleCustom;
    }
}
