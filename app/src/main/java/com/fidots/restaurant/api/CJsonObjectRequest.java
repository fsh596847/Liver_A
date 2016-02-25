package com.fidots.restaurant.api;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

public class CJsonObjectRequest extends JsonObjectRequest {

	public CJsonObjectRequest(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
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
}
