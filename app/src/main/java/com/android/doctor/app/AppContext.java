package com.android.doctor.app;

/**
 * @author Yong
 */

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.android.doctor.R;
import com.android.doctor.helper.ECSDKCoreHelper;
import com.android.doctor.model.User;
import com.android.doctor.ui.app.MainActivity;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.PlatformConfig;
import com.yuntongxun.ecsdk.ECDevice;
import com.yuntongxun.kitsdk.ECDeviceKit;
import com.yuntongxun.kitsdk.core.CCPAppManager;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Properties;

public class AppContext extends Application {
	public static final String TAG = "Doctor";
    public static Md5FileNameGenerator md5FileNameGenerator = new Md5FileNameGenerator();
	private static Context context;
	private static ImageLoader imageLoader;
	private DisplayImageOptions imageOptions;
	private boolean isLogin = false;
	private boolean mHttps;
    private User user;

	public AppContext() {
		Log.i(TAG, "AppContext()");
	}
	
	public static synchronized AppContext context() {
	    return (AppContext)context ;
	}
	
	public static ImageLoader getImageLoader() {
		return imageLoader;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
        //LeakCanary.install(this);
		init();
	}
	
	private void init() {
		context = getApplicationContext();
        //CrashHandler crashHandler = CrashHandler.getInstance();
        //crashHandler.init(getApplicationContext());
		CCPAppManager.setContext(this);
        initImageOptions();
		initImageLoader();
		initLogin();
        initSocialShareSDK();
        //mAppLocate = new AppLocate(this);
	}

	public void initLogin() {
        user = initUser();
        if (user != null) {
            isLogin = true;
        }
	}

    private void initSocialShareSDK() {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("3921700954","04b48b094faeb16683c32669824ebdad");
        //新浪微博 appkey appsecret
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        // QQ和Qzone appid appkey
        PlatformConfig.setAlipay("2015111700822536");
    }

	public boolean isLogin() {
		return isLogin;
	}

	private void initImageOptions() {
		imageOptions = new DisplayImageOptions.Builder()
			//.showStubImage(R.drawable.image_holder_color)
			//.showImageForEmptyUri(R.drawable.image_holder_color)
			.showImageOnFail(R.drawable.ic_picture_occupy)
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.cacheInMemory(false)
			.cacheOnDisc(true)
			.build();
	}
    private void initImageLoader() {
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), AppConfig.DEFAULT_SAVE_IMAGE_PATH);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(this)
                .threadPoolSize(8)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new WeakMemoryCache())       // .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(md5FileNameGenerator) // 将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                //.diskCache(new UnlimitedDiskCache(cacheDir ,null ,md5FileNameGenerator))//自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                        // .writeDebugLogs() // Remove for release app
                .build();//开始构建
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }
	public static DisplayImageOptions getRoundImageOptions() {
		return new DisplayImageOptions.Builder()
			//.showStubImage(R.drawable.image_holder_color)
			//.showImageForEmptyUri(R.drawable.image_holder_color)
			//.showImageOnFail(R.drawable.image_holder_color)
			.imageScaleType(ImageScaleType.EXACTLY)
			.displayer(new RoundedBitmapDisplayer(10)).build();
	}
	
	public DisplayImageOptions getOptions(){
		return imageOptions;
	}

	public boolean containsProperty(String key) {
		Properties props = getProperties();
		return props.containsKey(key);
	}

	public void setProperties(Properties ps) {
		AppConfig.getAppConfig(this).set(ps);
	}

	public Properties getProperties() {
		return AppConfig.getAppConfig(this).get();
	}

	public void setProperty(String key, String value) {
		AppConfig.getAppConfig(this).set(key, value);
	}

	/**
	 * 获取cookie时传AppConfig.CONF_COOKIE
	 * @param key
	 * @return
	 */
	public String getProperty(String key) {
		String res = AppConfig.getAppConfig(this).get(key);
		return res;
	}

	public void removeProperty(String... key) {
		AppConfig.getAppConfig(this).remove(key);
	}

    public static SharedPreferences getPreferences() {
        SharedPreferences preference = context.getSharedPreferences(AppConfig.CONF_SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        return preference;
    }

    public static void set(String key, int value) {
        Editor editor = getPreferences().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static void set(String key, boolean value) {
        SharedPreferences spf = context.getSharedPreferences(AppConfig.CONF_SHARED_PREFERENCES, MODE_PRIVATE);
        Editor editor = getPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void set(String key, String value) {
        Editor editor = getPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static boolean get(String key, boolean defValue) {
        return getPreferences().getBoolean(key, defValue);
    }

    public static String get(String key, String defValue) {
        return getPreferences().getString(key, defValue);
    }

    public static int get(String key, int defValue) {
        return getPreferences().getInt(key, defValue);
    }

    public static long get(String key, long defValue) {
        return getPreferences().getLong(key, defValue);
    }

    public static float get(String key, float defValue) {
        return getPreferences().getFloat(key, defValue);
    }

    public void saveUser(User u) {
        if (u == null) return;
        set(AppConfig.APP_CONFIG_USER, u.toJson());
        //setProperty("user.duid", "" + ue.getDuid());
        initLogin();
    }

    public synchronized User.UserEntity getUser() {
        if (user != null) {
            return user.getUser();
        } else {
            Log.d(AppConfig.TAG, "[AppContext-> getUser] user is null");
        }
        return null;
    }
    public synchronized User initUser() {
        Gson gson = new Gson();
        User u = gson.fromJson(get(AppConfig.APP_CONFIG_USER, ""), User.class);
        if (u == null) {
            Log.d(AppConfig.TAG, "[AppContext-> getUser] u == null");
            return null;
        }
        return u;
    }


    public Object getUserFieldValue(String name) {
        User.UserEntity userEntity = getUser();
        if (userEntity == null) return null;

        Class<?> classType = userEntity.getClass();
        String firstLetter = name.substring(0, 1).toUpperCase();
        String getMethodName = "get" + firstLetter + name.substring(1);
        try {
            Method getMethod = classType.getMethod(getMethodName,new Class[] {});
            return getMethod.invoke(userEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
		// 清除数据缓存
        DataCleanManager.cleanDatabases(this);
        DataCleanManager.cleanInternalCache(this);
        // 清除编辑器保存的临时内容
        Properties props = getProperties();
        for (Object key : props.keySet()) {
            String _key = key.toString();
            if (_key.startsWith("temp"))
                removeProperty(_key);
        }
    }

    public void clearUser() {
        removeProperty("config.user");
        removeProperty("user.uid");
        removeProperty("user.token");
    }

	public void logout() {
        isLogin  = false;
        clearUser();
		//clearAppCache();
        ECSDKCoreHelper.logout(false);
	}

    public void restartAPP() {
        ECDeviceKit.logout();
        Intent intent = new Intent(this, AppLauncher.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        AppManager.getAppManager().AppExit();
    }

	public void AppExit() {
		try {
			// 杀死该应用进程
			//AppContext.context().getmAppLocate().stopLocate();
			AppManager.getAppManager().AppExit();

		} catch (Exception e) {
		}
	}
}
