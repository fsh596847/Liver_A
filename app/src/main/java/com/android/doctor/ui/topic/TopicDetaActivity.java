package com.android.doctor.ui.topic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.FileUtils;
import com.android.doctor.helper.ImageHelper;
import com.android.doctor.helper.PermissionUtil;
import com.android.doctor.helper.camera.CameraProxy;
import com.android.doctor.helper.camera.CameraResult;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.STopicEntity;
import com.android.doctor.model.TopicList;
import com.android.doctor.model.TopicReplyList;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RespHandler;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.adapter.ImageGridAdapter;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.ui.viewholder.TopicDetaHeaderViewHolder;
import com.android.doctor.ui.viewholder.TopicReplyViewHolder;
import com.android.doctor.ui.widget.EmptyLayout;
import com.android.doctor.ui.widget.treeview.model.TreeNode;
import com.android.doctor.ui.widget.treeview.view.AndroidTreeView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.yuntongxun.kitsdk.setting.ECPreferenceSettings;
import com.yuntongxun.kitsdk.setting.ECPreferences;
import com.yuntongxun.kitsdk.ui.ECImagePreviewActivity;
import com.yuntongxun.kitsdk.ui.chatting.view.CCPChattingFooter2;
import com.yuntongxun.kitsdk.ui.chatting.view.SmileyPanel;
import com.yuntongxun.kitsdk.utils.FileAccessor;
import com.yuntongxun.kitsdk.utils.LogUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by Yong on 2016/4/6.
 */
public class TopicDetaActivity extends BaseActivity implements TopicReplyViewHolder.OnReplyContentClickListener, CameraResult {

    @InjectView(R.id.container)
    protected ViewGroup containerView;
    private AndroidTreeView tView;
    private String mTpId;
    private TreeNode root = TreeNode.root();
    private CCPChattingFooter2 mChattingFooter;
    private OnOnChattingPanelImpl mChattingPanelImpl = new OnOnChattingPanelImpl();
    private OnInputFooterListener mChattingFooterImpl = new OnInputFooterListener();
    private FrameLayout mChattingBottomPanel;
    private boolean isBaseViewLoad = false;
    private boolean isReplyViewLoad = false;
    private TopicList.TopicEntity mBaseTopic;
    private List<TopicReplyList.TopicRepliesEntity> mRepList;
    private View mContentView;

