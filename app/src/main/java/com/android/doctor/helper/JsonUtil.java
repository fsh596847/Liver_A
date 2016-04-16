package com.android.doctor.helper;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by Yong on 2016/4/11.
 */
public class JsonUtil {

    public static JsonObject toJson(String str) {
        JsonObject jso = (JsonObject)new JsonParser().parse(str);
        return jso;
    }
}
