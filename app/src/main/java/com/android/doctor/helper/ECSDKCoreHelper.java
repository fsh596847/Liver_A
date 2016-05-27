package com.android.doctor.helper;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.android.doctor.R;
import com.android.doctor.app.AppContext;
import com.android.doctor.model.User;
import com.android.doctor.ui.app.MainActivity;
import com.yuntongxun.ecsdk.ECChatManager;
import com.yuntongxun.ecsdk.ECDeskManager;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.ecsdk.ECError;
import com.yuntongxun.ecsdk.ECGroupManager;
import com.yuntongxun.ecsdk.ECInitParams;
import com.yuntongxun.ecsdk.ECNotifyOptions;
import com.yuntongxun.ecsdk.ECVoIPCallManager;
import com.yuntongxun.ecsdk.SdkErrorCode;
import com.yuntongxun.ecsdk.VideoRatio;
import com.yuntongxun.kitsdk.ECDeviceKit;
import com.yuntongxun.kitsdk.listener.OnInitSDKListener;
import com.yuntongxun.kitsdk.setting.ECPreferenceSettings;
import com.yuntongxun.kitsdk.setting.ECPreferences;
import com.yuntongxun.kitsdk.ui.chatting.model.IMChattingHelper;
import com.yuntongxun.kitsdk.utils.ECNotificationManager;
import com.yuntongxun.kitsdk.utils.ToastUtil;

import java.io.InvalidClassException;

/**
 * Created by Jorstin on 2015/3/17.
 */
public class ECSDKCoreHelper implements ECDevice.InitListener , ECDevice.OnECDeviceConnectListener,ECDevice.OnLogoutListener, OnInitSDKListener {

    public static final String TAG = "ECSDKCoreHelper";
    public static final String ACTION_LOGOUT = "com.yuntongxun.ECDemo_logout";
    public static final String ACTION_SDK_CONNECT = "com.yuntongxun.Intent_Action_SDK_CONNECT";
    public static final String ACTION_KICK_OFF = "com.yuntongxun.Intent_ACTION_KICK_OFF";
    private static final String AppKey = "8a48b5514fd49643014fed92fca838ea";
    private static final String AppToken = "f46afd5c1f092a218b8988217acb9677";

    private static ECSDKCoreHelper sInstance;
    private Context mContext;
    private ECDevice.ECConnectState mConnect = ECDevice.ECConnectState.CONNECT_FAILED;
    private ECInitParams mInitParams;
    private ECInitParams.LoginMode mMode = ECInitParams.LoginMode.FORCE_LOGIN;
    /**初始化错误*/
    public static final int ERROR_CODE_INIT = -3;
    
    public static final int WHAT_SHOW_PROGRESS = 0x101A;
	public static final int WHAT_CLOSE_PROGRESS = 0x101B;
    private boolean mKickOff = false;
    private ECNotifyOptions mOptions;
    public static SoftUpdate mSoftUpdate;

    private Handler handler;
    private ECSDKCoreHelper() {
    	initNotifyOptions();
    }

    public static ECSDKCoreHelper getInstance() {
        if (sInstance == null) {
            sInstance = new ECSDKCoreHelper();
        }
        return sInstance;
    }
    
    public synchronized void setHandler(final Handler handler) {
		this.handler = handler;
	}

    public static boolean isKickOff() {
        return getInstance().mKickOff;
    }

    public void init(Context ctx) {
        getInstance().mKickOff = false;
        getInstance().mContext = ctx;
        if (!ECDeviceKit.isInitialized()) {
            Log.d(TAG, "[ECSDKCoreHelper-> init] ECDeviceKit.isInitialized is false");
            User.UserEntity.YtxsubaccountEntity account = AppContext.context().getUser().getYtxsubaccount();
            ECDeviceKit.init(account.getVoipaccount(), ctx, this);
        }
    }

    public static void setSoftUpdate(String version , String desc , boolean mode) {
        mSoftUpdate = new SoftUpdate(version ,desc ,mode);
    }
    
    private void initNotifyOptions() {
        if(mOptions == null) {
            mOptions = new ECNotifyOptions();
        }
        mOptions.setNewMsgNotify(true);         //设置新消息是否提醒
        mOptions.setIcon(R.mipmap.ic_launcher); // 设置状态栏通知图标
        mOptions.setSilenceEnable(false);       // 设置是否启用勿扰模式（不会声音/震动提醒）
        mOptions.setSilenceTime(23, 0, 8, 0);   /* 设置勿扰模式时间段（开始小时/开始分钟-结束小时/结束分钟）
                                                    小时采用24小时制
                                                    如果设置勿扰模式不启用，则设置勿扰时间段无效
                                                    当前设置晚上11点到第二天早上8点之间不提醒
                                                    设置是否震动提醒(如果处于免打扰模式则设置无效，没有震动)
                                                 */
        mOptions.enableShake(true);
        mOptions.enableSound(true);             // 设置是否声音提醒(如果处于免打扰模式则设置无效，没有声音)
    }

