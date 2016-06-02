/*
 *  Copyright (c) 2013 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.android.doctor.ui.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.doctor.R;
import com.yuntongxun.kitsdk.setting.ECPreferenceSettings;
import com.yuntongxun.kitsdk.setting.ECPreferences;
import com.yuntongxun.kitsdk.ui.chatting.view.AppPanel;
import com.yuntongxun.kitsdk.ui.chatting.view.ChatFooterPanel;
import com.yuntongxun.kitsdk.ui.chatting.view.EmojiGrid;
import com.yuntongxun.kitsdk.ui.chatting.view.SmileyPanel;
import com.yuntongxun.kitsdk.utils.LogUtil;
import com.yuntongxun.kitsdk.utils.ResourceHelper;
import com.yuntongxun.kitsdk.view.CCPEditText;

import java.io.File;


/**
 * @author Jorstin Chan@容联•云通讯
 * @date 2014-12-10
 * @version 4.0
 */
public class CustChattingFooter extends LinearLayout {


    private static final String TAG = LogUtil.getLogUtilsTag(CustChattingFooter.class);
    private static final int WHAT_ON_DIMISS_DIALOG = 0x1;

    // cancel recording sliding distance field.
    private static final int CANCLE_DANSTANCE = 60;
    /**
     * Chatting mode that input mode
     */
    public static final int CHATTING_MODE_KEYBORD = 1;

    /**
     * Chatting mode that Speech mode, voice record.
     */
    public static final int CHATTING_MODE_ATTACH = 2;

    private InputMethodManager mInputMethodManager;

    private View mChattingFooterView;

    private ViewGroup mChatFooterAttachPanel;
//    private LinearLayout mTextPanel;

    private FrameLayout mChattingBottomPanel;

    private ImageButton mChattingAttach;

    /**
     * The emoji send button in panel.
     */
    private ImageButton mBiaoqing;

    private Chronometer mChronometer;

    private Button mChattingSend;

    private CCPEditText mEditText;

    /**
     * Panel tha display emoji.
     */
    private ChatFooterPanel mChatFooterEmojiPanel;

    /**
     * Interface definition for a callback to be invoked
     * when the {@link ChatFooterPanel} has been click
     */
    private OnChattingFooterLinstener mChattingFooterLinstener;

    private OnChattingPanelClickListener mChattingPanelClickListener;

    /**
     * Interface definition for a callback to be invoked
     * when Input Text
     */
    private ChatingInputTextWatcher mChatingInputTextWatcher;

    /**
     * Do not enable the enter button to send the message
     */
    private boolean mDonotEnableEnterkey;

    /**
     * Whether to display the keyboard
     */
    private boolean mShowKeyBord;

    /**
     * Whether to display emoji panel.
     */
    private boolean mBiaoqingEnabled;


    /**
     * @see #CHATTING_MODE_KEYBORD
     * @see
     */
    private int mChattingMode;

    /**
     *
     */
    private int mTop;


