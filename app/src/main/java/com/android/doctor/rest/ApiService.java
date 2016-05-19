package com.android.doctor.rest;

import com.android.doctor.model.AdjustPlanParam;
import com.android.doctor.model.ArchStatList;
import com.android.doctor.model.ArticleList;
import com.android.doctor.model.AsMyPlParam;
import com.android.doctor.model.AsPlDraftParam;
import com.android.doctor.model.HealthRecordList;
import com.android.doctor.model.LaborArchList;
import com.android.doctor.model.STopicEntity;
import com.android.doctor.model.SuggClassList;
import com.android.doctor.model.TPlanList;
import com.android.doctor.model.TestItemList;
import com.android.doctor.model.CheckOutItemList;
import com.android.doctor.model.ContactGroupList;
import com.android.doctor.model.ContactList;
import com.android.doctor.model.CrtPlanParam;
import com.android.doctor.model.DSendInviteParam;
import com.android.doctor.model.DiagList;
import com.android.doctor.model.DiseaseClass;
import com.android.doctor.model.DoctorInfo;
import com.android.doctor.model.DoctorKSLabelList;
import com.android.doctor.model.DoctorList;
import com.android.doctor.model.DoctorTimeSheet;
import com.android.doctor.model.DoctorZCLabelList;
import com.android.doctor.model.GroupDeta;
import com.android.doctor.model.GroupList;
import com.android.doctor.model.GroupMemberList;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.model.MedicClassify;
import com.android.doctor.model.MedicInfo;
import com.android.doctor.model.NewPlanRecord;
import com.android.doctor.model.NoticeMsgList;
import com.android.doctor.model.PatientBaseInfo;
import com.android.doctor.model.PatientList;
import com.android.doctor.model.PatientStats;
import com.android.doctor.model.PlanBaseInfo;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.PlanRecordList;
import com.android.doctor.model.PubPlanParam;
import com.android.doctor.model.RemindResultList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.RespError;
import com.android.doctor.model.RunPlanDeta;
import com.android.doctor.model.ScheduleCountList;
import com.android.doctor.model.TPlanDeta;
import com.android.doctor.model.TempPlanList;
import com.android.doctor.model.TopicBarList;
import com.android.doctor.model.TopicList;
import com.android.doctor.model.TopicReplyList;
import com.android.doctor.model.TreatPlanList;
import com.android.doctor.model.UpdatePlanDetaParam;
import com.android.doctor.model.User;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Yong on 2016-02-25.
 */
public interface ApiService {

    @POST("/api/v1/user/uregverifycode.json")
    Call<RespError> getURegVCode(@Body JsonObject jsonObject);

    @POST("/api/v1/user/uregister.json")
    Call<ResponseBody> signup(@Body Map<String, String> map);

    @POST("/api/v1/user/dlogin.json")
    Call<RespEntity<User>> login(@Body HashMap<String, String> user);

    @POST("/api/v1/user/upwdverifycode.json")
    Call<RespError> getUpwdVCode(@Body HashMap<String, String> param);

    @POST("/api/v1/user/uupdatepwd.json")
    Call<RespEntity<Object>> updatePassword(@Body HashMap<String, String> param);

    @POST("/api/v1/link/advancemypatients.json")
    Call<RespEntity<PatientList>> getPatientList(@Body Map<String, String> options);

    @GET("/api/v1/doctorstat/getdoctorpstats.json")
    Call<RespEntity<PatientStats>> getDoctorPStats(@Query("duid") String duid);

    @GET("/api/v1/followupplan/followupplans.json")
    Call<RespEntity<PlanList>> getPlanList(@QueryMap Map<String, String> option);

    @GET("/api/v1/doctor/getpatientfollowuprecord.json")
    Call<RespEntity<PlanList>> getDoctorPatientRecord(@Query("puid") String puid);

    @GET("/api/v1/followupplan/plandeta.json")
    Call<RespEntity<PlanDeta>> getPlanDeta(@Query("pid")String pid);

    @GET("/api/v1/followup/gettpldetail.json")
    Call<RespEntity<TPlanDeta>> getTPlanDeta(@Query("tplid")String tplid);

    @POST("/api/v1/followup/updatetpl.json")
    Call<RespEntity<PlanDeta>> updatePlan(@Body UpdatePlanDetaParam param);

    @GET("/api/v1/common/getdrugclass.json")
    Call<RespEntity<MedicClassify>> getMedicClassify();

    @GET("/api/v1/common/getdruglist.json")
    Call<RespEntity<MedicInfo>> getMedicInfo(@Query("pcode")String pcode);

    @GET("/api/v1/followupplan/planrecord.json")
    Call<RespEntity<PlanRecordList>> getPlanRecordList(@Query("pid")String pid);

    @POST("/api/v1/doctor/addfollowrecord.json")
    Call<RespEntity> addPlanRecord(@Body NewPlanRecord record);

