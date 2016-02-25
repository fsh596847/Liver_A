package com.fidots.restaurant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fidots.restaurant.R;
import com.fidots.restaurant.helper.DeviceHelper;
import com.fidots.restaurant.interf.OnAdapterItemClickListener;
import com.fidots.restaurant.models.SpecificProduct;
import com.fidots.restaurant.ui.adapter.base.BaseRecyViewAdapter;
import com.fidots.restaurant.ui.viewholder.LocalSpecificProductViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class LocalSpecificProductAdapter extends BaseRecyViewAdapter<SpecificProduct> {

    public void setItemOptionClickListener(
            OnAdapterItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (LocalSpecificProductViewHolder.class.equals(viewHolder.getClass())) {
                SpecificProduct brand = (SpecificProduct) this.getItem(pos);
                LocalSpecificProductViewHolder brandViewHolder = (LocalSpecificProductViewHolder) viewHolder;
                brandViewHolder.fillUI(brand);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_local_special_product, viewGroup, false);
        LinearLayout frameLayout = (LinearLayout) view.findViewById(R.id.ll_holder);
        int paddingPx = (int) DeviceHelper.dpToPixel(32);
        int screenWidth = (int) DeviceHelper.getScreenWidth() - paddingPx;

        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.width = screenWidth / 3;
        //layoutParams.height = screenWidth / 3;
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.invalidate();

        LocalSpecificProductViewHolder viewHolder = new LocalSpecificProductViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }

}
