package com.android.doctor.rest;

import android.util.Log;

import com.android.doctor.app.AppConfig;
import com.android.doctor.model.RespEntity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/4/25.
 */
public abstract class RespHandler<T> implements Callback<RespEntity<T>> {
    @Override
    public void onResponse(Call<RespEntity<T>> call, Response<RespEntity<T>> response) {
        if (response.isSuccessful()) {
            RespEntity<T> r = response.body();
            if (r == null) {
                defFailedInfo();
                Log.d(AppConfig.TAG, "[RespHandler-> onResponse ] response.body() is null" );
                return;
            }
            if (r.getError_code() == 0) {
                onSucceed(r);
            } else {
                onFailed(r);
                Log.d(AppConfig.TAG, "[RespHandler-> onResponse ] r.getError_code() " + r.getError_code());
            }
        } else {
            defFailedInfo();
            Log.d(AppConfig.TAG, "[RespHandler-> onResponse ] response.code " + response.code());
        }
    }

    @Override
    public void onFailure(Call<RespEntity<T>> call, Throwable t) {
        Log.d(AppConfig.TAG, "[RespHandler-> onFailure ] " + t.toString());
        defFailedInfo();
    }

    public void defFailedInfo() {
        RespEntity respEntity = new RespEntity();
        respEntity.setError_code(-1);
        respEntity.setError_msg("网络异常，请稍后重试");
        onFailed(respEntity);
    }

    public abstract void onSucceed(RespEntity<T> resp);
    public abstract void onFailed(RespEntity<T> resp);
}
