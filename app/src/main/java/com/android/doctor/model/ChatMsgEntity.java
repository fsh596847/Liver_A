package com.android.doctor.model;

/**
 * Created by Yong on 2016/5/19.
 */

public class ChatMsgEntity {
    //名字
    private String name;
    //日期
    private String date;
    //聊天内容
    private String text;
    //是否为对方发来的信息
    private boolean isComMsg = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean getMsgType() {
        return isComMsg;
    }

    public void setMsgType(boolean isComMsg) {
        this.isComMsg = isComMsg;
    }

    public ChatMsgEntity() {
    }

    public ChatMsgEntity(String name, String date, String text, boolean isComMsg) {
        this.name = name;
        this.date = date;
        this.text = text;
        this.isComMsg = isComMsg;
    }
}
