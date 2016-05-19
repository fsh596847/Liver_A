package com.android.doctor.ui.chat;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.app.AppManager;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.DiseaseClass;
import com.android.doctor.model.GroupList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.yuntongxun.kitsdk.utils.TextUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/3/26.
 */
public class EditGroupActivity extends BaseActivity {

    public static final String ARG_ENTITY = "ARG_ENTITY";
    @InjectView(R.id.edt_group_name)
    protected EditText mEdtGroupName;
    @InjectView(R.id.edt_group_classify)
    protected TextView mTvGroupClassify;
    @InjectView(R.id.tv_identify)
    protected TextView mTvGroupVerify;
    @InjectView(R.id.rg_join_condition)
    protected RadioGroup mRgJoinCondition;
    @InjectView(R.id.edt_group_announce)
    protected EditText mEdtGroupBrief;
    @InjectView(R.id.btn_group)
    protected AppCompatButton mBtnGroup;

    private int mMode = 0; //0=add,1=edit
    private List<DiseaseClass.DisClassEntity> mGClass;
    private GroupList.GroupsEntity mEntity;

    public static void startAtyForCreate(Activity aty, int rcode) {
        Intent intent = new Intent(aty, EditGroupActivity.class);
        aty.startActivityForResult(intent, rcode);
    }

