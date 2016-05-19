package com.android.doctor.ui.chat;

import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.AppAsyncTask;
import com.android.doctor.model.ContactGroupList;
import com.android.doctor.model.ContactList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

public class ContactsCache {

    private static ContactsCache instance;

    private ContactsCache(){}

    public static ContactsCache getInstance() {
        if (instance == null) {
            instance = new ContactsCache();
        }

        return instance;
    }

    public void onLoadContact(int mType) {
        User.UserEntity u = AppContext.context().getUser();
        if (u == null) {
            return;
        }
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("page_size", "9999");
        queryMap.put("uid", u.getDuid());
        queryMap.put("utype","0");
        queryMap.put("linktype", mType == 0 ? "医生" : "患者");

        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ContactList>> call = service.getMyContactList(queryMap);
        call.enqueue(new RespHandler<ContactList>() {
            @Override
            public void onSucceed(RespEntity<ContactList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    saveContacts(resp.getResponse_params().getData());
                }
            }

            @Override
            public void onFailed(RespEntity<ContactList> resp) {}
        });
    }

    public void onLoadGroupContact() {
        User.UserEntity u = AppContext.context().getUser();
        if (u == null) {
            return;
        }
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ContactGroupList>> call = service.getMyContactGroupList(u.getDuid());
        call.enqueue(new RespHandler<ContactGroupList>() {
            @Override
            public void onSucceed(RespEntity<ContactGroupList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    saveGroups(resp.getResponse_params().getGroups());
                }
            }

            @Override
            public void onFailed(RespEntity<ContactGroupList> resp) {}
        });
    }

    public void saveContacts(final List<ContactList.ContactEntity> list) {
        if (list == null) return;
        AppAsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppContext.context().setProperty(AppConfig.APP_CONTACT_PEER, new Gson().toJson(list));
            }
        });
    }

    public void saveGroups(final List<ContactGroupList.GroupsEntity> d) {
        AppAsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (d == null) return;
                AppContext.context().setProperty(AppConfig.APP_CONTACT_GROUP, new Gson().toJson(d));
            }
        });
    }

    public List<ContactList.ContactEntity> getContacts() {
        Gson gson = new Gson();
        return gson.fromJson(AppContext.context().getProperty(AppConfig.APP_CONTACT_PEER),
                new TypeToken<List<ContactList.ContactEntity>>() {}.getType());
    }

    public  ContactList.ContactEntity findContact(String uid) {
        List<ContactList.ContactEntity> list = getContacts();
        if (list != null) {
            for (ContactList.ContactEntity e : list) {
                if (e != null) {
                    ContactList.ContactEntity.PlatformEntity platformEntity = e.getPlatform();
                    if (platformEntity != null) {
                        if (uid.equals(platformEntity.getDuid()) || uid.equals(platformEntity.getPuid()))
                            return e;
                    }
                }
            }
        }
        return null;
    }

    public  ContactGroupList.GroupsEntity findGroup(String groupId) {
        List<ContactGroupList.GroupsEntity> list = getGroups();
        if (list != null) {
            for (ContactGroupList.GroupsEntity e : list) {
                if (groupId.equals(e.getGroupId())) return e;
            }
        }
        return null;
    }

    public  List<ContactGroupList.GroupsEntity> getGroups() {
        Gson gson = new Gson();
        return gson.fromJson(AppContext.context().getProperty(AppConfig.APP_CONTACT_GROUP),
                new TypeToken<List<ContactList.ContactEntity>>() {}.getType());
    }

    public interface OnLoadResultListener {
        void onResult(List<ContactList.ContactEntity> eList);
    }
}
