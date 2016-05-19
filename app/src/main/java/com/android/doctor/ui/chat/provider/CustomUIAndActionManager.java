package com.android.doctor.ui.chat.provider;

import com.yuntongxun.kitsdk.core.ECCustomProviderEnum;
import com.yuntongxun.kitsdk.core.ECKitCustomProviderManager;

public class CustomUIAndActionManager {

	/**
	 * 将自定义扩展的功能加载到kit sdk进行管理
	 */
	public static void initCustomUI() {

		ECKitCustomProviderManager.addCustomProvider(
				ECCustomProviderEnum.CONVERSATION_PROVIDER,
				CustomConversationListHelper.class);

		ECKitCustomProviderManager.addCustomProvider(
				ECCustomProviderEnum.CHATTING_PROVIDER,
				CustomChatHelper.class);

	}

}
