package com.fidots.restaurant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.fidots.restaurant.R;
import com.fidots.restaurant.interf.OnAdapterItemClickListener;
import com.fidots.restaurant.models.SpecificProduct;
import com.fidots.restaurant.ui.adapter.base.BaseRecyViewAdapter;
import com.fidots.restaurant.ui.viewholder.SpecificMerchantProductViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class MerchantSpecificProductAdapter extends BaseRecyViewAdapter<SpecificProduct> {

    public void setItemOptionClickListener(
            OnAdapterItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (SpecificMerchantProductViewHolder.class.equals(viewHolder.getClass())) {
                SpecificProduct s = (SpecificProduct) this.getItem(pos);
                SpecificMerchantProductViewHolder vh = (SpecificMerchantProductViewHolder) viewHolder;
                vh.fillUI(s);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_merchant_specific_product, viewGroup, false);
        SpecificMerchantProductViewHolder viewHolder = new SpecificMerchantProductViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }

}
