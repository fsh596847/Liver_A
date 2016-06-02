package com.android.doctor.ui.chat.provider;

import android.content.Context;
import android.util.Log;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.User;
import com.android.doctor.ui.chat.DoctorProfileActivity;
import com.android.doctor.ui.chat.GroupProfileActivity;
import com.android.doctor.ui.chat.custome.CommonUserData;
import com.android.doctor.ui.chat.custome.UserDataUtil;
import com.android.doctor.ui.patient.PatientProfileActivity;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.kitsdk.ECDeviceKit;
import com.yuntongxun.kitsdk.VoipKitManager;
import com.yuntongxun.kitsdk.custom.provider.chat.ECCustomChatActionProvider;
import com.yuntongxun.kitsdk.custom.provider.chat.ECCustomChatPlusExtendProvider;
import com.yuntongxun.kitsdk.custom.provider.chat.ECCustomChatUIProvider;
import com.yuntongxun.kitsdk.ui.ECChattingActivity;


public class CustomChatHelper implements ECCustomChatUIProvider,
		ECCustomChatActionProvider, ECCustomChatPlusExtendProvider {

	
	/**
	 * 当想扩展点击聊天底部'+'号、在这里添加标题内容
	 */
	@Override
	public String[] getCustomPlusTitleArray(Context context) {
		String[] arr = new String[] {  "音频", "视频" };
		return arr;
	}

	/**
	 * 当想扩展点击聊天底部'+'号、在这里添加图标
	 */
	@Override
	public int[] getCustomPlusDrawableArray(Context context) {
		int[] arr = null;
		arr = new int[] {
				/*R.drawable.image_icon,
				R.drawable.photograph_icon,*/
				R.drawable.voip_call,
				R.drawable.video_call
		};
		return arr;
	}
    
	/**
	 * 点击扩展的自定义item会触发该事件,index代表当前点击是第几个
	 */
	@Override
	public boolean onPlusExtendedItemClick(Context context, String title,
			int index) {
		/*if (DemoDataConstance.isShowCustom) {*/
        if (index == 2) {
            if (context.getClass().equals(ECChattingActivity.class)) {
                ECChattingActivity aty = (ECChattingActivity) context;
                VoipKitManager.makeVoiceCall("", "13520466999");
                Log.d(AppConfig.TAG, "[CustomChatHelper-> onPlusExtendedItemClick] " + aty.getmRecipients());
            }
        } else if (index == 3) {
            if(!ECDeviceKit.isInitialized()){
                UIHelper.showToast("SDK未初始化");
                return false;
            }
            VoipKitManager.makeVideoCall("", "18600283063");
        }
        return true;
	}

	/**
	 * 当想自定义长按消息item需要重写该方法
	 */
	@Override
	public boolean onCustomChatMessageItemLongClick(Context context,
			ECMessage message) {
		/*if (DemoDataConstance.isShowCustom) {
			ToastUtil.showMessage("自定义长按消息子条目触发事件");
			return true;
		}*/
		return false;
	}

	/**
	 * 当想自定义聊天界面导航右边按钮点击事件时触发
	 */
	@Override
	public boolean onRightavigationBarClick(Context context,// ok
			String sessionId) {
        GroupProfileActivity.startAty(context, sessionId);
		return false;
	}

	/**
	 * 当想自定义点击聊天消息item的头像的时候触发
	 */
	@Override
	public boolean onMessagePortRaitClick(Context context, ECMessage message) {// ok
		String udata = message.getUserData();
		if (udata != null) {
			Object obj = UserDataUtil.getUserData(udata);
			if (obj != null) {
				if (obj.getClass().equals(CommonUserData.class)) {
					CommonUserData uEntity = (CommonUserData) obj;
					User.UserEntity userEntity = AppContext.context().getUser();
					if (userEntity == null) {
						return false;
					}

                    CommonUserData.FromEntity from = uEntity.getFrom();
					if (userEntity.getDuid().equals(from.getId())) {
                        return false;
					}

					if (UserDataUtil.isPatient(uEntity)) {
                        PatientProfileActivity.startAty(context, from.getId());
					} else if (UserDataUtil.isGroup(uEntity)) {
                        if (String.valueOf(0).equals(from.getType())) {
                            DoctorProfileActivity.startAty(context, from.getId());
                        } else if (String.valueOf(1).equals(from.getId())) {
                            PatientProfileActivity.startAty(context, from.getId());
                        }
					} else if (UserDataUtil.isDoctor(uEntity)) {
                        DoctorProfileActivity.startAty(context, from.getId());
					}
					return true;
				}
			}
		}
		return false;
	}

}
