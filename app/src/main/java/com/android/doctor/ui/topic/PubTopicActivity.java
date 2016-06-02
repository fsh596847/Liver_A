package com.android.doctor.ui.topic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.FileUtils;
import com.android.doctor.helper.ImageHelper;
import com.android.doctor.helper.PermissionUtil;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.helper.camera.CameraProxy;
import com.android.doctor.helper.camera.CameraResult;
import com.android.doctor.model.Constants;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.ImageGridAdapter;
import com.android.doctor.ui.base.BaseActivity;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.yuntongxun.kitsdk.setting.ECPreferenceSettings;
import com.yuntongxun.kitsdk.setting.ECPreferences;
import com.yuntongxun.kitsdk.ui.ECImagePreviewActivity;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/4/6.
 */
public class PubTopicActivity extends BaseActivity implements CameraResult {

    @InjectView(R.id.tv_topic)
    protected TextView mTvTopic;
    @InjectView(R.id.edt_title)
    protected EditText mEdtTitle;
    @InjectView(R.id.edt_content)
    protected EditText mEdtContent;
    @InjectView(R.id.grid_image)
    protected GridView mImgGrid;
    private CameraProxy cameraProxy;
    private ImageGridAdapter mAdapter;
    private String mBarId, mBarName;

    public static void startAtyForCreate(Activity aty, String barId, String barName) {
        Intent intent = new Intent(aty, PubTopicActivity.class);
        intent.putExtra("barid", barId);
        intent.putExtra("barname", barName);
        aty.startActivityForResult(intent, Constants.REQ_CODE_FOR_CREATE);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_pub_topic);
    }

    @Override
    protected void init() {
        super.init();
        Intent intent = getIntent();
        mBarId = intent.getStringExtra("barid");
        mBarName = intent.getStringExtra("barname");
    }

    @Override
    protected void initView() {
        setActionBar(R.string.public_topic);
        cameraProxy = new CameraProxy(this, this);
        mAdapter = new ImageGridAdapter(this);
        mImgGrid = (GridView) findViewById(R.id.grid_image);
        mImgGrid.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mImgGrid.setAdapter(mAdapter);
        mImgGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 + 1 == mAdapter.getCount()) {
                    //new PopupWindows(PublishedActivity.this, noScrollgridview);
                    addPic();
                } else {
                    proPic(arg2, arg3);
                }
            }
        });
        mTvTopic.setText(mBarName);
    }

    protected void addPic() {
        if (PermissionUtil.checkPermission(this, Manifest.permission.CAMERA) &&
                PermissionUtil.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            showAddPicWayDialog();
        } else {
            DialogUtils.getMessageDialog(this, "请打开照片访问权限").show();
        }
    }

    private void proPic(int pos, long viewId) {
        /*if (viewId == R.id.tv_delete) {
            mAdapter.removeItem(pos);
        } else*/ {
            try {
                ECPreferences.savePreference(ECPreferenceSettings.SETTINGS_CROPIMAGE_OUTPUTPATH, (String) mAdapter.getItem(pos), true);
                Intent intent = new Intent(this, ECImagePreviewActivity.class);
                startActivity(intent);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showAddPicWayDialog() {
        DialogUtils.showBottomListDialog(this, Arrays.asList(getResources().getStringArray(R.array.add_picture_way)),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(DialogPlus dialog, Object item, View view, int position) {
                        dialog.dismiss();
                        switch (position) {
                            case 0 ://
                                cameraProxy.getPhoto2Camera(FileUtils.getSDCardPath());
                                break;
                            case 1: //
                                cameraProxy.getPhoto2Album(FileUtils.getSDCardPath());
                                break;
                            default:
                                break;
                        }
                    }
                }, null);
    }

    @Override
    public void onSuccess(String path) {
        Log.d(AppConfig.TAG, "PubTopicActivity-> select image onSuccess: " + path);
        //insertPic(bitmap, path);
        mAdapter.addItem(path);
    }

    @Override
    public void onFail(String path) {

    }

    @Override
    public void onActivityResult(final int requestCode,
                                 final int resultCode,
                                 final Intent imageReturnIntent) {
        if (resultCode != Activity.RESULT_OK)
            return;
        cameraProxy.onResult(requestCode, resultCode, imageReturnIntent);
    }

    private Map<String, RequestBody> getPubTopicParam() {
        String title = mEdtTitle.getText().toString();
        if (TextUtils.isEmpty(title)) {
            UIHelper.showToast("请输入标题");
            return null;
        }
        MediaType txMediaType = MediaType.parse("text/plain");
        User.UserEntity u = AppContext.context().getUser();
        Map<String,RequestBody> map = new HashMap<>();
        map.put("uid", RequestBody.create(txMediaType,u.getDuid()));
        map.put("title", RequestBody.create(txMediaType,title));
        map.put("content", RequestBody.create(txMediaType,mEdtContent.getText().toString()));
        map.put("topicbarid", RequestBody.create(txMediaType,mBarId));
        map.put("usertype", RequestBody.create(txMediaType,"0"));
        map.put("createnickname", RequestBody.create(txMediaType,u.getNickname()));

        List<String> paths = mAdapter.getData();
        if (paths != null) {
            MediaType imgMediaType = MediaType.parse("image/*");
            for (int i = 0; i < paths.size(); ++i) {
                String path = paths.get(i);
                String fileName = FileUtils.getFileName(path);
                map.put("files\"; filename=\""+ fileName +"\"", RequestBody.create(imgMediaType, new File(path)));
            }
        }
        return map;
    }

    @OnClick(R.id.tv_pub)
    protected void onPub() {
        Map<String,RequestBody> param = getPubTopicParam();
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.pubTopic(param);
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                dismissProcessDialog();
                RespEntity r = response.body();
                if (r != null) {
                    UIHelper.showToast(r.getError_msg());
                    onProResult(r);
                    setResult(RESULT_OK);
                }
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                dismissProcessDialog();
                UIHelper.showToast("发布失败");
            }
        });
    }
}
