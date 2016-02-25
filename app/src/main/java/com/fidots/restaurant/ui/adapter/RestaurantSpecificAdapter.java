package com.fidots.restaurant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fidots.restaurant.R;
import com.fidots.restaurant.helper.DeviceHelper;
import com.fidots.restaurant.interf.OnAdapterItemClickListener;
import com.fidots.restaurant.models.Dish;
import com.fidots.restaurant.models.Drink;
import com.fidots.restaurant.models.Room;
import com.fidots.restaurant.ui.adapter.base.BaseRecyViewAdapter;
import com.fidots.restaurant.ui.viewholder.DishViewHolder;
import com.fidots.restaurant.ui.viewholder.DrinkViewHolder;
import com.fidots.restaurant.ui.viewholder.RoomViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class RestaurantSpecificAdapter extends BaseRecyViewAdapter {

    public RestaurantSpecificAdapter() {}

    public void setItemOptionClickListener(
            OnAdapterItemClickListener itemOptionClickListener) {
        this.itemOptionClickListener = itemOptionClickListener;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int pos) {
        int viewType = viewHolder.getItemViewType();
        if (viewType == VIEW_TYPE_ITEM) {
            if (DishViewHolder.class.equals(viewHolder.getClass())) {
                Dish dish = (Dish) this.getItem(pos);
                DishViewHolder holder = (DishViewHolder) viewHolder;
                holder.fillUI(dish);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(R.layout.item_restaurant_special, viewGroup, false);
        LinearLayout frameLayout = (LinearLayout) view.findViewById(R.id.ll_holder);
        int paddingPx = (int) DeviceHelper.dpToPixel(32);
        int screenWidth = (int) DeviceHelper.getScreenWidth() - paddingPx;

        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.width = screenWidth / 3;
        //layoutParams.height = screenWidth / 3;
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.invalidate();

        DishViewHolder viewHolder = new DishViewHolder(view);
        viewHolder.setItemClickListener(itemOptionClickListener);
        return viewHolder;
    }
}
