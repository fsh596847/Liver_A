package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.android.doctor.R;
import com.android.doctor.interf.OnListItemClickListener;
import com.android.doctor.model.ArticleList;
import com.android.doctor.model.TopicList;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.ArticleViewHolder;
import com.android.doctor.ui.viewholder.SimpleTextViewHolder;
import com.android.doctor.ui.viewholder.TopicViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class TopicListAdapter extends BaseRecyViewAdapter {
    int layout;
    public TopicListAdapter(int layout) {
        this.layout = layout;
    }

    public void setItemOptionClickListener(
            OnListItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (TopicViewHolder.class.equals(viewHolder.getClass())) {
                TopicList.TopicEntity obj = (TopicList.TopicEntity) this.getItem(pos);
                TopicViewHolder holder = (TopicViewHolder) viewHolder;
                holder.setData(obj);
            } else if (ArticleViewHolder.class.equals(viewHolder.getClass())) {
                ArticleList.SuggestsEntity sugg = (ArticleList.SuggestsEntity) this.getItem(pos);
                ArticleViewHolder artVH = (ArticleViewHolder) viewHolder;
                artVH.setViewData(sugg);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(layout, viewGroup, false);
        if (layout == R.layout.item_topic) {
            TopicViewHolder viewHolder = new TopicViewHolder(view);
            viewHolder.setItemClickListener(itemOptionClickListener);
            return viewHolder;
        } else if (layout == R.layout.item_article) {
            ArticleViewHolder viewHolder = new ArticleViewHolder(view);
            viewHolder.setItemClickListener(itemOptionClickListener);
            return viewHolder;
        }
        return null;
    }
}
