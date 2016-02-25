package com.fidots.restaurant.api;

import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fidots.restaurant.app.AppContext;
import com.fidots.restaurant.app.PrefManager;
import com.fidots.restaurant.helper.StringHelper;

import android.content.Context;


public class ApiClient {
	@SuppressWarnings("unused")
	private static final String TAG = ApiClient.class.getSimpleName();
	
	@SuppressWarnings("unused")
	private static final int TIMEOUTMS = 30000;

    public static final String DELETE = "DELETE";
    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static RequestQueue requestQueue;
    private static ApiClient apiClient = null;
    private static AppContext application;

    
    private ApiClient(AppContext application) {
    	ApiClient.application = application;
    }

    public static synchronized ApiClient getClient(AppContext application) {
    	if (apiClient == null) {   		
    		apiClient = new ApiClient(application);
    	}
        return apiClient;
    }
    

    public static void clearUserCookies(Context context) {
        // (new HttpClientCookieStore(context)).a();
    }
    
    public void get(String url, String tag, NetworkResponseHandler handler) {   	
    	AuthJsonObjectRequest request = new AuthJsonObjectRequest(Method.GET, url, null, handler, handler);
    	String token = PrefManager.getUserToken(application);
		request.setAuthToken(token);
		System.out.println("ApiClient.get() X_AUTH_TOKEN:" + token);
		application.addToRequestQueue(request, tag);
    }
    
    public void post(String url, JSONObject param, String tag, NetworkResponseHandler handler) {
    	AuthJsonObjectRequest request = new AuthJsonObjectRequest(Method.POST, url, param, handler, handler);
		request.setAuthToken(PrefManager.getUserToken(application));
		application.addToRequestQueue(request, tag);
    }
    
    public void delete(String url, JSONObject jsonObject, String tag, NetworkResponseHandler handler) {
    	AuthJsonObjectRequest request = new AuthJsonObjectRequest(Method.DELETE, url, jsonObject, handler, handler);
		request.setAuthToken(PrefManager.getUserToken(application));
		application.addToRequestQueue(request, tag);
    }
    
    public void cancelAll(String tag) {
    	if (tag == null) {
    		return;
    	}
    	application.cancelRequests(tag);
    }
    
    public static void checkUpdate(String url, String tag, NetworkResponseHandler handler) {
    	JsonObjectRequest request = new JsonObjectRequest(Method.GET, url, null, handler, handler);
    	application.addToRequestQueue(request, tag);
    }
    
    public static boolean isNeedRefresh(Context context, String cacheKey, long interval) {
    	
		boolean refresh = PrefManager.getCacheIsNeedRefersh(context, cacheKey);
		
		String lastRefreshTime = PrefManager.getLastRefreshTime(context, cacheKey);
	    String currTime = StringHelper.getCurTimeStr();
	    long diff = StringHelper.calDateDifferent(lastRefreshTime, currTime);
	    
	    return diff > interval || refresh;
	}
}
