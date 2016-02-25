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
import com.fidots.restaurant.models.SpecificDish;
import com.fidots.restaurant.ui.adapter.base.BaseRecyViewAdapter;
import com.fidots.restaurant.ui.viewholder.DishViewHolder;
import com.fidots.restaurant.ui.viewholder.DrinkViewHolder;
import com.fidots.restaurant.ui.viewholder.RoomViewHolder;
import com.fidots.restaurant.ui.viewholder.SpecificDishViewHolder;

/**
 * Created by Yong on 2016-02-14.
 */
public class RestaurantContentAdapter extends BaseRecyViewAdapter {

    private int mCellLayout;
    public RestaurantContentAdapter(int layout) {
        this.mCellLayout = layout;
    }

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
            } else if (RoomViewHolder.class.equals(viewHolder.getClass())) {
                Room r = (Room) this.getItem(pos);
                RoomViewHolder holder = (RoomViewHolder) viewHolder;
                holder.fillUI(r);
            } else if (DrinkViewHolder.class.equals(viewHolder.getClass())) {
                Drink d = (Drink) this.getItem(pos);
                DrinkViewHolder holder = (DrinkViewHolder) viewHolder;
                holder.fillUI(d);
            } else if (SpecificDishViewHolder.class.equals(viewHolder.getClass())) {
                SpecificDish s = (SpecificDish) this.getItem(pos);
                SpecificDishViewHolder holder = (SpecificDishViewHolder) viewHolder;
                holder.fillUI(s);
            }
        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflater.inflate(mCellLayout, viewGroup, false);
        LinearLayout frameLayout = (LinearLayout) view.findViewById(R.id.ll_holder);
        int paddingPx = (int) DeviceHelper.dpToPixel(32);
        int screenWidth = (int) DeviceHelper.getScreenWidth() - paddingPx;

        ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
        layoutParams.width = screenWidth / 3;
        //layoutParams.height = screenWidth / 3;
        frameLayout.setLayoutParams(layoutParams);
        frameLayout.invalidate();

        if (mCellLayout == R.layout.item_dish) {
            DishViewHolder viewHolder = new DishViewHolder(view);
            viewHolder.setItemClickListener(itemOptionClickListener);
            return viewHolder;
        } else if (mCellLayout == R.layout.item_room) {
            RoomViewHolder viewHolder = new RoomViewHolder(view);
            viewHolder.setItemClickListener(itemOptionClickListener);
            return viewHolder;
        } else if (mCellLayout == R.layout.item_drink) {
            DrinkViewHolder viewHolder = new DrinkViewHolder(view);
            viewHolder.setItemClickListener(itemOptionClickListener);
            return viewHolder;
        } else if (mCellLayout == R.layout.item_dish_specific) {
            SpecificDishViewHolder viewHolder = new SpecificDishViewHolder(view);
            viewHolder.setItemClickListener(itemOptionClickListener);
            return viewHolder;
        }
        return null;
    }
}
