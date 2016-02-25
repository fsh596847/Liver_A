package com.fidots.restaurant.app;

import com.amap.api.location.AMapLocation;
import com.fidots.restaurant.helper.StringHelper;
import com.fidots.restaurant.models.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.Space;

public class PrefManager {

	private static final String CODE_ONLINE_CONFIG = "jintao_config";
	public static final String LAST_REFRESH_TIME = "last_refresh_time.pref";
	public static final String CACHE_IS_NEED_REFERSH = "cache_is_need_refresh.pref";
	
	
	public static SharedPreferences getSharedPref(Context context) {
		if (context == null) {
			return null;
		}
		return context.getSharedPreferences(CODE_ONLINE_CONFIG, 
				Context.MODE_PRIVATE);
	}
	
	public static void putUserInfo(Context context, User user) {
		if (user == null) {
			return;
		}		
		SharedPreferences sp = getSharedPref(context);
		Editor editor = sp.edit();
		editor.putString("user.uid", user.getUser_id())
			.putString("user.username", user.getUsername())
			.putString("user.nickname", user.getUsername())
			.putString("user.phonenumber", user.getMobilephone_num())
			.putString("user.avatar", user.getAvatar())
			.putString("user.token", user.getToken())
			.commit();
	}
	
	public static void cleanUserInfo(Context context) {
		SharedPreferences sp = getSharedPref(context);
		Editor editor = sp.edit();
		editor.remove("user.uid")
			.remove("user.username")
			.remove("user.nickname")
			.remove("user.token")
			.remove("user.avatar")
			.remove("user.phonenumber")
			.commit();
	}
	
	public static User getUserInfo(Context context) {
		User user = new User();
		SharedPreferences sp = getSharedPref(context);
		user.setUser_id(sp.getString("user.uid", null));
		user.setUsername(sp.getString("user.username", null));
		user.setToken(sp.getString("user.token", null));
		user.setAvatar(sp.getString("user.avatar", null));
		user.setMobilephone_num(sp.getString("user.phonenumber", null));
		return user;
	}
	
	public static void putUserLocation(Context context, AMapLocation location) {
		if (location == null) {
			return;
		}	
		
		SharedPreferences sp = getSharedPref(context);
		Editor editor = sp.edit();
		editor.putInt("user.location.errorCode", location.getErrorCode())
			.putFloat("user.location.latitude", (float) location.getLatitude())
			.putFloat("user.location.longitude", (float)location.getLongitude())
			.putString("user.location.province", location.getProvince())
			.putString("user.location.city", location.getCity())
			.putString("user.location.district", location.getDistrict())
			.putString("user.location.citycode", location.getCityCode())
			.commit();
	}
	
	
	
	public static void cleanUserLocation(Context context) {
		SharedPreferences sp = getSharedPref(context);
		Editor editor = sp.edit();
		editor.remove("user.location.latitude")
			.remove("user.location.longitude")
			.remove("user.location.province")
			.remove("user.location.city")
			.remove("user.location.district")
			.remove("user.location.citycode")
			.commit();
	}
	
	public static String getUserCityCode(Context context) {
		SharedPreferences sp = getSharedPref(context);
		return sp.getString("user.location.citycode", "010");
	}
	
	public static float[] getUserCoord(Context context) {
		SharedPreferences sp = getSharedPref(context);
		float coord[] = new float[2];
		coord[0] = sp.getFloat("user.location.longitude", 116);
		coord[1] = sp.getFloat("user.location.latitude", 40);
		return coord;
	}
	
	public static void putString(Context context, String key, String value) {
		SharedPreferences sp = getSharedPref(context);
		sp.edit().putString(key, value).commit();
	}
	
	public static String get(Context context, String key) {
		SharedPreferences sp = getSharedPref(context);
		return sp.getString(key, null);
	}
	
	public static void putInt(Context context, String key, int value) {
		SharedPreferences sp = getSharedPref(context);
		sp.edit().putInt(key, value).commit();
	}
	
	public static int getInt(Context context, String key) {
		SharedPreferences sp = getSharedPref(context);
		return sp.getInt(key, -1);
	}
	
	public static void clearUserToken(Context context) {
		SharedPreferences sp = getSharedPref(context);
		sp.edit().remove("user.token").clear().commit();
	}
	
	public static void setIsFirstStart(Context context, boolean is){
		SharedPreferences sp = getSharedPref(context);
        sp.edit().putBoolean("app.is_first_start",is).commit();
    }

    public static boolean getIsFirstStart(Context context){
    	SharedPreferences sp = getSharedPref(context);
        return sp.getBoolean("app.is_first_start", true);
    }
    
    public static String getUserToken(Context context) {
    	SharedPreferences sp = getSharedPref(context);
		return sp.getString("user.token", null);
	}
    
    public static String getRetailStoreId(Context context) {
    	SharedPreferences sp = getSharedPref(context);
    	return sp.getString("retail_store.sid", "-1");
    }
    
    public static String getRetailStoreUserId(Context context) {
    	SharedPreferences sp = getSharedPref(context);
    	return sp.getString("retail_store.uid", "-1");
    }
    
    public static int getRetailStoreStatus(Context context) {
    	SharedPreferences sp = getSharedPref(context);
    	return sp.getInt("retail_store.status", -1);
    }        
    
    public static String getUserName(Context context) {
    	SharedPreferences sp = getSharedPref(context);
    	return sp.getString("user.username", "");
    }
    
    public static String getUserPhone(Context context) {
    	SharedPreferences sp = getSharedPref(context);
    	return sp.getString("user.phonenumber", "");
    }
    
    public static int getInstalledVersion(Context context) {
    	SharedPreferences sp = getSharedPref(context);
    	return sp.getInt("app.installed.version", -1);
    }
    
    public static void setInstalledVersion(Context context, int ver){
    	SharedPreferences sp = getSharedPref(context);
        sp.edit().putInt("app.installed.version", ver).commit();
    }
       
    public static void setDashboardLastUpdateDate(Context context,long date) {
    	SharedPreferences preferences = context.getSharedPreferences(LAST_REFRESH_TIME,
    			Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putLong("pref_update_date_dashboard", date).commit();
    }

    public static long getDashboardLastUpdateTime(Context context) {
    	SharedPreferences preferences = context.getSharedPreferences(LAST_REFRESH_TIME, 
    			Context.MODE_PRIVATE);
        return preferences.getLong("pref_update_date_dashboard", 0);
    }
    
    public static void putToLastRefreshTime(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(LAST_REFRESH_TIME, 
        		Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    
    public static void putCacheIsNeedRefersh(Context context, String key, boolean isNeedRefresh) {
        SharedPreferences preferences = context.getSharedPreferences(CACHE_IS_NEED_REFERSH, 
        		Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putBoolean(key, isNeedRefresh);
        editor.commit();
    }
    
    /**
     * 
     */
    public static String getLastRefreshTime(Context context, String key) {
    	SharedPreferences preferences = context.getSharedPreferences(LAST_REFRESH_TIME, Context.MODE_PRIVATE);
        return preferences.getString(key, StringHelper.getCurTimeStr());
    }
    
    public static boolean getCacheIsNeedRefersh(Context context, String key) {
    	SharedPreferences preferences = context.getSharedPreferences(CACHE_IS_NEED_REFERSH, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, true);
    }
    
    public static void clearPreference(Context context, String prefName) {
    	SharedPreferences sp = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    	sp.edit().clear().commit();
    }
    
    public static void removeKey(Context context, String prefName, String key) {
    	SharedPreferences sp = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    	sp.edit().remove(key).commit();
    }
}
