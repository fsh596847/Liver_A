package com.android.doctor.app;

/**
 * @author Yong
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.android.doctor.model.User;
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
import com.yuntongxun.kitsdk.core.CCPAppManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

public class AppContext extends Application {
	private static final String TAG 						= AppContext.class.getSimpleName();
    public static Md5FileNameGenerator md5FileNameGenerator = new Md5FileNameGenerator();
	private static Context context;
	private static ImageLoader imageLoader;
	private DisplayImageOptions imageOptions;
    private AppLocate mAppLocate;
	private boolean isLogin = false;
	private boolean mHttps;

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
		init();
	}
	
	private void init() {
		context = this;
        //CrashHandler crashHandler = CrashHandler.getInstance();
        //crashHandler.init(getApplicationContext());
		CCPAppManager.setContext(this);
        initDisplayImageOptions();
		initImageLoader();
		initLogin();
        //mAppLocate = new AppLocate(this);
	}

	public void initLogin() {
        String token = getProperty("user.token");
        if (token != null && !token.isEmpty()) {
            isLogin = true;
        }
	}


	public boolean isLogin() {
		return isLogin;
	}

	private void initDisplayImageOptions() {
		imageOptions = new DisplayImageOptions.Builder()
			//.showStubImage(R.drawable.image_holder_color)
			//.showImageForEmptyUri(R.drawable.image_holder_color)
			//.showImageOnFail(R.drawable.image_holder_color)
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

	public AppLocate getmAppLocate() {
		return mAppLocate;
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

	public void saveUser(User u) {
        if (u == null) return;
        /*User.UserEntity ue = u.getUser();
        if (ue == null) return;
        try {
            File dirConf = getDir(AppConfig.APP_CONFIG, Context.MODE_PRIVATE);
            FileOutputStream fos = new FileOutputStream(dirConf.getPath() + File.separator + "user.data");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(u);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
		setProperty("user.token", u.getToken());
        setProperty("config.user", u.toJson());
        //setProperty("user.duid", "" + ue.getDuid());
        initLogin();
	}

    public User getUser() {
        /*try {
            File dirConf = getDir(AppConfig.APP_CONFIG, Context.MODE_PRIVATE);
            FileInputStream fis = new FileInputStream(dirConf.getPath() + File.separator + "user.data");
            ObjectInputStream ois = new ObjectInputStream(fis);
            User u = (User)ois.readObject();
            ois.close();
            return u;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        Gson gson = new Gson();
        return gson.fromJson(getProperty("config.user"), User.class);
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
        removeProperty("user.uid");
        removeProperty("user.token");
    }

	public void logout() {
        isLogin  = false;
        clearUser();
		clearAppCache();
	}

	public void AppExit(Context context) {
		try {
			// 杀死该应用进程
			//AppContext.context().getmAppLocate().stopLocate();
			AppManager.getAppManager().AppExit(context);

		} catch (Exception e) {
		}
	}
}