    protected GridView mChatFooterAttach;
    private CameraProxy cameraProxy;
    private ImageGridAdapter mAdapter;

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_topic_detail);
    }

    public static void startAty(Context ctx, String tpid) {
        Intent intent = new Intent(ctx, TopicDetaActivity.class);
        intent.putExtra("tpid", tpid);
        ctx.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mChattingFooter != null){
            mChattingFooter.onDestory();
            mChattingFooter = null;
        }
    }

    @Override
    protected void init() {
        super.init();
        mTpId = getIntent().getStringExtra("tpid");
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(R.string.topic);
        mChattingFooter = (CCPChattingFooter2) findViewById(R.id.nav_footer);
        // 注册聊天面板状态回调通知、包含录音按钮按钮下放开等录音操作
        mChattingFooter.setOnChattingFooterLinstener(mChattingFooterImpl);
        // 注册聊天面板附加功能（图片、拍照、文件）被点击回调通知
        mChattingFooter.setOnChattingPanelClickListener(mChattingPanelImpl);
        mChattingFooter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {}
        });
        mChattingFooter.findViewById(R.id.chatting_mode_btn).setVisibility(View.GONE);
        mContentView = findViewById(R.id.ll_content);
        mContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideBottom();
                return false;
            }
        });
        mChattingFooter.findViewById(com.yuntongxun.eckitsdk.R.id.chatting_attach_btn).setOnClickListener(mAttachClickListener);
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
        mChattingBottomPanel = (FrameLayout) mChattingFooter.findViewById(com.yuntongxun.eckitsdk.R.id.chatting_bottom_panel);
        mChattingBottomPanel.addView(mChatFooterAttach, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        mChattingFooter.switchChattingPanel(SmileyPanel.APP_PANEL_NAME_DEFAULT);
        mChattingFooter.initSmileyPanel();
        mChattingFooter.setVisibility(View.GONE);
        //initTreeView();
    }

    @Override
    protected void initData() {
        super.initData();
        onLoadTopicInfo();
        onLoadTpcReplyList();
    }

    private void initTreeView() {
        tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        //tView.setDefaultViewHolder(TopicReplyViewHolder.class);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        //tView.collapseAll();
        tView.setUse2dScroll(false);
        containerView.addView(tView.getView());
        tView.expandAll();
    }


    private void setTpcViewData(TopicList.TopicEntity tpc) {
        this.mBaseTopic = tpc;
        isBaseViewLoad = true;
        new TopicDetaHeaderViewHolder(this).initView(findViewById(R.id.include_tpheader),tpc);
        //TreeNode tr = new TreeNode(tpc);
        //tr.setViewHolder(new TopicDetaHeaderViewHolder(this));
        //root.addChild(tr);
        updateView();
    }

    private void setSubReplyViewData(TreeNode trNode, List<TopicReplyList.TopicRepliesEntity> subRepList) {
        if (subRepList == null || subRepList.isEmpty()) return;
        for (TopicReplyList.TopicRepliesEntity reply: subRepList) {
            TreeNode subNode = new TreeNode(reply);
            TopicReplyViewHolder viewHolder = new TopicReplyViewHolder(this);
            viewHolder.setItemClickListener(this);
            subNode.setViewHolder(viewHolder);
            setSubReplyViewData(subNode, reply.getReplies());
            trNode.addChild(subNode);
        }
    }

    private void setReplyViewData(List<TopicReplyList.TopicRepliesEntity> repList) {

        if (repList != null) {
            for (int i = 0; i < repList.size(); ++i) {
                TopicReplyList.TopicRepliesEntity rep = repList.get(i);
                //TopicReplyList.TopicRepliesEntity reply= new TopicReplyList.RepliesEntity(rep);
                TreeNode tr = new TreeNode(rep);
                TopicReplyViewHolder viewHolder = new TopicReplyViewHolder(this);
                viewHolder.setItemClickListener(this);
                tr.setViewHolder(viewHolder);
                root.addChild(tr);
                setSubReplyViewData(tr, rep.getReplies());
            }
        }
        mRepList = repList;
        isReplyViewLoad = true;
        updateView();
    }

    private void updateView() {
        if (isBaseViewLoad && isReplyViewLoad) {
            initTreeView();
            setMaskLayout(View.GONE, EmptyLayout.HIDE_LAYOUT, "");
        }
    }

    private void onLoadTopicInfo() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<STopicEntity>> call = service.getTopicById(mTpId);
        call.enqueue(new RespHandler<STopicEntity>() {
            @Override
            public void onSucceed(RespEntity<STopicEntity> resp) {
                if (resp.getResponse_params() != null) {
                    setTpcViewData(resp.getResponse_params().getTopic());
                }
            }

            @Override
            public void onFailed(RespEntity<STopicEntity> resp) {

            }
        });
    }

    private void onLoadTpcReplyList() {
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<TopicReplyList>> call = service.getTopicReplyList(mTpId);
        call.enqueue(new RespHandler<TopicReplyList>() {
            @Override
            public void onSucceed(RespEntity<TopicReplyList> resp) {
                if (resp.getResponse_params() != null) {
                    setReplyViewData(resp.getResponse_params().getTopicreplies());
                }
            }

            @Override
            public void onFailed(RespEntity<TopicReplyList> resp) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            LogUtil.d(AppConfig.TAG, "keycode back , chatfooter mode: " +  mChattingFooter.getMode());
            if(!mChattingFooter.isButtomPanelNotVisibility()) {
                hideBottom();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void hideBottom() {
        // 隐藏键盘
        DeviceHelper.hideSoftKeyboard(getCurrentFocus());

        if(mChattingFooter != null) {
            // 隐藏更多的聊天功能面板
            mChattingFooter.hideBottomPanel();
        }
    }

    private Map<String, String> getReplyParam(TopicReplyList.TopicRepliesEntity obj, String content, int replyStyle) {
        if (obj == null) return null;
        User.UserEntity user = AppContext.context().getUser();
        Map<String, String> map = new HashMap<>();
        map.put("topicbarid", obj.getTopicbarid());
        map.put("topicid", obj.getTopicid());
        map.put("replyid", obj.getReplyid());
        map.put("usertype", "0");
        map.put("replystyle", "" + replyStyle);
        map.put("uid", user.getDuid());
        map.put("replynickname", obj.getReplynickname());
        map.put("content", content);
        map.put("attachstyle", "0");
        return map;
    }

    private Map<String, String> getReplyParam(TopicList.TopicEntity obj, String content, int replyStyle) {
        if (obj == null) return null;
        User.UserEntity user = AppContext.context().getUser();
        Map<String, String> map = new HashMap<>();
        map.put("topicbarid", obj.getTopicbarid());
        map.put("topicid", obj.getTopicid());
        map.put("usertype", "0");
        map.put("replystyle", "" + replyStyle);
        map.put("uid", user.getDuid());
        map.put("replynickname", user.getNickname());
        map.put("content", content);
        map.put("attachstyle", "0");
        return map;
    }

    private void onPubReply( Map<String, String> param) {
        if (param == null) return;
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity> call = service.replyTopic(param);
        call.enqueue(new RespHandler() {
            @Override
            public void onSucceed(RespEntity resp) {
                //onResult(resp);
            }

            @Override
            public void onFailed(RespEntity resp) {
                //dismissProcessDialog();
            }
        });
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
        Log.e(AppConfig.TAG, "PubTopicActivity-> select image onSuccess: " + path);
        Bitmap bitmap = ImageHelper.getBitmapByPath(path);
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

    private Object mReplyTarget;

    @OnClick(R.id.img_pub)
    protected void onPubTopicReply() {
        mReplyTarget = mBaseTopic;
        switchFooterView();
    }

    private void switchFooterView() {
        if (mChattingFooter.getVisibility() == View.GONE) {
            mChattingFooter.setVisibility(View.VISIBLE);
        } else {
            mChattingFooter.setVisibility(View.GONE);
        }
    }


    @Override
    public void onContentClick(View v, Object obj) {
        Log.d(AppConfig.TAG, "TopicDetaActivity-> onContentClick");
        mReplyTarget = obj;
        switch (v.getId()) {
            case R.id.tv_oper1:
                break;
            case R.id.tv_oper2:
                if (mReplyTarget != null && mReplyTarget.getClass().equals(TopicReplyList.TopicRepliesEntity.class)) {
                    UserProfileActivity.startAty(this, ""+((TopicReplyList.TopicRepliesEntity) mReplyTarget).getReplyuid());
                }
                break;
            case R.id.tv_oper3:
                switchFooterView();
                break;
            case R.id.tv_oper4:
                break;
        }
    }

    private void handleSendReply(String text) {
        if (mReplyTarget == null )return;
        if (mReplyTarget.getClass().equals(TopicList.TopicEntity.class)) {
            onPubReply(getReplyParam((TopicList.TopicEntity) mReplyTarget, text, 0));
        } else if (mReplyTarget.getClass().equals(TopicReplyList.TopicRepliesEntity.class)) {
            onPubReply(getReplyParam((TopicReplyList.TopicRepliesEntity) mReplyTarget, text, 1));
        }
    }

    private void handleTackPicture() {
        if(!FileAccessor.isExistExternalStore()) {
            return;
        }

    }

    private class OnOnChattingPanelImpl implements CCPChattingFooter2.OnChattingPanelClickListener {

        @Override
        public void OnTakingPictureRequest() {
            //handleTackPicture();
            hideBottomPanel();
        }

        @Override
        public void OnSelectImageReuqest() {
            //handleSelectImageIntent();
            hideBottomPanel();
        }

        @Override
        public void OnSelectFileRequest() {
            //startActivityForResult(new Intent(TopicDetaActivity.this, ECFileExplorerActivity.class), 0x2a);
            hideBottomPanel();
        }

        private void hideBottomPanel() {
            mChattingFooter.hideBottomPanel();
        }

    }

    private class OnInputFooterListener extends OnEditTextFooterImpl {
        @Override
        public void OnSendTextMessageRequest(CharSequence text) {
            super.OnSendTextMessageRequest(text);
            if(text == null) {
                return ;
            }
            Log.d(AppConfig.TAG, "[OnInputFooterListener-> OnSendTextMessageRequest] " + text);
            handleSendReply(text.toString());
        }
    }

    final private View.OnClickListener mAttachClickListener
            = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mChattingFooter.isButtomPanelNotVisibility()) {
                DeviceHelper.hideSoftKeyboard(v);
                mChattingBottomPanel.setVisibility(View.VISIBLE);
                //mAppPanel.refreshAppPanel();
            } else {
                //setMode(CHATTING_MODE_VOICE, 22, true);
                if(mChatFooterAttach.getVisibility() == View.VISIBLE) {
                    mChatFooterAttach.setVisibility(View.GONE);
                } else {
                    mChatFooterAttach.setVisibility(View.VISIBLE);
                }
            }
        }
    };
}
