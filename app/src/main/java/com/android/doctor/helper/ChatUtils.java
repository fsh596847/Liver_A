package com.android.doctor.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.ContactGroupList;
import com.android.doctor.model.ContactList;
import com.android.doctor.model.User;
import com.google.gson.Gson;
import com.yuntongxun.kitsdk.custom.CommonUserData;
import com.yuntongxun.kitsdk.core.ECKitConstant;
import com.yuntongxun.kitsdk.db.IMessageSqlManager;
import com.yuntongxun.kitsdk.ui.ECChattingActivity;

/**
 * Created by Yong on 2016/4/25.
 */
public class ChatUtils {

    public static void chat2(Context context, String _sid, String _uid, String _toUserName, String _toUserUuid, String _toType) {
        Log.d(AppConfig.TAG, "[ChatUtils-> chat2] " + "[ " + _sid +", " + _uid +","+_toUserName+","+_toUserUuid+","+_toType);
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity == null) {
            return;
        }

        CommonUserData.ToEntity to = new CommonUserData.ToEntity(_uid, _toType, _toUserName, _toUserUuid);

        CommonUserData.FromEntity from = new CommonUserData.FromEntity(String.valueOf(userEntity.getDuid()),
                String.valueOf(0),
                userEntity.getUsername(),
                userEntity.getDuuid());
        CommonUserData udata = new CommonUserData(String.valueOf(0), to, from);

        String userdata = udata.toJson().toString();
        //long sid = IMessageSqlManager.querySessionIdByUserData(userdata);

        Intent intent = new Intent(context, ECChattingActivity.class);
        intent.putExtra(ECKitConstant.KIT_CONVERSATION_TARGET, _sid);
        intent.putExtra(ECChattingActivity.CONTACT_USER, _toUserName);
        intent.putExtra(ECChattingActivity.ARG_USER_DATA, userdata);
        context.startActivity(intent);

        Log.d(AppConfig.TAG, "[ChatUtils-> chat2], sid: " + _sid + ", " + udata.toJson().toString());
    }

    public static void chat2(Context ctx, String sid, String userData) {
        if (TextUtils.isEmpty(userData)) {
            return;
        }
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity == null) {
            return;
        }

        CommonUserData cud = new Gson().fromJson(userData, CommonUserData.class);

        CommonUserData.FromEntity from = new CommonUserData.FromEntity(String.valueOf(userEntity.getDuid()),
                String.valueOf(0),
                userEntity.getUsername(),
                userEntity.getDuuid());

        CommonUserData.ToEntity to;
        if (from.equals(cud.getTo())) {
            to = new CommonUserData.ToEntity(cud.getFrom().getId(),cud.getFrom().getType(),
                    cud.getFrom().getName(), cud.getFrom().getUuid());
        } else {
            to = cud.getTo();
        }
        CommonUserData udata = new CommonUserData(String.valueOf(0), to, from);

        Intent intent = new Intent(ctx, ECChattingActivity.class);
        intent.putExtra(ECKitConstant.KIT_CONVERSATION_TARGET, sid);
        intent.putExtra(ECChattingActivity.CONTACT_USER, to.getName());
        intent.putExtra(ECChattingActivity.ARG_USER_DATA, udata.toJson().toString());

        Log.d(AppConfig.TAG, "[ChatUtils-> chat2], contact_user, JsonObject: " + sid + "," + to.getName() + "," + udata.toJson().toString());
        ctx.startActivity(intent);
    }

}
