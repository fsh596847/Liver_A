package com.android.doctor.rest;

import com.android.doctor.app.AppContext;

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
}