    final private TextView.OnEditorActionListener mOnEditorActionListener
            = new TextView.OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            if(((actionId == EditorInfo.IME_NULL) && mDonotEnableEnterkey) || actionId == EditorInfo.IME_ACTION_SEND) {
                mChattingSend.performClick();
                return true;
            }
            return false;
        }
    };

    final private OnTouchListener mOnTouchListener
            = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            hideBottomPanel();
            if(mChattingFooterLinstener != null) {
                mChattingFooterLinstener.OnInEditMode();
            }
            return false;
        }

    };

    long currentTimeMillis = 0;

    final private OnClickListener mChattingSendClickListener
            = new OnClickListener() {

        @Override
        public void onClick(View v) {
            LogUtil.d(TAG, "send msg onClick");
            String message = mEditText.getText().toString();

            if((message.trim().length() == 0) && message.length() != 0) {
                LogUtil.d(TAG, "empty message cant be sent");
                return;
            }

            // send.
            if(mChattingFooterLinstener != null) {
                mChattingFooterLinstener.OnSendTextMessageRequest(message);
            }

            mEditText.clearComposingText();
            mEditText.setText("");
        }
    };


    final private OnClickListener mChattingSmileyClickListener
            = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if(isButtomPanelNotVisibility()) {

                hideInputMethod();
                //setMode(0, -1, true);

                if(mChatFooterEmojiPanel != null) {
                    mChatFooterEmojiPanel.setVisibility(View.VISIBLE);
                }
                mChatFooterAttachPanel.setVisibility(View.GONE);
                mChattingBottomPanel.setVisibility(View.VISIBLE);
                //mAppPanel.refreshAppPanel();
            }
            requestFocusEditText(true);
            mChatFooterAttachPanel.setVisibility(View.GONE);
            setBiaoqingEnabled(true);
            displaySmileyPanel();
        }
    };

    final private OnClickListener mChattingAttachClickListener
            = new OnClickListener() {

        @Override
        public void onClick(View v) {

            if(isButtomPanelNotVisibility()) {

                hideInputMethod();
                //setMode(0, -1, true);

                if(mChatFooterEmojiPanel != null) {
                    mChatFooterEmojiPanel.setVisibility(View.GONE);
                }
                mChatFooterAttachPanel.setVisibility(View.VISIBLE);
                requestFocusEditText(true);
                mChattingBottomPanel.setVisibility(View.VISIBLE);
                //mAppPanel.refreshAppPanel();
            } else {
                //setMode(CHATTING_MODE_VOICE, 22, true);
                mChatFooterEmojiPanel.setVisibility(View.GONE);
                if (mChatFooterAttachPanel.getVisibility() == VISIBLE) {
                    hideChatFooterPanel();
                    requestFocusEditText(true);
                    mInputMethodManager.showSoftInput(mEditText, 0);
                } else {
                    requestFocusEditText(false);
                    setBiaoqingEnabled(false);
                    mChatFooterAttachPanel.setVisibility(VISIBLE);
                }
            }
        }
    };


    final private AppPanel.OnAppPanelItemClickListener mAppPanelItemClickListener
            = new AppPanel.OnAppPanelItemClickListener() {

        @Override
        public void OnTakingPictureClick() {
            if(mChattingPanelClickListener != null) {
                mChattingPanelClickListener.OnTakingPictureRequest();
            }
        }

        @Override
        public void OnSelectImageClick() {
            if(mChattingPanelClickListener != null) {
                mChattingPanelClickListener.OnSelectImageReuqest();
            }
        }

        @Override
        public void OnSelectFileClick() {
            if(mChattingPanelClickListener != null) {
                mChattingPanelClickListener.OnSelectFileRequest();
            }
        }

        @Override
        public void OnSelectVoiceClick() {

        }

        @Override
        public void OnSelectVideoClick() {

        }

        @Override
        public void OnSelectFireMsgClick() {

        }

        @Override
        public void OnSelectFireLocationClick() {

        }
    };

    final private EmojiGrid.OnEmojiItemClickListener mEmojiItemClickListener
            = new EmojiGrid.OnEmojiItemClickListener() {

        @Override
        public void onEmojiItemClick(int emojiid, String emojiName) {
            mEditText.setEmojiText(emojiName);
        }

        @Override
        public void onEmojiDelClick() {
            mEditText.getInputConnection().sendKeyEvent(
                    new KeyEvent(MotionEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            mEditText.getInputConnection().sendKeyEvent(
                    new KeyEvent(MotionEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
        }
    };

    /**
     * @param context
     */
    public CustChattingFooter(Context context) {
        this(context , null);
    }

    /**
     * @param context
     * @param attrs
     */
    public CustChattingFooter(Context context, AttributeSet attrs) {
        this(context, attrs ,0);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public CustChattingFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);

        mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        initChatFooter(context);
    }

    /**
     *
     */
    private void initChatFooter(Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        mChattingFooterView = inflate(context, R.layout.chatting_footer, this);
        mEditText = ((CCPEditText) findViewById(R.id.chatting_content_et));

//        mTextPanel = ((LinearLayout) findViewById(R.id.text_panel_ll));
        mChattingBottomPanel = ((FrameLayout)findViewById(R.id.chatting_bottom_panel));
        mChattingAttach = ((ImageButton) findViewById(R.id.chatting_attach_btn));
        mChattingSend = ((Button) findViewById(R.id.chatting_send_btn));
//        mVoiceRecord = ((Button) findViewById(R.id.voice_record_bt));
        mChronometer = ((Chronometer) findViewById(R.id.chronometer));
        mChatFooterAttachPanel = (ViewGroup) findViewById(R.id.attach_panel);

        enableChattingSend(false);
        resetEnableEnterkey();

        LogUtil.e(TAG, "send edittext ime option " + mEditText.getImeOptions());
        mEditText.setOnEditorActionListener(mOnEditorActionListener);
        mEditText.setOnTouchListener(mOnTouchListener);
        mChattingSend.setOnClickListener(mChattingSendClickListener);

        mChattingAttach.setVisibility(View.VISIBLE);
        mChattingAttach.setOnClickListener(mChattingAttachClickListener);

        setBottomPanelHeight(LayoutParams.MATCH_PARENT);

        initChattingFooterPanel();
        initSmileyPanel();
        LogUtil.i(TAG, "init time:" + (System.currentTimeMillis() - currentTimeMillis));
    }

    /**
     *
     */

    public final void initSmileyPanel() {
        mBiaoqing = (ImageButton) findViewById(R.id.chatting_smiley_btn);
        mBiaoqing.setVisibility(View.VISIBLE);
        mBiaoqing.setOnClickListener(mChattingSmileyClickListener);
    }

    /**
     *
     */
    public final void displaySmileyPanel() {
        /*mTextPanel*/mEditText.setVisibility(View.VISIBLE);
        //mVoiceRecord.setVisibility(View.GONE);
        if(mChatFooterEmojiPanel != null) {
            mChatFooterEmojiPanel.reset();
        }
    }

    /**
     * Hide keyboard, keyboard does not show
     */
    private void hideInputMethod() {
        hideSoftInputFromWindow(this);
        setKeyBordShow(false);
    }

    /**
     *
     * @param tab
     */
    public final void switchChattingPanel(String tab) {
        if(TextUtils.isEmpty(tab)){
            return;
        }

        if(mChatFooterEmojiPanel == null) {
            initChattingFooterPanel();
        } else {
            mChatFooterEmojiPanel.reset();
        }
    }

    public void hideSoftInputFromWindow(View view) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) view
                    .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager == null) {
                return;
            }
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * init {@link ChatFooterPanel} if not null.
     */
    private void initChattingFooterPanel() {
        if(mChatFooterEmojiPanel == null) {
            mChatFooterEmojiPanel = new SmileyPanel(getContext(), null);
        }
        mChatFooterEmojiPanel.setOnEmojiItemClickListener(mEmojiItemClickListener);
        /*if(mChatFooterEmojiPanel != null) {
            mChatFooterEmojiPanel.setVisibility(View.GONE);
        }*/

        if(mChattingBottomPanel != null) {
            mChattingBottomPanel.addView(mChatFooterEmojiPanel,
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);
        }

        if(mEditText.getText().length() <= 0){
            return;
        }


    }

    /**
     * Hide {@link ChatFooterPanel} if not null.
     */
    private void hideChatFooterPanel() {
        mChattingBottomPanel.setVisibility(View.GONE);
        mChatFooterAttachPanel.setVisibility(View.GONE);
        setBiaoqingEnabled(false);
    }

    /**
     * Whether display emoji panel
     */
    private void setBiaoqingEnabled(boolean enabled) {
        if(mBiaoqing == null) {
            return;
        }

        if((mBiaoqingEnabled && enabled) || (!mBiaoqingEnabled && !enabled)) {
            LogUtil.d(TAG, "biao qing panel has " + enabled);
            return;
        }
        mBiaoqingEnabled = enabled;
        if(enabled) {
            mBiaoqing.setImageDrawable(getContext().getResources().getDrawable(R.drawable.chatting_biaoqing_operation_enabled));
            return;
        }

        mBiaoqing.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ytx_chatting_setmode_biaoqing_btn));
    }

    /**
     * If it is possible to enable the send button
     * @param canSend
     */
    public void enableChattingSend(boolean canSend) {
        if(mChattingAttach == null || mChattingSend == null) {
            return ;
        }

        // If the current attachment button visible, and the Enter key to send the message model
        if ((!mDonotEnableEnterkey && mChattingAttach.getVisibility() == View.VISIBLE)
                || mDonotEnableEnterkey && mChattingSend.getVisibility() == View.VISIBLE) {
            return;
        }

        if(mDonotEnableEnterkey) {
            mChattingSend.setVisibility(View.VISIBLE);
            mChattingAttach.setVisibility(View.GONE);
        } else {
            mChattingSend.setVisibility(View.GONE);
            mChattingAttach.setVisibility(View.VISIBLE);
        }
        LogUtil.d(LogUtil.getLogUtilsTag(getClass()), "mDonotEnableEnterkey " + mDonotEnableEnterkey);
        mChattingSend.getParent().requestLayout();
    }

    private void requestFocusEditText(boolean focus) {
        if(focus) {
            mEditText.requestFocus();
            //mTextPanel.setEnabled(true);
            return ;
        }
        mEditText.clearFocus();
        //mTextPanel.setEnabled(false);

    }

    /**
     *
     */
    public final void resetEnableEnterkey() {
        mDonotEnableEnterkey = ECPreferences.getSharedPreferences()
                .getBoolean(ECPreferenceSettings.SETTINGS_ENABLE_ENTER_KEY.getId(),
                        (Boolean) ECPreferenceSettings.SETTINGS_ENABLE_ENTER_KEY .getDefaultValue());
    }

    /**
     * Clear input box
     */
    public final void clearEditText() {
        if(mEditText == null) {
            return;
        }

        mEditText.setText("");
    }

    /**
     * 获得草稿
     * @return
     */
    public final String getEditText() {
        if(mEditText == null) {
            return "";
        }

        return mEditText.getText().toString();
    }

    /**
     *
     * @param showKeyBord
     */
    private void setKeyBordShow(boolean showKeyBord){
        if(mShowKeyBord = showKeyBord) {
            return;
        }
        LogUtil.d(TAG, "set Show KeyBord " + showKeyBord);
        mShowKeyBord = showKeyBord;
    }


    /**
     * switch chatting mode for Speech mode, input mode
     * @param mode
     */
    private void switchChattingMode(int mode) {
        mChattingMode = mode;
        switch (mode) {
            case CHATTING_MODE_KEYBORD:
            	//隐藏voice操作区域
                /*mTextPanel*/mEditText.setVisibility(View.VISIBLE);
                //mVoiceRecord.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     *
     * @return
     */
    public boolean isButtomPanelNotVisibility() {
        return mChattingBottomPanel.getVisibility() != View.VISIBLE;
    }

    /**
     * set the {@link AppPanel} default height
     * @param height
     */
    private void setBottomPanelHeight(int height) {

        int widthPixels = 0;
        if(height <= 0) {
            int[] displayScreenMetrics = getDisplayScreenMetrics();
            if(displayScreenMetrics[0] >= displayScreenMetrics[1]) {
                height = ResourceHelper.fromDPToPix(getContext(), 230);
            } else {
                height = ECPreferences.getSharedPreferences().getInt(
                        ECPreferenceSettings.SETTINGS_KEYBORD_HEIGHT.getId(),
                        ResourceHelper.fromDPToPix(getContext(), 230));
            }

            widthPixels = displayScreenMetrics[0];
        }

        if(height > 0 && mChattingBottomPanel != null) {
            LogUtil.d(TAG , "set bottom panel height: " + height);
            ViewGroup.LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, height);
            if(mChattingBottomPanel.getLayoutParams() != null) {
                layoutParams =  mChattingBottomPanel.getLayoutParams();
            }
            layoutParams.height = height;
        }
    }

    /**
     * Access to mobile phone screen resolution and the width and height
     * @return
     */
    @SuppressWarnings("deprecation")
    private int[] getDisplayScreenMetrics() {
        int[] metrics = new int[2];
        if (getContext() instanceof Activity) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            metrics[0] = displayMetrics.widthPixels;
            metrics[1] = displayMetrics.heightPixels;
            return metrics;
        }
        Display display = ((WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        metrics[0] = display.getWidth();
        metrics[1] = display.getHeight();

        return null;
    }


    /**
     * Register a drag event listener callback object for {@link CCPEditText}. The parameter is
     * an implementation of {@link OnDragListener}. To send a drag event to a
     * View, the system calls the
     * {@link OnDragListener#onDrag(View, android.view.DragEvent)} method.
     * @param l An implementation of {@link OnDragListener}.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public final void setOnEditTextDragListener(OnDragListener l) {
        mEditText.setOnDragListener(l);
    }

    /**
     *
     * @param l
     */
    public final void setOnChattingFooterLinstener(OnChattingFooterLinstener l) {
        mChattingFooterLinstener = l;
    }

    /**
     *
     * @param l
     */
    public final void setOnChattingPanelClickListener(OnChattingPanelClickListener l) {
        mChattingPanelClickListener = l;
    }

    /**
     * Register a drag event listener callback object for {@link CCPEditText}
     * @param textWatcher
     */
    public final void addTextChangedListener(TextWatcher textWatcher) {
        mChatingInputTextWatcher = new ChatingInputTextWatcher(textWatcher);
        mEditText.addTextChangedListener(mChatingInputTextWatcher);
    }

    /**
     *
     */
    public final void setEditTextNull() {
        mEditText.setText(null);
    }

    public final void setEditText(CharSequence text) {
        mEditText.setText(text);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    /**
     *
     * @return
     */
    public long getAvailaleSize(){

        File path = Environment.getExternalStorageDirectory(); //取得sdcard文件路径
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return (availableBlocks * blockSize)/1024 /1024;//  MIB单位
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    public final void hideBottomPanel() {
        setMode(CHATTING_MODE_ATTACH, 20, false);
    }

    /**
     * @param mode
     */
    public void setMode(int mode) {
        setMode(mode, true);
    }

    /**
     *
     * @param mode
     * @param focus
     */
    public final void setMode(int mode , boolean focus) {
        switchChattingMode(mode);
        switch (mode) {
            case CHATTING_MODE_KEYBORD:
                requestFocusEditText(true);
                hideChatFooterPanel();
                if(focus) {
                    if(mEditText.length() > 0) {
                        enableChattingSend(true);
                        return;
                    }
                }
                enableChattingSend(false);
                break;
            default:
                setVisibility(View.VISIBLE);
                break;
        }

    }

    /**
     *
     * @param mode
     * @param messageMode
     * @param focus
     */
    private void setMode(int mode , int messageMode , boolean focus) {
        if(focus) {
            switch (mode) {
                case CHATTING_MODE_KEYBORD:

                    requestFocusEditText(true);
                    mInputMethodManager.showSoftInput(this.mEditText, 0);
                    break;
                default:
                    if(focus && messageMode != 21 && mBiaoqing != null) {
                        setBiaoqingEnabled(false);
                    }
                    if(!focus && mode == 0) {
                        setBiaoqingEnabled(false);
                    }
                    mChattingBottomPanel.setVisibility(View.VISIBLE);
                    mChatFooterAttachPanel.setVisibility(View.VISIBLE);

                    break;
            }
        } else {
            if (messageMode == 20) {
                hideChatFooterPanel();
            }

            if(messageMode != 21 && mBiaoqing != null) {
                setBiaoqingEnabled(false);
            }
        }
    }

    public void setAttachPanel(View v) {
        mChatFooterAttachPanel.removeAllViews();
        mChatFooterAttachPanel.addView(v);
    }

    /**
     *
     */
    public final void onPause(){
        if(mChatFooterEmojiPanel != null) {
            mChatFooterEmojiPanel.onPause();
        }

        mChattingFooterLinstener.onPause();
    }

    public void onDestory() {
        mChatFooterAttachPanel = null;
        if(mChatFooterEmojiPanel != null) {
            mChatFooterEmojiPanel.onDestroy();
            mChatFooterEmojiPanel = null;
        }

        if(mEditText != null) {
            mEditText.miInputConnection = null;
            mEditText.setOnEditorActionListener(null);
            mEditText.setOnTouchListener(null);
            mEditText.removeTextChangedListener(null);
            mEditText.clearComposingText();
            mEditText = null;
        }
        mChattingSend.setOnClickListener(null);

        //mVoiceRecord.removeTextChangedListener(null);
        //initAppPanel();
        mChattingAttach.setOnClickListener(null);
        mChattingAttach = null;

        mChattingFooterLinstener = null;
        mChattingPanelClickListener = null;
        mChatingInputTextWatcher = null;
    }

    /**
     * Interface definition for a callback to be invoked when the {@link ChatFooterPanel} has been click
     * such as .emoji click , voice rcd onTouch 
     */
    public interface OnChattingFooterLinstener {

        //void OnVoiceRcdInitReuqest();

        //void OnVoiceRcdStartRequest();
        /**
         * Called when the voce record button nomal and cancel send voice.
         */
        //void OnVoiceRcdCancelRequest();
        /**
         * Called when the voce record button nomal and send voice.
         */
        //void OnVoiceRcdStopRequest();

        void OnSendTextMessageRequest(CharSequence text);

        //void OnUpdateTextOutBoxRequest(CharSequence text);

        //void OnSendCustomEmojiRequest(int emojiid, String emojiName);

        //void OnEmojiDelRequest();

        void OnInEditMode();

        void onPause();

        //void onResume();

        void release();
    }

    /**
     * Interface definition for a callback to be invoked when the {@link ChatFooterPanel} has been click
     * such as .emoji click , voice rcd onTouch 
     */
    public interface OnChattingPanelClickListener {
        void OnTakingPictureRequest();
        void OnSelectImageReuqest();
        void OnSelectFileRequest();

    }


    private class ChatingInputTextWatcher implements TextWatcher {

        private TextWatcher mTextWatcher;
        /**
         *
         */
        public ChatingInputTextWatcher(TextWatcher textWatcher) {
            mTextWatcher = textWatcher;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            mTextWatcher.beforeTextChanged(s, start, count, after);
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            mTextWatcher.onTextChanged(s, start, before, count);
        }

        @Override
        public void afterTextChanged(Editable s) {
            mTextWatcher.afterTextChanged(s);
            if ((s.length() > 0) && (s.toString().trim().length() > 0)){
                mDonotEnableEnterkey = true;
                enableChattingSend(true);
            } else {
                mDonotEnableEnterkey = false;
                enableChattingSend(false);
            }
        }

    }


    /**
     * @return
     */
    public int getMode() {
        return 0;
    }


    /**
     * @param b
     */
    public void setCancle(boolean b) {

    }

    /**
     * @return
     */
    public boolean isVoiceRecordCancle() {
        return false;
    }

}
