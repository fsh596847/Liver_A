package com.android.doctor.ui.topic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.model.TopicReplyList;
import com.android.doctor.ui.base.BaseFragment;
import com.android.doctor.ui.viewholder.TopicReplyViewHolder;
import com.android.doctor.ui.widget.EmptyLayout;
import com.android.doctor.ui.widget.treeview.model.TreeNode;
import com.android.doctor.ui.widget.treeview.view.AndroidTreeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by Yong on 2016/5/27.
 */
public class FragmentTopicReply extends BaseFragment{

    @InjectView(R.id.container)
    protected ViewGroup containerView;
    private AndroidTreeView tView;
    private TreeNode root = TreeNode.root();
    private TopicDetaActivity mDetaActivity;

    private List<TopicReplyList.TopicRepliesEntity> mRepList;

    public void setRepList(List<TopicReplyList.TopicRepliesEntity> mRepList) {
        this.mRepList = mRepList;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mDetaActivity = (TopicDetaActivity) getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_topic_reply;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        setReplyViewData(mRepList);
    }

    private void setSubReplyViewData(TreeNode trNode, List<TopicReplyList.TopicRepliesEntity> subRepList) {
        if (subRepList == null || subRepList.isEmpty()) return;
        for (TopicReplyList.TopicRepliesEntity reply: subRepList) {
            TreeNode subNode = new TreeNode(reply);
            TopicReplyViewHolder viewHolder = new TopicReplyViewHolder(mDetaActivity);
            viewHolder.setItemClickListener(mDetaActivity);
            subNode.setViewHolder(viewHolder);
            trNode.addChild(subNode);
            setSubReplyViewData(subNode, reply.getReplies());
        }
    }

    private void setReplyViewData(List<TopicReplyList.TopicRepliesEntity> repList) {
        if (repList != null) {
            List<TreeNode> nodes = new ArrayList<>();
            for (int i = 0; i < repList.size(); ++i) {
                TopicReplyList.TopicRepliesEntity rep = repList.get(i);
                //TopicReplyList.TopicRepliesEntity reply= new TopicReplyList.RepliesEntity(rep);
                TreeNode replyNode = new TreeNode(rep);
                TopicReplyViewHolder viewHolder = new TopicReplyViewHolder(mDetaActivity);
                viewHolder.setItemClickListener(mDetaActivity);
                replyNode.setViewHolder(viewHolder);
                nodes.add(replyNode);
                setSubReplyViewData(replyNode, rep.getReplies());
            }
            root.addChildren(nodes);
        }
        initTreeView();
    }

    private void initTreeView() {
        //TreeNode baseNode= new TreeNode("Folder with very long name ").setViewHolder(new SelectableItemHolder(this));
        // fillFolder(baseNode);
        // root.addChildren(baseNode);

        tView = new AndroidTreeView(mDetaActivity, root);
        tView.setDefaultAnimation(true);
        //tView.setDefaultViewHolder(SelectableItemHolder.class);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        //
        tView.setUse2dScroll(false);

        final View view = tView.getView(R.style.TreeNodeStyleDivided);
        tView.expandNodeFirst(root, true);
        //tView.expandAll();
        containerView.post(new Runnable() {
            @Override
            public void run() {
                containerView.addView(view);
            }
        });

        //root.getViewHolder().getNodeItemsView().invalidate();
        //containerView.invalidate();
        //tView.expandAll();
        //tView.setUseAutoToggle(false);
        //tView.collapseAll();
        //tView.toggleNode(root);
        //expandView(root);
    }
}
