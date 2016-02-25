package com.fidots.restaurant.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AppContext extends Application implements AMapLocationListener{
	private static final String TAG 						= AppContext.class.getSimpleName();
	private static final String CACHE_FOLDER_NAME 			= "codeonline";
	private static final String CACHE_IMAGE 				= "image";
	private static final String CACHE_DATA 					= "data";
	private static final int 	CACHE_SIZE_MIB 				= 5 * 1000 * 1000;
	private static final int 	NETWORK_THREAD_POOL_SIZE 	= 6;
	private static final int 	TIMEOUTMS 					= 30000;
	
	private static Context context;
	private static ImageLoader imageLoader;
	private DisplayImageOptions imageOptions;
	private static RequestQueue mRequestQueue;
	private boolean isLogin;
	private boolean mHttps;
	//声明AMapLocationClient类对象
	public AMapLocationClient mLocationClient = null;
	public AMapLocationClientOption mLocationOption = null;

	public AppContext() {
		Log.i(TAG, "BaseApplication()");
	}
	
	public static synchronized AppContext context() {
	    return (AppContext)context ;
	}
	
	public static ImageLoader getImageLoader() {
		return imageLoader;
	}
	
	public static RequestQueue getRequestQueue() {
		return mRequestQueue;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		init();
	}
	
	private void init() {
		context = getApplicationContext();
		initDisplayImageOptions();
		initImageLoader();
		initVolleyQueue();
		initLogin();
		//initLoc();
	}

	public void initLogin() {
		/*User user = getLoginUser();
		if (null != user && user.getUser_id() != null) {
            isLogin = true;
        } else {
            PrefManager.cleanUserInfo(this);
        }*/
	}
	
	private void initLoc() {
		//声明定位回调监听器
		//初始化定位
		mLocationClient = new AMapLocationClient(getApplicationContext());
		//设置定位回调监听
		mLocationClient.setLocationListener(this);

		//初始化定位参数
		mLocationOption = new AMapLocationClientOption();
		//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
		mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
		//设置是否返回地址信息（默认返回地址信息）
		mLocationOption.setNeedAddress(true);
		//设置是否只定位一次,默认为false
		mLocationOption.setOnceLocation(false);
		//设置是否强制刷新WIFI，默认为强制刷新
		mLocationOption.setWifiActiveScan(true);
		//设置是否允许模拟位置,默认为false，不允许模拟位置
		mLocationOption.setMockEnable(false);
		//设置定位间隔,单位毫秒,默认为2000ms
		mLocationOption.setInterval(2000);
		//给定位客户端对象设置定位参数
		mLocationClient.setLocationOption(mLocationOption);
		//启动定位
		mLocationClient.startLocation();
	}
	
	/*public User getLoginUser() {
		return PrefManager.getUserInfo(this);
	}*/
			
	public boolean isLogin() {
		return isLogin;
	}

	private void initVolleyQueue() {
		if (mRequestQueue == null) {
			Cache cache = new DiskBasedCache(getCacheDir(CACHE_DATA), CACHE_SIZE_MIB);
    		Network network = new BasicNetwork(new HurlStack());
    		mRequestQueue = new RequestQueue(cache, network, NETWORK_THREAD_POOL_SIZE);
			mRequestQueue.start();
		}
	}
	
	public void initImageLoader() {
		File cacheDir = getCacheDir(CACHE_IMAGE);
		imageLoader = ImageLoader.getInstance();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
		            .denyCacheImageMultipleSizesInMemory()
		            .memoryCache(new LRULimitedMemoryCache(CACHE_SIZE_MIB)) // You can pass your own memory cache implementation
		            .discCache(new UnlimitedDiskCache(cacheDir)) // You can pass your own disc cache implementation
		            .defaultDisplayImageOptions(imageOptions)
		            .build();
		imageLoader.init(config);
	}
	
	public File getCacheDir(String name) {
		File appCacheDir = null;
		if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + CACHE_FOLDER_NAME;
			appCacheDir = new File(path, name);
		}
		if(appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())){
			String path = getCacheDir().getPath() + File.separator + CACHE_FOLDER_NAME;
			appCacheDir = new File(path, name);
		}
		return appCacheDir;
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

	@SuppressWarnings("unused")
	private File getCacheDirectory() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			//File dir1 = Environment.getExternalStorageDirectory();
			String sdCardPath = Environment.getExternalStorageDirectory().getPath();
			//String cachePath = sdCardPath + File.separator + CACHE_FOLDER_NAME;
			return new File(sdCardPath, CACHE_FOLDER_NAME);
		} else {
			String dataPath =  getCacheDir().getPath();
			//String cachePath = dataPath + File.separator + CACHE_FOLDER_NAME;
			return new File(dataPath, CACHE_FOLDER_NAME);
		}
	}
	
	public <T> void addToRequestQueue(Request<T> req, String tag){
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		VolleyLog.d("add request to queue: %s", req.getUrl());
		req.setRetryPolicy(new DefaultRetryPolicy(TIMEOUTMS, 0, 1));
		mRequestQueue.add(req);
	}
	
	public <T> void addToRequestQueue(Request<T> req){
		req.setTag(TAG);
		req.setRetryPolicy(new DefaultRetryPolicy(TIMEOUTMS, 0, 1));
		mRequestQueue.add(req);
	}
	
	public void cancelRequests(Object tag){
		if (mRequestQueue != null){
			mRequestQueue.cancelAll(tag);
		}
	}
	
	
	public long caculateCacheSize() {
		long fileSize = 0;

		File dataCacheDir = getCacheDir(CACHE_DATA);
		File imgCacheDir = getCacheDir(CACHE_IMAGE);
		
		//fileSize += FileHelper.getDirSize(dataCacheDir);
		//fileSize += FileHelper.getDirSize(imgCacheDir);
		
		File filesDir = getFilesDir();
        File cacheDir = getCacheDir();
        
       // fileSize += FileHelper.getDirSize(filesDir);
		//fileSize += FileHelper.getDirSize(cacheDir);
		return fileSize;
	}
	
	public void clearAppCache() {
		 
        /*DataCleanManager.cleanInternalCache(this);
        DataCleanManager.cleanExternalCache(this);
        DataCleanManager.cleanDatabases(this);
        DataCleanManager.cleanSharedPreference(this);*/
        
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
        	String path = Environment.getExternalStorageDirectory().getAbsolutePath()
        			+ File.separator
        			+ CACHE_FOLDER_NAME;
        	//DataCleanManager.cleanCustomCache(path);
        }	       
	}
	 
	 
	public String getSaveImgPath() {
		 String savePath = null ;
		 String storageState = Environment.getExternalStorageState();
         if (storageState.equals(Environment.MEDIA_MOUNTED)) {
             savePath = Environment.getExternalStorageDirectory()
                     .getAbsolutePath() + "/jintao/Camera/";
             File savedir = new File(savePath);
             if (!savedir.exists()) {
                 savedir.mkdirs();
             }
         } else {
        	 savePath = getCacheDir().getAbsolutePath();
         }
         return savePath;
	}

	@SuppressWarnings("unused")
	@Override
	public void onLocationChanged(AMapLocation amapLocation) {
		int errCode = -1;
		if(amapLocation != null){
			if (amapLocation.getErrorCode() == 0) {
				//定位成功回调信息，设置相关消息
				amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
				amapLocation.getLatitude();//获取纬度
				amapLocation.getLongitude();//获取经度
				amapLocation.getAccuracy();//获取精度信息
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = new Date(amapLocation.getTime());
				df.format(date);//定位时间
				amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
				amapLocation.getCountry();//国家信息
				amapLocation.getProvince();//省信息
				amapLocation.getCity();//城市信息
				amapLocation.getDistrict();//城区信息
				amapLocation.getStreet();//街道信息
				amapLocation.getStreetNum();//街道门牌号信息
				amapLocation.getCityCode();//城市编码
				amapLocation.getAdCode();//地区编码
			} else {
				//显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
				Log.e("AmapError", "location Error, ErrCode:"
						+ amapLocation.getErrorCode() + ", errInfo:"
						+ amapLocation.getErrorInfo());
			}
        } 
		errCode = amapLocation.getErrorCode();
		//PrefManager.putInt(this, "user.location.errorCode", errCode);
		Log.i(TAG, "" + errCode);
	} 
	
	public void stopLocation() {
        mLocationClient.stopLocation();
	}
	
	public void logout() {
		stopLocation();
		clearAppCache();
		//AppManager.getAppManager().AppExit(this);
	}



	public interface OnLocationChangedListener {
		void notifyLocateResult(String result);
	}
}
