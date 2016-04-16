package com.android.doctor.ui.tabs;

import com.android.doctor.R;
import com.android.doctor.ui.fragment.FragmentHome;
import com.android.doctor.ui.message.FragmentMainMsg;
import com.android.doctor.ui.fragment.FragmentMyInfo;
import com.android.doctor.ui.topic.FragmentTopic;
import com.android.doctor.ui.plan.FragmentVisitPlan;

public enum MainTab {

	MAIN(0, R.string.main_tab_patient, R.drawable.ic_tab_icon_0,
			FragmentHome.class),

	MSG(1, R.string.main_tab_info, R.drawable.ic_tab_icon_1,
			FragmentMainMsg.class),
	
	VISIT(2, R.string.main_tab_visit, R.drawable.ic_tab_icon_2,
			FragmentVisitPlan.class),

	TOPIC(3, R.string.main_tab_topic, R.drawable.ic_tab_icon_3,
			FragmentTopic.class),

	ME(4, R.string.main_tab_me, R.drawable.ic_tab_icon_4,
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
