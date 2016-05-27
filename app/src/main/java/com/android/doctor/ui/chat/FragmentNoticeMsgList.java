package com.android.doctor.ui.chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.interf.OnRefreshDataListener;
import com.android.doctor.model.AskMangeMsgList;
import com.android.doctor.model.Constants;
import com.android.doctor.model.ContactAssistantMsgList;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.model.GroupNoticeMsgList;
import com.android.doctor.model.PatientAskMsgList;
import com.android.doctor.model.PatientList;
import com.android.doctor.model.PatientReportMsgList;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.NoticeMsgListAdapter;
import com.android.doctor.ui.base.BaseRecyViewFragment;
import com.android.doctor.ui.mine.AskReplyListActivity;
import com.android.doctor.ui.patient.PatientProfileActivity;
import com.android.doctor.ui.plan.PlanDetaActivity;
import com.android.doctor.ui.widget.LinearSpacingItemDecoration;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

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
        mAdapter = new NoticeMsgListAdapter(mProStatus == 0 ? R.layout.item_notice_msg : R.layout.item_notice_msg_proc);
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
        if (Constants.NOTIFY_TYPE_CONTACT_ASSISTANT == mMsgType) {
            onLoadContactNoticeMsg();
        } else if (Constants.NOTIFY_TYPE_PATIENT_ASK == mMsgType) {
            onLoadPAskNoticeMsg();
        } else if (Constants.NOTIFY_TYPE_ASK_MANAGE == mMsgType) {
            onLoadAskManageNoticeMsg();
        } else if (Constants.NOTIFY_TYPE_GROUP_ASSISTANT == mMsgType) {
            onLoadGroupNoticeMsg();
        } else if (Constants.NOTIFY_TYPE_PATIENT_REPORT == mMsgType) {
            onLoadPReportNoticeMsg();
        }
    }

    public Map<String,String> getParam() {
        Map<String, String> param = new HashMap<>();
        param.put("msgtype", "" + mMsgType);
        if (mProStatus != TYPE_MSG_ALL) {
            param.put("process_status", "" + mProStatus);
        }
        return param;
    }

    /**
     * 200 联系人助手
     */
    private void onLoadContactNoticeMsg() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<ContactAssistantMsgList>> call = service.getContactNoticeMsgList(getParam());
        call.enqueue(new RespHandler<ContactAssistantMsgList>() {
            @Override
            public void onSucceed(RespEntity<ContactAssistantMsgList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    onSuccess(resp.getResponse_params().getData());
                } else {
                    onSuccess(new ArrayList());
                }
            }

            @Override
            public void onFailed(RespEntity<ContactAssistantMsgList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    /**
     * 300 患者咨询问诊
     *
     */
    private void onLoadPAskNoticeMsg() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PatientAskMsgList>> call = service.getPAskNoticeMsgList(getParam());
        call.enqueue(new RespHandler<PatientAskMsgList>() {
            @Override
            public void onSucceed(RespEntity<PatientAskMsgList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    onSuccess(resp.getResponse_params().getData());
                } else {
                    onSuccess(new ArrayList());
                }
            }

            @Override
            public void onFailed(RespEntity<PatientAskMsgList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    /**
     * 400 随诊管理
     *
     */
    private void onLoadAskManageNoticeMsg() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<AskMangeMsgList>> call = service.getAskManageNoticeMsgList(getParam());
        call.enqueue(new RespHandler<AskMangeMsgList>() {
            @Override
            public void onSucceed(RespEntity<AskMangeMsgList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    onSuccess(resp.getResponse_params().getData());
                } else {
                    onSuccess(new ArrayList());
                }
            }

            @Override
            public void onFailed(RespEntity<AskMangeMsgList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    /**
     * 500 群助手
     *
     */
    private void onLoadGroupNoticeMsg() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<GroupNoticeMsgList>> call = service.getGroupNoticeMsgList(getParam());
        call.enqueue(new RespHandler<GroupNoticeMsgList>() {
            @Override
            public void onSucceed(RespEntity<GroupNoticeMsgList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    onSuccess(resp.getResponse_params().getData());
                } else {
                    onSuccess(new ArrayList());
                }
            }

            @Override
            public void onFailed(RespEntity<GroupNoticeMsgList> resp) {
                onFail(resp.getError_msg());
            }
        });
    }

    /**
     * 700 患者报道
     *
     */
    private void onLoadPReportNoticeMsg() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<PatientReportMsgList>> call = service.getPReportNoticeMsgList(getParam());
        call.enqueue(new RespHandler<PatientReportMsgList>() {
            @Override
            public void onSucceed(RespEntity<PatientReportMsgList> resp) {
                if (resp != null && resp.getResponse_params() != null) {
                    onSuccess(resp.getResponse_params().getData());
                } else {
                    onSuccess(new ArrayList());
                }
            }

            @Override
            public void onFailed(RespEntity<PatientReportMsgList> resp) {
                onFail(resp.getError_msg());
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



    @Override
    public void onItemClick(int position, View view) {
        int resId = view.getId();
        Object obj = mAdapter.getItem(position);
        if(ContactAssistantMsgList.ContactAssistMsgEntity.class.equals(obj.getClass())) {
            ContactAssistantMsgList.ContactAssistMsgEntity e = (ContactAssistantMsgList.ContactAssistMsgEntity) obj;
            if (resId == R.id.tv_oper) { //同意或拒绝
                showMsgAction(e.getMsgid(),  e.getStatus());
            } else {
                if ("-1".equals(e.getStatus()) || "0".equals(e.getStatus())) { //设置已读
                    onProcessMsg(e.getMsgid(), e.getStatus(), String.valueOf(5), "");
                }
            }
        } else if (PatientAskMsgList.PAskMsgEntity.class.equals(obj.getClass())) {
            PatientAskMsgList.PAskMsgEntity e = (PatientAskMsgList.PAskMsgEntity) obj;
            if (resId == R.id.tv_oper) { //同意或拒绝
                showMsgAction(e.getMsgid(),  e.getStatus());
            } else {
                if ("-1".equals(e.getStatus()) || "0".equals(e.getStatus())) { //设置已读
                    onProcessMsg(e.getMsgid(), e.getStatus(), String.valueOf(5), "");
                }
                AskReplyListActivity.startAty(getActivity(), e.getMsgparam().getAskid());
            }
        } else if (AskMangeMsgList.AskManageMsgEntity.class.equals(obj.getClass())) {
            AskMangeMsgList.AskManageMsgEntity e = (AskMangeMsgList.AskManageMsgEntity) obj;
            if (resId == R.id.tv_oper) { //同意或拒绝
                showMsgAction(e.getMsgid(),  e.getStatus());
            } else {
                if ("-1".equals(e.getStatus()) || "0".equals(e.getStatus())) { //设置已读
                    onProcessMsg(e.getMsgid(), e.getStatus(), String.valueOf(5), "");
                }
                PlanList.PlanBaseEntity plan = e.getMsgparam();
                PlanDetaActivity.startAty(getActivity(), "" + plan.getPid(), plan.getStatus(), plan);
            }
        } else if (GroupNoticeMsgList.MsgEntity.class.equals(obj.getClass())) {
            GroupNoticeMsgList.MsgEntity e = (GroupNoticeMsgList.MsgEntity)obj;
            if (resId == R.id.tv_oper) { //同意或拒绝
                showMsgAction(e.getMsgid(),  e.getStatus());
            } else {
                if ("-1".equals(e.getStatus()) || "0".equals(e.getStatus())) { //设置已读
                    onProcessMsg(e.getMsgid(), e.getStatus(), String.valueOf(5), "");
                }
                String uType = e.getFromutype();
                if ("0".equals(uType)) {
                    DoctorProfileActivity.startAty(getActivity(), e.getFrom());
                } else if ("1".equals(uType)) {
                    PatientProfileActivity.startAty(getActivity(), e.getFrom());
                }
            }
        } else if (PatientReportMsgList.ReportMsgEntity.class.equals(obj.getClass())) {
            PatientReportMsgList.ReportMsgEntity e = (PatientReportMsgList.ReportMsgEntity) obj;
            if (resId == R.id.tv_oper) { //同意或拒绝
                showMsgAction(e.getMsgid(),  e.getStatus());
            } else {
                if ("-1".equals(e.getStatus()) || "0".equals(e.getStatus())) { //设置已读
                    onProcessMsg(e.getMsgid(), e.getStatus(), String.valueOf(5), "");
                }
                String uType = e.getFromutype();
                if ("0".equals(uType)) {
                    DoctorProfileActivity.startAty(getActivity(), e.getFrom());
                } else if ("1".equals(uType)) {
                    PatientProfileActivity.startAty(getActivity(), e.getFrom());
                }
            }
        }

    }


    private void showMsgAction(final String msgid, final String status) {
        DialogUtils.showBottomListDialog(getActivity(),
                Arrays.asList(getResources().getStringArray(R.array.notice_msg_oper)),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        switch (position) {
                            case 0 ://同意
                                onProcessMsg(msgid, status, String.valueOf(1), "");
                                break;
                            case 1://拒绝
                                onProcessMsg(msgid, status, String.valueOf(2), "");
                                break;
                            case 2:
                                break;
                            default:break;
                        }
                    }
                }, null);
    }


    private void onProcessMsg(String msgId, String oldstats, String newstats, String msgextend) {
        Map<String, String> map = new HashMap<>();
        map.put("msgid", msgId);
        map.put("oldstatus", oldstats);
        map.put("newstatus", newstats);
        map.put("msgextend", msgextend);
        onProcessMsg(map);
        //ConversationSqlManager.setChattingSessionRead(Long.getLong(sessionId));
    }

    public void onProcessMsg(Map<String, String> map) {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.processMsg(map);
        call.enqueue(new RespHandler() {
            @Override
            public void onSucceed(RespEntity resp) {
                onRefresh();
                UIHelper.showToast(resp.getError_msg());
            }

            @Override
            public void onFailed(RespEntity resp) {
                UIHelper.showToast(resp.getError_msg());
            }
        });
    }

}
