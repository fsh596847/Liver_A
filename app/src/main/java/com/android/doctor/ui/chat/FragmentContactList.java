package com.android.doctor.ui.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.app.AppManager;
import com.android.doctor.helper.ChatUtils;
import com.android.doctor.model.Constants;
import com.android.doctor.model.ContactGroupList;
import com.android.doctor.model.ContactList;
import com.android.doctor.model.MessageEvent;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.ContactListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.android.doctor.ui.patient.PatientProfileActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import retrofit2.Call;

/**
 * 联系人列表，包括群组
 * Created by Yong on 2016/3/8.
 */
public class FragmentContactList extends BaseRecyViewFragment {
    @InjectView(R.id.fl_container)
    protected FrameLayout mFrameLayout;

    public static final String EXTRA_TYPE = "type";
    private int mType;
    private Map<String, String> queryMap = new HashMap<>();
    private ContactActivity mActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mActivity = (ContactActivity) getActivity();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact_list;
    }

    @Override
    protected void setAdapter() {
        mAdapter = new ContactListAdapter(mType);
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void init() {
        Bundle b = getArguments();
        if (b != null) {
            mType = b.getInt(EXTRA_TYPE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event){
        if (event.message == Constants.EVENT_MSG_UPDATE_CONTACT_GROUP) {
            onRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    protected void onLoad(int pageNum, int limit) {
        User.UserEntity u = AppContext.context().getUser();
        if (u == null) {
            return;
        }
        String duid = u.getDuid();
        if (mType == Constants.CONTACT_TYPE_DOCTOR || mType == Constants.CONTACT_TYPE_PATIENT) {
            onLoadCommonContact(duid);
        } else {
            onLoadGroupContact(duid);
        }
    }

    private void onLoadCommonContact(String duid) {
        queryMap.put("page_size", "9999");
        queryMap.put("uid", "" + duid);
        queryMap.put("utype","0");
        queryMap.put("linktype", mType == Constants.CONTACT_TYPE_DOCTOR ? "医生" : "患者");

        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ContactList>> call = service.getMyContactList(queryMap);
        call.enqueue(new RespHandler<ContactList>() {
            @Override
            public void onSucceed(RespEntity<ContactList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    onSuccess(resp == null ? null : resp.getResponse_params().getData());
                }
            }

            @Override
            public void onFailed(RespEntity<ContactList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }


    private void onLoadGroupContact(String duid) {

        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ContactGroupList>> call = service.getMyContactGroupList(duid);
        call.enqueue(new RespHandler<ContactGroupList>() {
            @Override
            public void onSucceed(RespEntity<ContactGroupList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    onSuccess(resp == null ? null : resp.getResponse_params().getGroups());
                }
            }

            @Override
            public void onFailed(RespEntity<ContactGroupList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    @Override
    protected void onScrollChanged() {
        super.onScrollChanged();
        if (isScrollTop()) {
            mActivity.showSearchView();
        } else {
            mActivity.hideSearchView();
        }
    }

    public void onSearch(String keywords) {
        queryMap.put("keywords", keywords);
        onRefresh();
    }

    public void onClearSearch() {
        queryMap.remove("keywords");
        onRefresh();
    }

    private void forNewConversation(Object object) {
        if (object.getClass().equals(ContactList.ContactEntity.class)) {
            ContactList.ContactEntity contactEntity = (ContactList.ContactEntity) object;
            if (mType == Constants.CONTACT_TYPE_DOCTOR) {//医生详情
                DoctorProfileActivity.startAty(getActivity(), contactEntity.getPlatform().getDuid());
            } else {   //患者详情
                PatientProfileActivity.startAty(getActivity(), contactEntity.getPlatform().getPuid());
            }
        } else if (object.getClass().equals(ContactGroupList.GroupsEntity.class)) {
            ContactGroupList.GroupsEntity groupsEntity = (ContactGroupList.GroupsEntity) object;
            ChatUtils.chat2(getActivity(), groupsEntity.getGroupId(),groupsEntity.getGroupId(),
                    groupsEntity.getName(),groupsEntity.getUuid(), String.valueOf(0));
        }
    }


    @Override
    public void onItemClick(int position, View view) {
        Object object = mAdapter.getItem(position);
        if (object == null || mActivity == null) {
            Log.d(TAG, "[FragmentContactList-> onItemClick], type, code " + mType + "" );
            return;
        }
        int code = mActivity.getRCode();
        Log.d(TAG, "[FragmentContactList-> onItemClick], type, code " + code + "" );
        if (code == ContactActivity.REQUEST_CODE_FOR_NEW_CONVERSATION) {
            forNewConversation(object);
        } else if (code == ContactActivity.REQUEST_CODE_FOR_ADD_MEMBER) {
            if (object.getClass().equals(ContactList.ContactEntity.class)) {
                ContactList.ContactEntity contactEntity = (ContactList.ContactEntity) object;
                ContactList.ContactEntity.PlatformEntity pe = contactEntity.getPlatform();
                if (pe != null) {
                    Intent in = new Intent();
                    in.putExtra("uid", mType == Constants.CONTACT_TYPE_DOCTOR ? pe.getDuid() : pe.getPuid());
                    in.putExtra("usertype", String.valueOf(mType == Constants.CONTACT_TYPE_DOCTOR ? 0 : 1));
                    mActivity.setResult(Activity.RESULT_OK, in);
                    AppManager.getAppManager().finishActivity(mActivity);
                }
            }
        }
    }
}