    public static void startAtyForEdit(Activity aty, int rcode, GroupList.GroupsEntity entity) {
        Intent intent = new Intent(aty, EditGroupActivity.class);
        intent.putExtra(ARG_ENTITY, entity);
        aty.startActivityForResult(intent, rcode);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_create_group);
    }

    @Override
    protected void init() {
        super.init();
        mEntity = getIntent().getParcelableExtra(ARG_ENTITY);
    }

    @Override
    protected void initView() {
        super.initView();
        if (mEntity == null) {
            setActionBar(R.string.create_group);
            mTvGroupVerify.setText(getString(R.string.allow_anybody_join));
        } else {
            mMode = 1;
            setActionBar(R.string.edit_group);
            mEdtGroupName.setText(mEntity.getName());
            List<String> arrs = Arrays.asList(getResources().getStringArray(R.array.group_identify));
            int perIndex = Integer.parseInt(mEntity.getPermission());
            if (0 <= perIndex && perIndex < arrs.size()) {
                mTvGroupVerify.setText(arrs.get(perIndex));
            }
            String strCls = "";
            List<String> cls = mEntity.getGroupclass();
            for (String s:cls) {strCls += s;}
            mTvGroupClassify.setText(strCls);
            mEdtGroupBrief.setText(mEntity.getDeclared());
            mBtnGroup.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        super.initData();
        onLoadGroupClass();
    }

    private void onLoadGroupClass() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<DiseaseClass>> call = service.getDisClass();
        call.enqueue(new RespHandler<DiseaseClass>() {
            @Override
            public void onSucceed(RespEntity<DiseaseClass> resp) {
                if (resp.getResponse_params() != null) {
                    mGClass = resp.getResponse_params().getData();
                }
            }

            @Override
            public void onFailed(RespEntity<DiseaseClass> resp) {
                onLoadResult(resp);
            }
        });
    }

    private boolean onCheckNull() {
        String gname = mEdtGroupName.getText().toString();
        if (TextUtil.isEmpty(gname)) {
            UIHelper.showToast("组名不能为空");
            return false;
        }
        String gclass = mTvGroupClassify.getText().toString();
        if (TextUtil.isEmpty(gclass)) {
            UIHelper.showToast("分组名不能为空");
            return false;
        }
        return true;
    }

    private Map<String, String> genCreateGroupParam() {
        Map<String, String> map = new HashMap<>();
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity == null) return null;
        map.put("uid", userEntity.getDuid());
        map.put("usertype", "0");
        map.put("groupname", mEdtGroupName.getText().toString());
        map.put("groupclass",mTvGroupClassify.getText().toString());
        map.put("permission",(String) mTvGroupVerify.getTag());
        map.put("declared", mEdtGroupBrief.getText().toString());
        return map;
    }

    private Map<String,String> genModifyGroupParam() {
        Map<String, String> param = new HashMap<>();
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity != null) {
            User.UserEntity.YtxsubaccountEntity account = userEntity.getYtxsubaccount();
            param.put("sid", account.getSubaccountsid());
            param.put("token", account.getSubtoken());
            param.put("groupId", mEntity.getGroupId());
            //param.put("owneruid", mEntity.getOwner());
            param.put("name", mEdtGroupName.getText().toString());
            param.put("permission", (String) mTvGroupVerify.getTag());
            param.put("declared", mEdtGroupBrief.getText().toString());
            param.put("groupclass", mTvGroupClassify.getText().toString());
        }
        return param;
    }

    private Map<String,String> genDissolveGroupParam() {
        Map<String, String> param = new HashMap<>();
        User.UserEntity userEntity = AppContext.context().getUser();
        if (userEntity != null) {
            User.UserEntity.YtxsubaccountEntity account = userEntity.getYtxsubaccount();
            param.put("sid", account.getSubaccountsid());
            param.put("token", account.getSubtoken());
            param.put("groupId", mEntity.getGroupId());
            //param.put("owneruid", mEntity.getOwner());
            param.put("owner", mEntity.getOwner());
        }
        return param;
    }

    private void onCreateGroup() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.createGroup(genCreateGroupParam());
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                dismissProcessDialog();
                RespEntity r = response.body();
                if (r != null) {
                    UIHelper.showToast(r.getError_msg());
                    onFinished(r);
                }
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                Log.d(AppConfig.TAG,"[EditGroupActivity->onCreateGroup->onFailure]" + t.toString());
                dismissProcessDialog();
            }
        });
    }

    private void onEditGroup() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.modifyGroup(genModifyGroupParam());
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                dismissProcessDialog();
                RespEntity r = response.body();
                if (r != null) {
                    UIHelper.showToast(r.getError_msg());
                    onFinished(r);
                }
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                Log.d(AppConfig.TAG,"[EditGroupActivity->onEditGroup->onFailure]" + t.toString());
                dismissProcessDialog();
            }
        });
    }


    private void onFinished(RespEntity r) {
        if (r != null && r.getError_code() == 0) {
            setResult(RESULT_OK);
            AppManager.getAppManager().finishActivity(this);
        }
    }

    private void onDissolveGroup() {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.deleteGroup(genDissolveGroupParam());
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                dismissProcessDialog();
                RespEntity r = response.body();
                if (r != null) {
                    UIHelper.showToast(r.getError_msg());
                    onFinished(r);
                }
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                Log.d(AppConfig.TAG,"[EditGroupActivity->onDissolveGroup->onFailure]" + t.toString());
                dismissProcessDialog();
            }
        });
    }

    /**保存*/
    @OnClick(R.id.tv_save)
    protected void onSave() {
        if (!onCheckNull() || isFastDoubleClick()) return;

        if(mMode == 0) {
            onCreateGroup();
        } else {
            onEditGroup();
        }
    }

    @OnClick(R.id.tv_identify)
    protected void onGroupIdentify() {
        final List<String> arrs = Arrays.asList(getResources().getStringArray(R.array.group_identify));
        DialogUtils.showBottomListDialog(this, arrs, new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        if (position != 2) {
                            mTvGroupVerify.setText(arrs.get(position));
                        }
                    }
                }, null);
    }

    @OnClick(R.id.edt_group_classify)
    protected void onSetGroupClass() {
        if (mGClass == null || mGClass.isEmpty()) return;
        final List<String> arrs = new ArrayList<>();
        for (DiseaseClass.DisClassEntity classEntity : mGClass) {
            arrs.add(classEntity.getName());
        }
        arrs.add(getString(R.string.cancel));
        DialogUtils.showBottomListDialog(this, arrs, new OnItemClickListener() {
            @Override
            public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                dialog.dismiss();
                if (position != arrs.size()-1) {
                    mTvGroupClassify.setText(arrs.get(position));
                    mTvGroupClassify.setTag(position);
                }
            }
        }, null);
    }

    @OnClick(R.id.btn_group)
    protected void onDissolve() {
        onDissolveGroup();
    }
}
