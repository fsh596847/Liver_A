package com.android.doctor.ui.mine;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.FileUtils;
import com.android.doctor.helper.ImageHelper;
import com.android.doctor.helper.PermissionUtil;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.helper.camera.CameraProxy;
import com.android.doctor.helper.camera.CameraResult;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.ImageGridAdapter;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.topic.OnEditTextFooterImpl;
import com.android.doctor.ui.widget.TIChattingFooter;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.yuntongxun.kitsdk.setting.ECPreferenceSettings;
import com.yuntongxun.kitsdk.setting.ECPreferences;
import com.yuntongxun.kitsdk.ui.ECImagePreviewActivity;
import com.yuntongxun.kitsdk.ui.chatting.view.SmileyPanel;
import com.yuntongxun.kitsdk.utils.EmoticonUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/5/19.
 */
public class AskReplyListActivity extends BaseActivity implements CameraResult {
    @InjectView(R.id.chat_footer)
    protected TIChattingFooter mChatFooter;
    private OnInputFooterListener mChattingFooterImpl = new OnInputFooterListener();
    protected GridView mChatFooterAttach;
    private CameraProxy cameraProxy;
    private ImageGridAdapter mAdapter;

    private Handler mHandler;
    private Looper mLooper;
    private FragmentAskReplyList fragmentAskReplyList;

    private String mAskId;

    public static void startAty(Context c, String askid) {
        Intent intent = new Intent(c, AskReplyListActivity.class);
        intent.putExtra("ask_id", askid);
        c.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_ask_reply);
    }

    @Override
    protected void initView() {
        super.initView();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        mAskId = getIntent().getStringExtra("ask_id");
        fragmentAskReplyList = FragmentAskReplyList.newInstance(mAskId);
        trans.replace(R.id.fl_container, fragmentAskReplyList);
        trans.commit();
        setActionBar(R.string.ask_deta);

        initFooterView();
        fragmentAskReplyList.setTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                DeviceHelper.hideSoftKeyboard(v);
                if (mChatFooter != null) {
                    mChatFooter.hideBottomPanel();
                }
                return false;
            }
        });
    }

    private void initFooterView() {
        mChatFooter.setOnChattingFooterLinstener(mChattingFooterImpl);
        mChatFooter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });

        View view = LayoutInflater.from(this).inflate(R.layout.gridview_image, null);
        cameraProxy = new CameraProxy(this, this);
        mAdapter = new ImageGridAdapter(this);
        mChatFooterAttach = (GridView) view.findViewById(R.id.grid_image);
        mChatFooterAttach.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mChatFooterAttach.setAdapter(mAdapter);
        mChatFooterAttach.setOnItemClickListener(new AdapterView.OnItemClickListener() {

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

        mChatFooter.setAttachPanel(mChatFooterAttach);
    }

    @Override
    protected void initData() {
        super.initData();
        HandlerThread thread = new HandlerThread("REPLY_ACTIVITY",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mLooper = thread.getLooper();
        mHandler = new Handler(mLooper);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                doEmojiPanel();
                Log.d(AppConfig.TAG, "HandlerThread-> run");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mChatFooter != null){
            mChatFooter.onDestory();
            mChatFooter = null;
        }
        if(mChattingFooterImpl != null) {
            mChattingFooterImpl.release();
            mChattingFooterImpl = null;
        }

        if(mLooper != null) {
            mLooper.quit();
            mLooper = null;
        }

        if(mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
    }

    private void doEmojiPanel() {
        if(EmoticonUtil.getEmojiSize() == 0) {
            EmoticonUtil.initEmoji();
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;
        cameraProxy.onResult(requestCode, resultCode, data);
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
        Log.e(AppConfig.TAG, "PubTopicActivity-> select image onSuccess: " + path);
        Bitmap bitmap = ImageHelper.getBitmapByPath(path);
        //insertPic(bitmap, path);
        mAdapter.addItem(path);
    }

    @Override
    public void onFail(String path) {
        Log.e(AppConfig.TAG, "PubTopicActivity-> select image failed: " + path);
    }

    private Map<String, String> getReplyParam(String content) {

        User.UserEntity u = AppContext.context().getUser();
        Map<String,String> map = new HashMap<>();
        map.put("askid", mAskId);
        map.put("replyuid", u.getDuid());
        map.put("replynickname", u.getNickname());
        map.put("replyrote", "0");
        map.put("content", content);

        /*List<String> paths = mAdapter.getData();
        if (paths != null) {
            for (int i = 0; i < paths.size(); ++i) {
                map.put("" + i, ImageHelper.getBitmapByPath(paths.get(i)));
            }
        }*/
        return map;
    }

    private void sendReply(String text) {
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.askReply(getReplyParam(text));
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                dismissProcessDialog();
                RespEntity r = response.body();
                if (r != null) {
                    UIHelper.showToast(r.getError_msg());
                    onProResult(r);
                }
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                dismissProcessDialog();
            }
        });
    }

    private class OnInputFooterListener implements TIChattingFooter.OnChattingFooterLinstener {

        @Override
        public void OnInEditMode() {
            fragmentAskReplyList.scrollListViewToLast();
        }

        @Override
        public void onPause() {}

        @Override
        public void release() {}

        @Override
        public void OnSendTextMessageRequest(CharSequence text) {
            if(text == null) {
                return ;
            }
            Log.d(AppConfig.TAG, "[OnInputFooterListener-> OnSendTextMessageRequest] " + text);
            sendReply(text.toString());
        }
    }
}
