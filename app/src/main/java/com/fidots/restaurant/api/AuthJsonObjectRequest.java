package com.fidots.restaurant.api;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

public class AuthJsonObjectRequest extends JsonObjectRequest {
	private String authToken;
	
	public AuthJsonObjectRequest(int method, String url,
			JSONObject jsonRequest, Listener<JSONObject> listener,
			ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
		// TODO Auto-generated constructor stub
	} 
	
	@Override
	public Map<String, String> getHeaders()
		throws AuthFailureError {						
		HashMap<String, String> headers = new HashMap<String, String>();
	       headers.put("X_AUTH_TOKEN", authToken);
		return headers;
	}

	
	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		
		try {
	        String jsonString = new String(response.data,
	                HttpHeaderParser.parseCharset(response.headers));
	        //Allow null
	        if (jsonString == null || jsonString.length() == 0) {
	            return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
	        }
	        return Response.success(new JSONObject(jsonString),
	                HttpHeaderParser.parseCacheHeaders(response));
	    } catch (UnsupportedEncodingException e) {
	        return Response.error(new ParseError(e));
	    } catch (JSONException je) {
	        return Response.error(new ParseError(je));
	    }
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
}