    @Override
    public void onInitialized() {
        Log.d(TAG, "[ECSDKCoreHelper-> onInitialized] ECSDK is ready");

        ECDevice.setNotifyOptions(mOptions);    // 设置消息提醒
                                                /* 设置SDK注册结果回调通知，当第一次初始化注册成功或者失败会通过该引用回调
                                                   通知应用SDK注册状态
                                                   当网络断开导致SDK断开连接或者重连成功也会通过该设置回调
                                                */
        ECDevice.setOnChatReceiveListener(IMChattingHelper.getInstance());
        ECDevice.setOnDeviceConnectListener(this);

        User.UserEntity.YtxsubaccountEntity account = AppContext.context().getUser().getYtxsubaccount();

        if (mInitParams == null){
            mInitParams = ECInitParams.createParams();
        }
        mInitParams.reset();
        mInitParams.setUserid(account.getVoipaccount());
        mInitParams.setAppKey(AppKey);
        mInitParams.setToken(AppToken);

        // ECInitParams.LoginMode.FORCE_LOGIN
        mInitParams.setMode(getInstance().mMode);
        if(!TextUtils.isEmpty(account.getVoippwd())) {  // 如果有密码（VoIP密码，对应的登陆验证模式是）
                                                        // ECInitParams.LoginAuthType.PASSWORD_AUTH
            mInitParams.setPwd(account.getVoippwd());
        }

        // 设置登陆验证模式（是否验证密码/如VoIP方式登陆）
        //if(clientUser.getLoginAuthType() != null) {
            mInitParams.setAuthType(ECInitParams.LoginAuthType.PASSWORD_AUTH);
        //}

        if(!mInitParams.validate()) {
            ToastUtil.showMessage(R.string.regist_params_error);
            Intent failIntent = new Intent(ACTION_SDK_CONNECT);
            failIntent.putExtra("error", -1);
            mContext.sendBroadcast(failIntent);
            return ;
        }

        if(mInitParams.validate()) {
            ECDevice.login(mInitParams);
        }
        
    }

    @Override
    public void onConnect() {}

    @Override
    public void onDisconnect(ECError error) {
        ToastUtil.showMessage(error.errorMsg);
    }

    @Override
    public void onConnectState(ECDevice.ECConnectState state, ECError error) {
        Log.d(TAG, "[ECSDKCoreHelper-> onConnectState] state:  " + state + " " + error.errorMsg);
        if(state == ECDevice.ECConnectState.CONNECT_FAILED && error.errorCode == SdkErrorCode.SDK_KICKED_OFF) {
            try {
                ECPreferences.savePreference(ECPreferenceSettings.SETTINGS_REGIST_AUTO, "", true);
//                ECPreferences.savePreference(ECPreferenceSettings.SETTINGS_FULLY_EXIT, true, true);
//                ECPreferences.savePreference(ECPreferenceSettings.SETTINGS_FULLY_EXIT, true, true);
            } catch (InvalidClassException e) {
                e.printStackTrace();
            }
            mKickOff = true;
            // 失败，账号异地登陆
            Intent intent = new Intent(ACTION_KICK_OFF);
            intent.putExtra("kickoffText" , error.errorMsg);
            mContext.sendBroadcast(intent);
            ((MainActivity)mContext).handlerKickOff(error.errorMsg);
            INotificationManager.getInstance().showKickoffNotification(mContext ,error.errorMsg);
        }
        getInstance().mConnect = state;
        Intent intent = new Intent(ACTION_SDK_CONNECT);
        intent.putExtra("error", error.errorCode);
        mContext.sendBroadcast(intent);
        postConnectNotify();
    }

    /**
     * 当前SDK注册状态
     * @return
     */
    public static ECDevice.ECConnectState getConnectState() {
        return getInstance().mConnect;
    }

    @Override
    public void onLogout() {
        getInstance().mConnect = ECDevice.ECConnectState.CONNECT_FAILED;
        if(mInitParams != null && mInitParams.getInitParams() != null) {
            mInitParams.getInitParams().clear();
        }
        mInitParams = null;
        mContext.sendBroadcast(new Intent(ACTION_LOGOUT));
    }

    @Override
    public void onError(Exception exception) {
        Log.e(TAG, "ECSDK couldn't start: " + exception.getLocalizedMessage());
        Intent intent = new Intent(ACTION_SDK_CONNECT);
        intent.putExtra("error", ERROR_CODE_INIT);
        mContext.sendBroadcast(intent);
        ECDevice.unInitial();
    }

    /**
     * 状态通知
     */
    private static void postConnectNotify() {
        if(getInstance().mContext instanceof MainActivity) {
            ((MainActivity) getInstance().mContext).onNetWorkNotify(getConnectState());
        }
    }

    public static void logout(boolean isNotice) {
    	ECDevice.NotifyMode notifyMode = (isNotice) ? ECDevice.NotifyMode.IN_NOTIFY : ECDevice.NotifyMode.NOT_NOTIFY;
        ECDevice.logout(notifyMode, getInstance());
        getInstance().mKickOff = false;
        ECDeviceKit.logout();
    }

    /**
     * IM聊天功能接口
     * @return
     */
    public static ECChatManager getECChatManager() {
        ECChatManager ecChatManager = ECDevice.getECChatManager();
        Log.d(TAG, "ecChatManager :" + ecChatManager);
        return ecChatManager;
    }

    /**
     * 群组聊天接口
     * @return
     */
    public static ECGroupManager getECGroupManager() {
        return ECDevice.getECGroupManager();
    }

    public static ECDeskManager getECDeskManager() {
        return ECDevice.getECDeskManager();
    }



    public static class SoftUpdate  {
        public String version;
        public String desc;
        public boolean force;

        public SoftUpdate(String version ,String desc, boolean force) {
            this.version = version;
            this.force = force;
            this.desc = desc;
        }
    }
    
    /**
     * 
     * @return返回底层so库 是否支持voip及会议功能 
     * true 表示支持 false表示不支持
     * 请在sdk初始化完成之后调用
     */
    public boolean isSupportMedia(){
    	
    	return ECDevice.isSupportMedia();
    }
    
    public static boolean hasFullSize(String inStr) {
		if (inStr.getBytes().length != inStr.length()) {
			return true;
		}
		return false;
	}

    /**
     * 判断服务是否自动重启
     * @return 是否自动重启
     */
    public static boolean isUIShowing() {
        return ECDevice.isInitialized();
    }
    
}
