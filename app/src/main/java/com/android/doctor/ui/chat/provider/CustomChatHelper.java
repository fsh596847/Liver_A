package com.android.doctor.ui.chat.provider;

import android.content.Context;

import com.android.doctor.app.AppContext;
import com.android.doctor.helper.ChatUtils;
import com.android.doctor.model.User;
import com.android.doctor.ui.chat.DoctorProfileActivity;
import com.android.doctor.ui.chat.GroupProfileActivity;
import com.android.doctor.ui.chat.NoticeMsgActivity;
import com.android.doctor.ui.patient.PatientProfileActivity;
import com.yuntongxun.ecsdk.ECMessage;
import com.yuntongxun.kitsdk.custom.CommonUserData;
import com.yuntongxun.kitsdk.custom.MsgUserDataUtil;
import com.yuntongxun.kitsdk.custom.NoticeUserData;
import com.yuntongxun.kitsdk.custom.provider.chat.ECCustomChatActionProvider;
import com.yuntongxun.kitsdk.custom.provider.chat.ECCustomChatPlusExtendProvider;
import com.yuntongxun.kitsdk.custom.provider.chat.ECCustomChatUIProvider;


public class CustomChatHelper implements ECCustomChatUIProvider,
		ECCustomChatActionProvider, ECCustomChatPlusExtendProvider {

	
	/**
	 * 当想扩展点击聊天底部'+'号、在这里添加标题内容
	 */
	@Override
	public String[] getCustomPlusTitleArray(Context context) {
		String[] arr = null;
		/*if (DemoDataConstance.isShowCustom) {
			arr = new String[] { "测试1", "测试2", "测试3" };
			return arr;
		}*/
		return null;
	}

	/**
	 * 当想扩展点击聊天底部'+'号、在这里添加图标
	 */
	@Override
	public int[] getCustomPlusDrawableArray(Context context) {

		int[] arr = null;
		/*if (DemoDataConstance.isShowCustom) {
			arr = new int[] { R.drawable.custom_chattingfooter_file_selector,
					R.drawable.custom_chattingfooter_image_selector,
					R.drawable.custom_chattingfooter_takephoto_selector,
			};
			return arr;
		}*/
		return null;
	}
    
	/**
	 * 点击扩展的自定义item会触发该事件,index代表当前点击是第几个
	 */
	@Override
	public boolean onPlusExtendedItemClick(Context context, String title,
			int index) {
		/*if (DemoDataConstance.isShowCustom) {
			ToastUtil.showMessage("点击的是index=" + index + ";title=" + title);
			return true;
		}*/
		return false;
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
			Object obj = MsgUserDataUtil.getUserData(udata);
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

					if (MsgUserDataUtil.isPatient(uEntity)) {
                        PatientProfileActivity.startAty(context, from.getId());
					} else if (MsgUserDataUtil.isGroup(uEntity)) {
                        if (String.valueOf(0).equals(from.getType())) {
                            DoctorProfileActivity.startAty(context, from.getId());
                        } else if (String.valueOf(1).equals(from.getId())) {
                            PatientProfileActivity.startAty(context, from.getId());
                        }
					} else if (MsgUserDataUtil.isDoctor(uEntity)) {
                        DoctorProfileActivity.startAty(context, from.getId());
					}
					return true;
				}
			}
		}
		return false;
	}

}
