package com.android.doctor.app;

import com.android.doctor.helper.AppAsyncTask;
import com.android.doctor.model.ArticleList;
import com.android.doctor.model.ContactGroupList;
import com.android.doctor.model.ContactList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.SuggClassList;
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

/**
 * Created by Yong on 2016/4/29.
 */
public class DataCacheManager {

    private static DataCacheManager instance;

    private DataCacheManager(){}

    public static DataCacheManager getInstance() {
        if (instance == null) {
            instance = new DataCacheManager();
        }

        return instance;
    }

    public void onLoadCollectArticles() {
        User.UserEntity u = AppContext.context().getUser();
        if (u == null) return;
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ArticleList>> call = service.getSuggList(u.getDuid());
        call.enqueue(new RespHandler<ArticleList>() {
            @Override
            public void onSucceed(RespEntity<ArticleList> resp) {
                if (resp == null || resp.getResponse_params() == null) {

                } else  {
                    saveCollectArticles(resp.getResponse_params().getSuggests());
                }
            }

            @Override
            public void onFailed(RespEntity<ArticleList> resp) {}
        });
    }

    public void onLoadSuggClassList() {
        User.UserEntity userEntity = AppContext.context().getUser();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<SuggClassList>> call = service.getSuggClassList(userEntity.getDuid());
        call.enqueue(new RespHandler<SuggClassList>() {
            @Override
            public void onSucceed(RespEntity<SuggClassList> resp) {
                if (resp == null || resp.getResponse_params() == null) {

                } else  {
                    saveSubscribleSub(resp.getResponse_params().getData());
                }
            }

            @Override
            public void onFailed(RespEntity<SuggClassList> resp) {}
        });
    }

    public void onLoadContact(final int mType) {
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
                    saveContacts(resp.getResponse_params().getData(),
                            mType == 0 ? AppConfig.APP_CONTACT_DOCTOR : AppConfig.APP_CONTACT_PATIENT);
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

    public void saveContacts(final List<ContactList.ContactEntity> list, final String key) {
        if (list == null) return;
        AppAsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppContext.context().setProperty(key, new Gson().toJson(list));
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

    public List<ContactList.ContactEntity> getContacts(String key) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(AppContext.context().getProperty(key),
                    new TypeToken<List<ContactList.ContactEntity>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public  ContactList.ContactEntity findContact(String key, String uid) {
        List<ContactList.ContactEntity> list = getContacts(key);
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

    public List<ContactGroupList.GroupsEntity> getGroups() {
        Gson gson = new Gson();
        try {
            return gson.fromJson(AppContext.context().getProperty(AppConfig.APP_CONTACT_GROUP),
                    new TypeToken<List<ContactGroupList.GroupsEntity>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface OnLoadResultListener {
        void onResult(List<ContactList.ContactEntity> eList);
    }

    public void saveCollectArticles(final List<ArticleList.SuggestsEntity> list) {
        AppAsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (list == null) return;
                AppContext.context().setProperty(AppConfig.APP_MY_COLLECT_ARTICLE, new Gson().toJson(list));
            }
        });
    }

    public void saveSubscribleSub(final List<SuggClassList.SuggEntity> list) {
        AppAsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (list == null) return;
                AppContext.context().setProperty(AppConfig.APP_MY_SUBSCRIBE_SUBJECT, new Gson().toJson(list));
            }
        });
    }

    public List<ArticleList.SuggestsEntity> getCollects() {
        Gson gson = new Gson();
        try {
            return gson.fromJson(AppContext.context().getProperty(AppConfig.APP_MY_COLLECT_ARTICLE),
                    new TypeToken<List<ArticleList.SuggestsEntity>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<SuggClassList.SuggEntity> getSubjects() {
        Gson gson = new Gson();
        try {
            return gson.fromJson(AppContext.context().getProperty(AppConfig.APP_MY_SUBSCRIBE_SUBJECT),
                    new TypeToken<List<SuggClassList.SuggEntity>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArticleList.SuggestsEntity findArticlesByID(String code) {
        List<ArticleList.SuggestsEntity> list = getCollects();
        if (list != null) {
            for (ArticleList.SuggestsEntity e : list) {
                if (e != null && e.getUniquecode().equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

    public SuggClassList.SuggEntity findSubjectByCode(String c) {
        List<SuggClassList.SuggEntity> list = getSubjects();
        if (list != null) {
            for (SuggClassList.SuggEntity e : list) {
                if (e != null && e.getCode().equals(c)) {
                    return e;
                }
            }
        }
        return null;
    }
}