    @GET("/api/v1/common/gettreatplan.json")
    Call<RespEntity<TreatPlanList>> getTreatPlanList();

    @GET("/api/v1/common/getdiaglist.json")
    Call<RespEntity<DiagList>> getDiagList();

    @Headers("Content-Type: application/json" )
    @POST("/api/v1/followupplan/adjust.json")
    Call<RespEntity<PlanDeta>> adjustPlan(@Body /*AdjustPlanParam*/ AdjustPlanParam param);

    @POST("/api/v1/followupplan/pub.json")
    Call<RespEntity<PlanDeta>> pubPlan(@Body /*AdjustPlanParam*/ PubPlanParam param);

    @POST("/api/v1/followupplan/finish.json")
    Call<RespEntity<Object>> terminatePlan(@Body String pid);

    @POST("/api/v1/followupplan/draft.json")
    Call<RespEntity<Object>> savePlAsDraft(@Body AsPlDraftParam para);

    @POST("/api/v1/followupplan/mytpl.json")
    Call<RespEntity<Object>> savePlAsMyPl(@Body AsMyPlParam param);

    @POST("/api/v1/followupplan/deldraft.json")
    Call<RespEntity<Object>> delPlDraft(@Body String pid);

    @POST("/api/v1/followup/createmytpl.json")
    Call<RespEntity<Object>> crtNewPlan(@Body CrtPlanParam param);

    @GET("/api/v1/doctor/hospitalPatient.json")
    Call getHospitalPatient(@QueryMap Map<String, String> map);

    @GET("/api/v1/doctor/hospitalPatientByDuid.json")
    Call<RespEntity<HosPaitentList>> getHosPatientByDuid(@QueryMap Map<String, String> map);

    @POST("/api/v1/link/doctorsendinvite.json")
    Call<RespEntity> dSendInvite(@Body DSendInviteParam param);

    @POST("/api/v1/link/doctorsendinvitemanual.json")
    Call<RespEntity> dSendInviteManual(@Body DSendInviteParam param);

    @GET("/api/v1/linkpersons/advancequerymylinkpersons.json")
    Call<RespEntity<ContactList>> getMyContactList(@QueryMap Map<String, String> map);

    @GET("/api/v1/ytx/im/querygroup.json")
    Call<RespEntity<ContactGroupList>> getMyContactGroupList(@Query("owneruid") String id);

    @GET("/api/v1/user/getduserinfobyfilter.json")
    Call<RespEntity<DoctorList>> getDoctorList(@QueryMap Map<String, String> map);

    @GET("/api/v1/dict/mget.json")
    Call<RespEntity<DoctorZCLabelList>> getDoctorZCFilterList(@QueryMap Map<String, String> map);

    @GET("/api/v1/hospitaldicts/hget1.json")
    Call<RespEntity<DoctorKSLabelList>> getDoctorKsFilterList(@QueryMap Map<String, String> map);

    @GET("/api/v1/msg/mytypemsg.json")
    Call<RespEntity<NoticeMsgList>> getNoticeMsgList(@QueryMap Map<String, String> map);

    @GET("/api/v1/doctor/doctorregister.json")
    Call<RespEntity<DoctorTimeSheet>> getDoctorTimeSheet(@Query("duid") String duid);

    @GET("/api/v1/user/dget.json")
    Call<RespEntity<DoctorInfo>> getDoctorBaseInfo(@Query("duid") String duid);

    @POST("/api/v1/linkpersons/removefriend.json")
    Call<RespEntity> deleteFriend(@Body String param);

    @POST("/api/v1/linkpersons/addlinkperson.json")
    Call<RespEntity> addFriend(@Body Map<String, String> params);

    @GET("/api/v1/archives/getpatient.json")
    Call<RespEntity<PatientBaseInfo>> getPatientBaseInfo(@Query("puid") String puid);

    @POST("/api/v1/ytx/im/creategroup.json")
    Call<RespEntity> createGroup(@Body Map<String, String> params);

    @GET("/api/v1/ask/getclass.json")
    Call<RespEntity<DiseaseClass>> getDisClass();

    @GET("/api/v1/ytx/im/getpublicgroups.json")
    Call<RespEntity<GroupList>> searchGroupList(@QueryMap Map<String, String> map);

    @GET("/api/v1/ytx/im/querymember.json")
    Call<RespEntity<GroupMemberList>> getGroupMemberList(@Query("groupId") String gid);

    @POST("/api/v1/ytx/im/setgroupmsg.json")
    Call<RespEntity> setGroupMsg(@Body Map<String, String> params);

    @POST("/api/v1/ytx/im/deletegroup.json")
    Call<RespEntity> deleteGroup(@Body Map<String, String> params);

    @POST("/api/v1/ytx/im/modifygroup.json")
    Call<RespEntity> modifyGroup(@Body Map<String, String> params);

    @POST("/api/v1/ytx/im/modifycard.json")
    Call<RespEntity> modifyGroupCard(@Body Map<String, String> params);

