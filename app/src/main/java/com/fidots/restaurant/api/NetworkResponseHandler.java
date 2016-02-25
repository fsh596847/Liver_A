package com.fidots.restaurant.api;

import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.fidots.restaurant.helper.VolleyErrorHelper;

public abstract class NetworkResponseHandler implements ErrorListener, Listener<JSONObject> {
	
	//private Context context;
	
	public NetworkResponseHandler() {
		//this.context = context;
	}
	
	@Override
	public void onResponse(JSONObject arg0) {
		onSuccess(arg0);
	}
	
	@Override
	public void onErrorResponse(VolleyError error) {
		if ((error instanceof NetworkError) || (error instanceof TimeoutError)) {
			//Intent intent = new Intent(Constants.NET_ERROR_TIMEOUT());
            //LocalBroadcastManager.getInstance(AppContext.context()).sendBroadcast(intent);
		} else if (error instanceof NoConnectionError) {
			//Intent intent = new Intent(Constants.NET_ERROR_CONNECTION());
           // LocalBroadcastManager.getInstance(AppContext.context()).sendBroadcast(intent);
		} else if (VolleyErrorHelper.isAuthFailure(error)) {
			//UIHelper.reLogin(context);
		}
		if (error != null) {
			Log.e(NetworkResponseHandler.class.getSimpleName(), error.toString());
		}
		onFail(error);
	}
	
	public abstract void onSuccess(JSONObject jsonObject);
	
	public abstract void onFail(VolleyError error);
}
