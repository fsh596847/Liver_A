package com.android.doctor.ui.topic;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.DeviceHelper;
import com.android.doctor.helper.DialogUtils;
import com.android.doctor.helper.FileUtils;
import com.android.doctor.helper.HandlerHelper;
import com.android.doctor.helper.ImageHelper;
import com.android.doctor.helper.PermissionUtil;
import com.android.doctor.helper.UIHelper;
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
import com.android.doctor.ui.viewholder.SelectableItemHolder;
import com.android.doctor.ui.viewholder.TopicDetaHeaderViewHolder;
import com.android.doctor.ui.viewholder.TopicReplyViewHolder;
import com.android.doctor.ui.widget.EmptyLayout;
import com.android.doctor.ui.widget.CustChattingFooter;
import com.android.doctor.ui.widget.treeview.model.TreeNode;
import com.android.doctor.ui.widget.treeview.view.AndroidTreeView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.yuntongxun.kitsdk.setting.ECPreferenceSettings;
import com.yuntongxun.kitsdk.setting.ECPreferences;
import com.yuntongxun.kitsdk.ui.ECImagePreviewActivity;
import com.yuntongxun.kitsdk.utils.EmoticonUtil;
import com.yuntongxun.kitsdk.utils.FileAccessor;
import com.yuntongxun.kitsdk.utils.LogUtil;

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

/**
 * Created by Yong on 2016/4/6.
 */
public class TopicDetaActivity extends BaseActivity implements TopicReplyViewHolder.OnReplyContentClickListener, CameraResult {

    @InjectView(R.id.container)
    protected ViewGroup containerView;
    //@InjectView(R.id.scrollview)
    //protected ScrollView mScrollView;

    private AndroidTreeView tView;
    private String mTpId;
    private TreeNode root;

    @InjectView(R.id.chat_footer)
    protected CustChattingFooter mChatFooter;
    private OnInputFooterListener mChattingFooterImpl = new OnInputFooterListener();
    private boolean isBaseViewLoad = false;
    private boolean isReplyViewLoad = false;
    private TopicList.TopicEntity mBaseTopic;

    private View mContentView;

    protected GridView mChatFooterAttach;
    private CameraProxy cameraProxy;
    private ImageGridAdapter mAdapter;

    private HandlerHelper mHandlerHelp = new HandlerHelper();
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


    private void doEmojiPanel() {
        if(EmoticonUtil.getEmojiSize() == 0) {
            EmoticonUtil.initEmoji();
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
        /*mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideBottom();
                return false;
            }
        });*/
        initFooterView();
        //initTreeView();
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
        mHandlerHelp.postRunnOnThead(new Runnable() {
            @Override
            public void run() {
                doEmojiPanel();
                Log.d(AppConfig.TAG, "HandlerThread-> run");
            }
        });
        onLoadData();
    }

    private void onLoadData() {
        containerView.removeAllViews();
        root = TreeNode.root();
        onLoadTopicInfo();
        onLoadTpcReplyList();
    }

    private void setReplyFragment(List<TopicReplyList.TopicRepliesEntity> list) {
        FragmentTopicReply fragment = new FragmentTopicReply();
        fragment.setRepList(list);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction trans = fm.beginTransaction();
        trans.replace(R.id.container, fragment);
        trans.commitAllowingStateLoss();
    }

    private void initTreeView() {
       /* TreeNode baseNode= new TreeNode(mBaseTopic).setViewHolder(new TopicDetaHeaderViewHolder(this));
        fillFolder(baseNode);
        root.addChildren(baseNode);*/

        tView = new AndroidTreeView(this, root);
        tView.setDefaultAnimation(true);
        tView.setDefaultViewHolder(TopicReplyViewHolder.class);
        tView.setDefaultContainerStyle(R.style.TreeNodeStyleDivided, true);
        //
        tView.setUse2dScroll(false);
        final View view = tView.getView(R.style.TreeNodeStyleDivided);
        tView.expandAll();
        HandlerHelper.postRunnOnUI(new Runnable() {
            @Override
            public void run() {
                containerView.addView(view);
                List<TreeNode> childs = root.getChildren();
                if (childs != null) {
                    for (TreeNode treeNode : childs) {
                       // tView.toggleNode(treeNode);
                        //treeNode.getViewHolder().getView().performClick();
                    }
                }

                setMaskLayout(View.GONE, EmptyLayout.HIDE_LAYOUT, "");
            }
        });
    }

    private void fillFolder(TreeNode folder) {
        TreeNode currentNode = folder;
        for (int i = 0; i < 10; i++) {
            TreeNode baseNode= new TreeNode("Folder with very long name ");
            baseNode.setViewHolder(new SelectableItemHolder(this));
            currentNode.addChild(baseNode);
            currentNode = baseNode;
        }
    }

    private void expandView(TreeNode node) {
        List<TreeNode> childs = node.getChildren();
        if (childs != null) {
            for (TreeNode treeNode : childs) {
                //tView.expandNode(treeNode, true);
                //expandView(treeNode);
                treeNode.getViewHolder().toggle(true);
            }
        }
    }

    private void setTpcViewData(TopicList.TopicEntity tpc) {
        this.mBaseTopic = tpc;
        isBaseViewLoad = true;
        new TopicDetaHeaderViewHolder(this).initView(findViewById(R.id.include_tpheader),tpc);
        //baseNode= new TreeNode(tpc);
        //baseNode.setViewHolder(new TopicDetaHeaderViewHolder(this));
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
                TreeNode replyNode = new TreeNode(rep);
                TopicReplyViewHolder viewHolder = new TopicReplyViewHolder(this);
                viewHolder.setItemClickListener(this);
                replyNode.setViewHolder(viewHolder);
                root.addChildren(replyNode);
                setSubReplyViewData(replyNode, rep.getReplies());
            }
        }

