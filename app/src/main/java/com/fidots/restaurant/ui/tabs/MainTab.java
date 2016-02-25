package com.fidots.restaurant.ui.tabs;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.fragment.FragmentHome;
import com.fidots.restaurant.ui.fragment.FragmentLocalYellowPage;
import com.fidots.restaurant.ui.fragment.FragmentMyInfo;
import com.fidots.restaurant.ui.fragment.FragmentPartner;
import com.fidots.restaurant.ui.fragment.FragmentRestaurantDetails;

public enum MainTab {

	MAIN(0, R.string.main_tab_home_name, R.drawable.tab_icon_new,
			FragmentHome.class),

	TROLL(1, R.string.main_tab_local_yellow_page, R.drawable.tab_icon_new,
			FragmentLocalYellowPage.class),
	
	COLLECT(2, R.string.main_tab_partner, R.drawable.tab_icon_new,
			FragmentPartner.class),
			
	ME(3, R.string.main_tab_me, R.drawable.tab_icon_new,
			FragmentMyInfo.class);

	private int idx;
	private int resName;
	private int resIcon;
	private Class<?> clz;

	private MainTab(int idx, int resName, int resIcon, Class<?> clz) {
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
