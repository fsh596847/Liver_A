package com.android.doctor.helper.camera;

/**
 * Created by Yong on 2016/2/27.
 */
public interface CameraResult {

    void onSuccess(String path);

    void onFail(String path);
}
