package com.fidots.restaurant.ui.tabs;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.fragment.FragmentConsumPerPerson;
import com.fidots.restaurant.ui.fragment.FragmentTabDishRoom;

public enum RestaurantTab {

	MAIN(0, R.string.restaurant_tab_dishes, R.drawable.tab_icon_new,
			FragmentTabDishRoom.class),

	TROLL(1, R.string.restaurant_tab_cabbage, R.drawable.tab_icon_new,
			FragmentConsumPerPerson.class),

	COLLECT(2, R.string.restaurant_tab_rooms, R.drawable.tab_icon_new,
			FragmentTabDishRoom.class),

	ME(3, R.string.restaurant_tab_drinks, R.drawable.tab_icon_new,
			FragmentTabDishRoom.class);

	private int idx;
	private int resName;
	private int resIcon;
	private Class<?> clz;

	private RestaurantTab(int idx, int resName, int resIcon, Class<?> clz) {
		this.idx = idx;
		this.resName = resName;
		this.resIcon = resIcon;
		this.clz = clz;
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public int getResName() {
		return resName;
	}

	public void setResName(int resName) {
		this.resName = resName;
	}

	public int getResIcon() {
		return resIcon;
	}

	public void setResIcon(int resIcon) {
		this.resIcon = resIcon;
	}

	public Class<?> getClz() {
		return clz;
	}

	public void setClz(Class<?> clz) {
		this.clz = clz;
	}
}
