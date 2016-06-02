package com.android.doctor.model;

/**
 * Created by Yong on 2016/5/6.
 */
public class Constants {
    public static final int USER_TYPE_DOCTOR = 0x00;
    public static final int USER_TYPE_PATIENT = 0x01;

    public static final int PLAN_STATUS_INIT = 0x03;
    public static final int PLAN_STATUS_NOT_CREATE = 0x00;
    public static final int PLAN_STATUS_IN_EXEC = 0x01;
    public static final int PLAN_STATUS_FINISHED = 0x02;

    public static final int TOPIC_TYPE_LIST_BY_BARID = 0x04;
    public static final int TOPIC_TYPE_I_PUB = 0x05;
    public static final int TOPIC_TYPE_I_INVOVLE = 0x06;

    public static final int REQUEST_CODE_FOR_SELECT = 0x07;
    public static final int REQUEST_CODE_FOR_LOOKUP = 0x08;

    public static final int REQUEST_CODE_EDIT_EDU_EXP = 0x09;
    public static final int REQUEST_CODE_EDIT_CARRER_EXP = 0x10;
    public static final int REQUEST_CODE_EDIT_FAVOR = 0x11;
    public static final int REQUEST_CODE_EDIT_SUGGEST = 0x12;

    public static final int USER_COLLECTED_ARTICLE = 0x013;
    public static final int ARTICLE_LIST_UNDER_CLASS = 0x14;

    public static final int KBASE_ITEM_TYPE_SUBJECT = 0x015;
    public static final int KBASE_ITEM_TYPE_ARTICLE = 0x016;

    public static final int KBASE_SUBJECT_STATE_SUBSCRIBED = 0x017;
    public static final int KBASE_SUBJECT_STATE_UNSUBSCRIBED = 0x018;

    public static final int PATIENT_TYPE_OUT_ZY = 0x019;
    public static final int PATIENT_TYPE_MZ = 0x020;
    public static final int PATIENT_TYPE_IS_DOCTOR = 0x021;
    public static final int PATIENT_TYPE_DIAG_RECORD = 0x022;

    public static final int CONTACT_TYPE_PATIENT = 0x023;
    public static final int CONTACT_TYPE_DOCTOR = 0x024;
    public static final int CONTACT_TYPE_GROUP = 0x025;

    public static final int REQ_CODE_FOR_CREATE = 0x26;
    public static final int REQ_CODE_FOR_UPDATE = 0x27;
    public static final int REQ_CODE_FOR_LOOKUP = 0x08;

    public static final int NOTIFY_TYPE_CONTACT_ASSISTANT = 200;
    public static final int NOTIFY_TYPE_PATIENT_ASK = 300;
    public static final int NOTIFY_TYPE_ASK_MANAGE = 400;
    public static final int NOTIFY_TYPE_GROUP_ASSISTANT = 500;
    public static final int NOTIFY_TYPE_PATIENT_REPORT = 700;

    public static final int EVENT_MSG_UPDATE_CONTACT_GROUP = 0x100;
    public static final int EVENT_MSG_UPDATE_CONTACT_DOCTOR = 0x102;
    public static final int EVENT_MSG_UPDATE_CONTACT_PATIENT = 0x103;

    public static final int EVENT_MSG_UPDATE_SUGG_LIST = 0x200;
}
