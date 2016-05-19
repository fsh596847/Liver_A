package com.android.doctor.ui.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.interf.OnRefreshDataListener;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.model.NoticeMsgList;
import com.android.doctor.model.PatientList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.NoticeMsgListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.android.doctor.ui.widget.LinearSpacingItemDecoration;
import com.google.gson.Gson;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/9.
 */
public class FragmentNoticeMsgList extends BaseRecyViewFragment {
    public static final int TYPE_MSG_NOT_PROCESS = 0;
    public static final int TYPE_MSG_ALL = 1;

    private OnRefreshDataListener refreshDataListener;
    private int mProStatus = 1;
    private int mMsgType;
    private String sessionId;
    private Map<String, String> option = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recycler_view;
    }

    public FragmentNoticeMsgList() {
        Log.d(AppConfig.TAG,"FragentNoticeMsgList()" );
    }

    @Override
    protected void init() {
        Bundle b = getArguments();
        if (b != null) {
            mMsgType = b.getInt("type");
            mProStatus = b.getInt("state");
            sessionId = b.getString(NoticeMsgActivity.ARG_SESSION_ID);
        }
    }

    @Override
    protected void setAdapter() {
        mAdapter = new NoticeMsgListAdapter(R.layout.item_notice_msg);
        mAdapter.setItemOptionClickListener(this);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setLayoutManager() {
        super.setLayoutManager();
        recyclerView.addItemDecoration(new LinearSpacingItemDecoration(8, LinearLayoutManager.VERTICAL));
    }

    public void setRefreshDataListener(OnRefreshDataListener refreshDataListener) {
        this.refreshDataListener = refreshDataListener;
    }

    @Override
    protected void initData() {
        super.initData();
        onRefresh();
    }

    @Override
    protected void onLoad(int pageNum, int limit) {
        option.put("msgtype", "" + mMsgType);
        option.put("process_status", "" + mProStatus);

        onLoadRequest(option);
    }

    /**
     *
     * @param option
     */
    private void onLoadRequest(Map<String, String> option) {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<NoticeMsgList>> call = service.getNoticeMsgList(option);
        call.enqueue(new Callback<RespEntity<NoticeMsgList>>() {
            @Override
            public void onResponse(Call<RespEntity<NoticeMsgList>> call, Response<RespEntity<NoticeMsgList>> response) {
                RespEntity<NoticeMsgList> data = response.body();
                if (response.isSuccessful()) {
                    if (data == null) {
                        onSuccess(new ArrayList());
                        return;
                    } else if (data.getResponse_params() != null) {
                        onSuccess(data.getResponse_params().getData());
                    }
                } else {
                    String errMsg = "";
                    if (data != null) {
                        errMsg = data.getError_msg();
                    }
                    onFail(errMsg);
                }
            }

            @Override
            public void onFailure(Call<RespEntity<NoticeMsgList>> call, Throwable t) {
                Log.d(TAG, t.toString());
                onFail("加载失败");
            }
        });
    }


    @Override
    protected void onLoadMore(int pageNum, int limit) {
        List data = mAdapter.getData();
        Log.d(AppConfig.TAG, "onLoadMore, pn, limit " + pageNum +", " + limit);
        if (data != null) {
            int pos = data.size() - 1;
            if (0 <= pos) {
                Object obj = data.get(pos);
                if (obj != null) {
                    if (obj.getClass().equals(PatientList.DataEntity.class)) {
                        PatientList.DataEntity last = (PatientList.DataEntity) obj;
                        onLoad(last.getRelationship(), PAGE_SIZE);
                    } else if (obj.getClass().equals(HosPaitentList.HosPatientEntity.class)){
                        HosPaitentList.HosPatientEntity entity = (HosPaitentList.HosPatientEntity) obj;
                        onLoad(entity.getHospitalPatientId(), PAGE_SIZE);
                    }
                }
            }
        }
    }

    @Override
    protected void onSuccess(List d) {
        super.onSuccess(d);
        if (refreshDataListener != null) {
            refreshDataListener.onRefreshComplete("success");
        }
    }

    @Override
    protected void onFail(String msg) {
        super.onFail(msg);
        if (refreshDataListener != null) {
            refreshDataListener.onRefreshComplete(msg);
        }
    }

    public void onSearch(String key, String keywords) {
        option.put(key, keywords);
        onRefresh();
    }

    public void onFilter(List<User.UserEntity.GroupsEntity.ChildgroupsEntity> groups) {
        if (groups.isEmpty()) {
            option.remove("groups");
        } else {
            option.put("groups", new Gson().toJson(groups));
        }
        onRefresh();
    }

    public void clearOption(String key) {
        option.remove(key);
        onRefresh();
    }



    @Override
    public void onItemClick(int position, View view) {
        final NoticeMsgList.MsgEntity msg = (NoticeMsgList.MsgEntity) mAdapter.getItem(position);
        if (view.getId() == R.id.tv_oper) {
            DialogUtils.showBottomListDialog(getActivity(),
                    Arrays.asList(getResources().getStringArray(R.array.notice_msg_oper)),
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                            dialog.dismiss();
                            switch (position) {
                                case 0 ://同意
                                    onProcessMsg(msg.getMsgid(), msg.getStatus(), String.valueOf(1), "");
                                    break;
                                case 1://拒绝
                                    onProcessMsg(msg.getMsgid(), msg.getStatus(), String.valueOf(2), "");
                                    break;
                                case 2:
                                    break;
                                default:break;
                            }
                        }
                    }, null);
        }
    }

    private void onProcessMsg(String msgId, String oldstats, String newstats, String msgextend) {
        Map<String, String> map = new HashMap<>();
        map.put("msgid", msgId);
        map.put("oldstatus", oldstats);
        map.put("newstatus", newstats);
        map.put("msgextend", msgextend);
        ((NoticeMsgActivity)getActivity()).onProcessMsg(map);
        //ConversationSqlManager.setChattingSessionRead(Long.getLong(sessionId));
    }

}