        isReplyViewLoad = true;
        updateView();
    }

    private void updateView() {
        if (isBaseViewLoad && isReplyViewLoad) {
            initTreeView();
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
            public void onSucceed(final RespEntity<TopicReplyList> resp) {
                if (resp.getResponse_params() != null) {
                     //setMaskLayout(View.GONE, EmptyLayout.HIDE_LAYOUT, "");
                     //setReplyFragment(resp.getResponse_params().getTopicreplies());
                    // setReplyViewData(resp.getResponse_params().getTopicreplies());
                    mHandlerHelp.postRunnOnThead(new Runnable() {
                        @Override
                        public void run() {
                            setReplyViewData(resp.getResponse_params().getTopicreplies());
                        }
                    });
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
            LogUtil.d(AppConfig.TAG, "keycode back , chatfooter mode: " +  mChatFooter.getMode());
            if(!mChatFooter.isButtomPanelNotVisibility()) {
                hideBottom();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void hideBottom() {
        // 隐藏键盘
        DeviceHelper.hideSoftKeyboard(getCurrentFocus());

        if(mChatFooter != null) {
            // 隐藏更多的聊天功能面板
            mChatFooter.hideBottomPanel();
        }
    }

    private Map<String, RequestBody> getReplyParam(TopicReplyList.TopicRepliesEntity obj, String content, int replyStyle) {
        if (obj == null) return null;
        User.UserEntity user = AppContext.context().getUser();
        Map<String, RequestBody> map = new HashMap<>();
        MediaType txMediaType = MediaType.parse("text");
        map.put("topicbarid", RequestBody.create(txMediaType,obj.getTopicbarid()));
        map.put("topicid", RequestBody.create(txMediaType,obj.getTopicid()));
        map.put("replyid", RequestBody.create(txMediaType,obj.getReplyid()));
        map.put("usertype", RequestBody.create(txMediaType,"0"));
        map.put("replystyle", RequestBody.create(txMediaType,"" + replyStyle));
        map.put("uid", RequestBody.create(txMediaType,user.getDuid()));
        map.put("replynickname", RequestBody.create(txMediaType,obj.getReplynickname()));
        map.put("content", RequestBody.create(txMediaType,content));
        map.put("attachstyle", RequestBody.create(txMediaType,"0"));

        List<String> paths = mAdapter.getData();
        if (paths != null) {
            MediaType imgMediaType = MediaType.parse("image");
            for (int i = 0; i < paths.size(); ++i) {
                map.put("image_" + i, RequestBody.create(imgMediaType, new File(paths.get(i))));
            }
        }
        return map;
    }

    private Map<String, RequestBody> getReplyParam(TopicList.TopicEntity obj, String content, int replyStyle) {
        if (obj == null) return null;
        User.UserEntity user = AppContext.context().getUser();
        Map<String, RequestBody> map = new HashMap<>();
        MediaType txMediaType = MediaType.parse("text");
        map.put("topicbarid", RequestBody.create(txMediaType,obj.getTopicbarid()));
        map.put("topicid", RequestBody.create(txMediaType,obj.getTopicid()));
        map.put("usertype", RequestBody.create(txMediaType,"0"));
        map.put("replystyle", RequestBody.create(txMediaType, "" + replyStyle));
        map.put("uid", RequestBody.create(txMediaType,user.getDuid()));
        map.put("replynickname", RequestBody.create(txMediaType,user.getNickname()));
        map.put("content", RequestBody.create(txMediaType,content));
        map.put("attachstyle", RequestBody.create(txMediaType,"0"));

        List<String> paths = mAdapter.getData();
        if (paths != null) {
            MediaType imgMediaType = MediaType.parse("image");
            for (int i = 0; i < paths.size(); ++i) {
                map.put("image_" + i, RequestBody.create(imgMediaType, new File(paths.get(i))));
            }
        }
        return map;
    }

    private void onPubReply( Map<String, RequestBody> param) {
        mChatFooter.setVisibility(View.GONE);
        if (param == null) return;
        showProcessDialog();
        ApiService service = RestClient.createService(ApiService.class);
        Call<RespEntity<Object>> call = service.replyTopic(param);
        call.enqueue(new RespHandler<Object>() {
            @Override
            public void onSucceed(RespEntity<Object> resp) {
                onPubReplyResult(resp);
            }

            @Override
            public void onFailed(RespEntity<Object> resp) {
                onPubReplyResult(resp);
            }
        });
    }

    private void onPubReplyResult(RespEntity<Object> resp) {
        dismissProcessDialog();
        if (resp != null) {
            String text = resp.getError_msg();
            UIHelper.showToast(text);
            if (resp.getError_code() == 0) {
                onLoadData();
            }
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
        if (mChatFooter.getVisibility() == View.GONE) {
            mChatFooter.setVisibility(View.VISIBLE);
        } else {
            mChatFooter.setVisibility(View.GONE);
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

    }


    private class OnInputFooterListener implements CustChattingFooter.OnChattingFooterLinstener {

        @Override
        public void OnInEditMode() {
            //fragmentAskReplyList.scrollListViewToLast();
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
            handleSendReply(text.toString());
        }
    }
}
