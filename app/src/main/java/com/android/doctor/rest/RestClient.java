package com.android.doctor.rest;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yong on 2016-02-25.
 */
public class RestClient {
    public static final String API_BASE_URL = "http://101.201.151.203:18080/";
    private ApiService apiService;

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken, TypeAdapterFactory factory) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        if (authToken != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .header("token", authToken)
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL);
        if (factory == null) {
            builder.addConverterFactory(GsonConverterFactory.create());
        } else {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(factory) // This is the important line ;)
                    .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
                    .create();
            builder.addConverterFactory(GsonConverterFactory.create(gson));
        }
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.connectTimeout(30, TimeUnit.SECONDS);
        httpClient.readTimeout(30, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public ApiService getApiService()
    {
        return apiService;
    }

    public static String getImgURL(String imgurl, float w, float h) {
        if (TextUtils.isEmpty(imgurl)) return null;
        if (imgurl.startsWith("http")) {
            if (w > 0 && h > 0) {
                String str = String.format(Locale.CHINA, "?imageView2/1/w/%f/h/%f", w, h);
                return imgurl.concat(str);
            } else {
                return imgurl;
            }
        } else {
            StringBuffer sb = new StringBuffer(API_BASE_URL);
            sb.append("api/v1/livercrm/").append(imgurl);
            if (w > 0 && h > 0) {
                String str = String.format(Locale.CHINA, "?w=%f&h=%f", w, h);
                sb.append(str);
            }
            return sb.toString();
        }
    }

    public static String getFaceURL(String uuid) {
        return new StringBuffer(API_BASE_URL)
                .append("api/v1/user/face.json?uuid=")
                .append(uuid).toString();
    }
}
