package com.android.doctor.ui.tabs;

import com.android.doctor.R;
import com.yuntongxun.kitsdk.fragment.ConversationListFragment;

/**
 * Created by Yong on 2016/3/8.
 */
public enum ContactTab {

    MAIN(0, R.string.patient, 0, ConversationListFragment.class),

    TROLL(1, R.string.group, 0, ConversationListFragment.class),

    COLLECT(2, R.string.doctor, 0, ConversationListFragment.class);

    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    private ContactTab(int idx, int resName, int resIcon, Class<?> clz) {
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
