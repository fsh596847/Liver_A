package com.fidots.restaurant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fidots.restaurant.R;
import com.fidots.restaurant.interf.OnAdapterItemClickListener;
import com.fidots.restaurant.models.SpecificProduct;
import com.fidots.restaurant.ui.adapter.base.BaseRecyViewAdapter;
import com.fidots.restaurant.ui.viewholder.SpecificMerchantBaseViewHolder;
import com.fidots.restaurant.ui.viewholder.SpecificMerchantProductViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class SpecificMerchantInfoAdapter extends BaseRecyViewAdapter {
    public static final int VIEW_TYPE_BASE = 0;
    public static final int VIEW_TYPE_PRODUCT = 1;

    public void setItemOptionClickListener(
            OnAdapterItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    public int getItemViewType(int position) {
        int viewType = super.getItemViewType(position);
        if (viewType == VIEW_TYPE_ITEM) {
            Object item = data.get(position);
            if (position == 0) {
                viewType = VIEW_TYPE_BASE;
            } else {
                viewType = VIEW_TYPE_PRODUCT;
            }
        }
        return viewType;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_BASE) {
            if (SpecificMerchantBaseViewHolder.class.equals(viewHolder.getClass())) {
                SpecificProduct s = (SpecificProduct) this.getItem(pos);
                SpecificMerchantBaseViewHolder vh = (SpecificMerchantBaseViewHolder) viewHolder;
                vh.fillUI(s);
            }
        } else if (viewType == VIEW_TYPE_PRODUCT) {
            if (SpecificMerchantProductViewHolder.class.equals(viewHolder.getClass())) {
                SpecificProduct s = (SpecificProduct) this.getItem(pos);
                SpecificMerchantProductViewHolder vh = (SpecificMerchantProductViewHolder) viewHolder;
                vh.fillUI(s);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == VIEW_TYPE_BASE) {
            view = inflater.inflate(R.layout.item_specific_merchant_base, viewGroup, false);
            SpecificMerchantBaseViewHolder viewHolder = new SpecificMerchantBaseViewHolder(view);
            viewHolder.setItemClickListener(itemOptionClickListener);
            return viewHolder;
        } else if (viewType == VIEW_TYPE_PRODUCT) {
            view = inflater.inflate(R.layout.item_merchant_specific_product, viewGroup, false);
            SpecificMerchantProductViewHolder viewHolder = new SpecificMerchantProductViewHolder(view);
            viewHolder.setItemClickListener(itemOptionClickListener);
            return viewHolder;
        }
        return null;
    }

}
