package com.yuntongxun.kitsdk.custom;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yong on 2016/4/26.
 */
public class UserDataUtil {

    public static Object getUserData(String udata) {
        if (TextUtils.isEmpty(udata)) return null;
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(udata);
            String tp = String.valueOf(jsonObject.get("type"));
            if ("0".equals(tp)) {
                return new Gson().fromJson(udata, CommonUserData.class);
            } else {
                return new Gson().fromJson(udata, NoticeUserData.class);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isGroup(CommonUserData userData) {
        String id = userData.getTo().getId();
        if (id != null && id.toUpperCase().startsWith("G")) {
            return true;
        }
        return false;
    }

    public static boolean isTypeTo(CommonUserData cud, String tp) {
        CommonUserData.ToEntity toEntity = cud.getTo();
        if (toEntity != null) {
            String toTp = toEntity.getType();
            if (toTp != null && toTp.equals(tp)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTypeFrom(CommonUserData cud, String tp) {
        CommonUserData.FromEntity fromEntity = cud.getFrom();
        if (fromEntity != null) {
            String from = fromEntity.getType();
            if (from != null && from.equals(tp)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPatient(CommonUserData uEntity) {
        if ((isTypeFrom(uEntity, String.valueOf(1))
                || isTypeTo(uEntity, String.valueOf(1))) && !isGroup(uEntity)) {
            return true;
        }
        return false;
    }

    public static boolean isDoctor(CommonUserData uEntity) {
        if (!isGroup(uEntity)
                && isTypeFrom(uEntity, String.valueOf(0))
                && isTypeTo(uEntity, String.valueOf(0))) {
            return true;
        }
        return false;
    }

    public static boolean isNotice(Object obj) {
        if (obj.getClass().equals(NoticeUserData.class)) {
            return true;
        }
        return false;
    }
}
