package com.android.doctor.ui.topic;

import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.model.Comment;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.viewholder.TopicCommentViewHolder;
import com.android.doctor.ui.widget.treeview.model.TreeNode;
import com.android.doctor.ui.widget.treeview.view.AndroidTreeView;

import butterknife.InjectView;

/**
 * Created by Yong on 2016/4/6.
 */
public class TopicDetailActivity extends BaseActivity {

    @InjectView(R.id.container)
    protected ViewGroup containerView;
    private AndroidTreeView tView;
    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_topic_detail);
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar("");
        initCommentView();
    }

    private void initCommentView() {
        TreeNode root = TreeNode.root();

        TreeNode tr1 = new TreeNode(new Comment());
        TreeNode tr2 = new TreeNode(new Comment());
        TreeNode tr3 = new TreeNode(new Comment());

        addData(tr1);
        addData(tr2);
        addData(tr3);
        root.addChildren(tr1, tr2, tr3);

        tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setDefaultViewHolder(TopicCommentViewHolder.class);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        //tView.expandAll();
        containerView.addView(tView.getView());
    }

    private void addData(TreeNode profile) {
        TreeNode socialNetworks = new TreeNode(new Comment());
        TreeNode places = new TreeNode(new Comment());

        TreeNode facebook = new TreeNode(new Comment());
        TreeNode linkedin = new TreeNode(new Comment());
        TreeNode google = new TreeNode(new Comment());
        TreeNode twitter = new TreeNode(new Comment());

        TreeNode lake = new TreeNode(new Comment());
        TreeNode mountains = new TreeNode(new Comment());

        places.addChildren(lake, mountains);
        socialNetworks.addChildren(facebook, google, twitter, linkedin);
        profile.addChildren(socialNetworks, places);
    }
}
