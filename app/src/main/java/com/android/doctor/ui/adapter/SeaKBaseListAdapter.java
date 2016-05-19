package com.android.doctor.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.model.ArticleList;
import com.android.doctor.model.Constants;
import com.android.doctor.model.SuggClassList;
import com.android.doctor.ui.adapter.sticky_adapter.StickyRecyclerHeadersAdapter;
import com.android.doctor.ui.base.BaseRecyViewAdapter;
import com.android.doctor.ui.viewholder.ArticleViewHolder;
import com.android.doctor.ui.viewholder.KSubjectViewHolder;

public class SeaKBaseListAdapter<T> extends BaseRecyViewAdapter<T> implements StickyRecyclerHeadersAdapter<ViewHolder> {

	public SeaKBaseListAdapter() {}
	
	@Override
	public int getItemViewType(int position) {
		int viewType = super.getItemViewType(position);
		if (viewType == VIEW_TYPE_ITEM) {
			Object obj = data.get(position);
            if (obj != null) {
                if (obj.getClass().equals(SuggClassList.SuggEntity.class)) {
                    return Constants.KBASE_ITEM_TYPE_SUBJECT;
                } else if (obj.getClass().equals(ArticleList.SuggestsEntity.class)) {
                    return Constants.KBASE_ITEM_TYPE_ARTICLE;
                }
            }
		}
		return viewType;
	}
	
	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
        Object obj = this.getItem(position);
        if (KSubjectViewHolder.class.equals(holder.getClass())) {
            KSubjectViewHolder vh = (KSubjectViewHolder) holder;
            if (obj != null) {
                if (obj.getClass().equals(SuggClassList.SuggEntity.class)) {
                    vh.setViewData((SuggClassList.SuggEntity) obj);
                }
            }
        } else if (ArticleViewHolder.class.equals(holder.getClass())) {
            ArticleViewHolder aVh = (ArticleViewHolder) holder;
            aVh.setViewData((ArticleList.SuggestsEntity) obj);
        }
	}

	@Override
	protected ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == Constants.KBASE_ITEM_TYPE_SUBJECT) {
            view = inflater.inflate(R.layout.item_subs_subject, viewGroup, false);
            KSubjectViewHolder vh = new KSubjectViewHolder(view);
            vh.setItemClickListener(itemOptionClickListener);
            return vh;
        } else if (viewType == Constants.KBASE_ITEM_TYPE_ARTICLE) {
            view = inflater.inflate(R.layout.item_article, viewGroup, false);
            ArticleViewHolder vh = new ArticleViewHolder(view);
            vh.setItemClickListener(itemOptionClickListener);
            return vh;
        }

        return null;
	}

    @Override
    public long getHeaderId(int position) {
        Object obj = getItem(position);
        if (obj != null) {
            if (obj.getClass().equals(SuggClassList.SuggEntity.class)) {
                return 0;
            } else if (obj.getClass().equals(ArticleList.SuggestsEntity.class)) {
                return 1;
            }
        }
        return -1;
    }

    @Override
    public ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_header, parent, false);
        return new RecyclerView.ViewHolder(view) {};
    }

    @Override
    public void onBindHeaderViewHolder(ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        long l = getHeaderId(position);
        textView.setText(l == 0 ? "主题" : "文章");
    }
}
