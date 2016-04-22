package com.android.doctor.rest;

import com.android.doctor.model.AdjustPlanParam;
import com.android.doctor.model.ContactList;
import com.android.doctor.model.DSendInviteParam;
import com.android.doctor.model.DiagList;
import com.android.doctor.model.HosPaitentList;
import com.android.doctor.model.MedicClassify;
import com.android.doctor.model.MedicInfo;
import com.android.doctor.model.NewPlanRecord;
import com.android.doctor.model.PatientList;
import com.android.doctor.model.PatientStats;
import com.android.doctor.model.PlanDeta;
import com.android.doctor.model.PlanList;
import com.android.doctor.model.PlanRecordList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.RespError;
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
    Call<RespEntity<PlanList>> getPlanList(@QueryMap HashMap<String, String> option);

    @GET("/api/v1/doctor/getpatientfollowuprecord.json")
    Call<RespEntity<PlanList>> getDoctorPatientRecord(@Query("puid") String puid);

    @GET("/api/v1/followupplan/plandeta.json")
    Call<RespEntity<PlanDeta>> getPlanDeta(@Query("pid")String pid);

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
    Call<RespEntity<PlanDeta>> adjustPlan(@Body /*AdjustPlanParam*/ JsonObject jsonObject);

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
}