    @POST("/api/v1/ytx/im/applygroup.json")
    Call<RespEntity> applyJoinGroup(@Body Map<String, String> params);

    @POST("/api/v1/ytx/im/deletegroupmember.json")
    Call<RespEntity> deleteGroupMember(@Body Map<String, String> params);

    @GET("/api/v1/ytx/im/querygroup.json")
    Call<RespEntity<GroupList>> getMineGroupList(@Query("owneruid") String ownerId);

    @GET("/api/v1/ytx/im/querygroupdetail.json")
    Call<RespEntity<GroupDeta>> getGroupDeta(@Query("groupId") String groupId);

    @POST("/api/v1/ytx/im/inviteuser.json")
    Call<RespEntity> inviteGroupMember(@Body Map<String, String> params);

    @POST("/api/v1/msg/process.json")
    Call<RespEntity> processMsg(@Body Map<String, String> params);

    @POST("/api/v1/followupplan/getdoctorschedule.json")
    Call<RespEntity<ScheduleCountList>> getDoctorSchedule(@Body Map<String, Object> params);

    @GET("/api/v1/doctor/remindresults.json")
    Call<RespEntity<RemindResultList>> getDoctorRemindResultList(@QueryMap Map<String, String> map);

    @GET("/api/v1/followupplan/getplanbyid.json")
    Call<RespEntity<PlanBaseInfo>> getPlanById(@Query("pid") String pid);

    @GET("/api/v1/followupplan/runplandeta.json")
    Call<RespEntity<RunPlanDeta>> getRunPlanDeta(@QueryMap Map<String, String> map);

    @GET("/api/v1/followup/getmytpl.json")
    Call<RespEntity<TempPlanList>> getMyTPl(@Query("uid") String uid);

    @GET("/api/v1/followup/getsystpl.json")
    Call<RespEntity<TempPlanList>> getSysTPl();

    @GET("/api/v1/followup/getdefaulttpl.json")
    Call<RespEntity<TPlanList>> getDefaultTPl();

    @GET("/api/v1/archives/gethostestclass.json")
    Call<RespEntity<TestItemList>> getCheckItemList(@Query("hosid")String hosid);

    @GET("/api/v1/followup/getcheckitem.json")
    Call<RespEntity<CheckOutItemList>> getCheckOutItemList();

    @GET("/api/v1/topic/gettopics.json")
    Call<RespEntity<TopicList>> getTopicList(@QueryMap Map<String,String> queryMap);

    @GET("/api/v1/topic/gettopic.json")
    Call<RespEntity<STopicEntity>> getTopicById(@Query("topicid")String topicId);

    @GET("/api/v1/topicbar/gettopicbars.json")
    Call<RespEntity<TopicBarList>> getTopicBarList();

    @GET("/api/v1/topic/getreplies.json")
    Call<RespEntity<TopicReplyList>> getTopicReplyList(@Query("topicid")String topicId);

    @POST("/api/v1/topic/reply.json")
    Call<RespEntity> replyTopic(@Body Map<String, String> params);

    @GET("/api/v1/user/getsugg.json")
    Call<RespEntity<ArticleList>> getSuggList(@Query("uid")String uid);

    @GET("/api/v1/dict/suggclass.json")
    Call<RespEntity<SuggClassList>> getSuggClassList(@Query("uid")String uid);

    @GET("/api/v1/dict/get.json")
    Call<RespEntity<SuggClassList>> getMoreSuggClassList();

    @GET("/api/v1/dict/get.json")
    Call<RespEntity<SuggClassList>> getSearchSuggClassList(@Query("name")String name);

    @POST("/api/v1/sugg/collect.json")
    Call<RespEntity> collect(@Body Map<String, String> params);

    @POST("/api/v1/topic/pub.json")
    Call<RespEntity> pubTopic(@Body Map<String, Object> params);

    @GET("/api/v1/sugg/kget.json")
    Call<RespEntity<ArticleList>> getArticleList(@QueryMap Map<String,String> queryMap);

    @POST("/api/v1/sugg/focus.json")
    Call<RespEntity> subOrUnSub(@Body Map<String, Object> params);

    @GET("/api/v1/archives/gettestitemlist.json")
    Call<RespEntity<ArchStatList>> getArchStatData(@QueryMap Map<String, String> map);

    @GET("/api/v1/archives/getlist.json")
    Call<RespEntity<HealthRecordList>> getHealthRecordData(@QueryMap Map<String, String> map);

    @POST("/api/v1/remote/hisdatabyphoneimport.json")
    Call<RespEntity> archImport(@Body Map<String, Object> params);

    @POST("/api/v1/doctor/patientrecordbinding.json")
    Call<RespEntity> bindRecord(@Body Map<String, String> params);

    @GET("/api/v1/archives/getlist.json")
    Call<RespEntity<LaborArchList>> getLaborArchData(@QueryMap Map<String, String> map);
}
