package com.android.doctor.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.android.doctor.app.AppContext;
import com.android.doctor.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by Yong on 2016/3/1.
 */
public class TextSliderView extends BaseSliderView {
    public TextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.banner_layout,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        target.setScaleType(ImageView.ScaleType.FIT_XY);
        TextView description = (TextView)v.findViewById(R.id.description);
        description.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }

    protected void bindEventAndShow(final View v, ImageView targetImageView) {
        final BaseSliderView me = this;

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnSliderClickListener != null) {
                    mOnSliderClickListener.onSliderClick(me);
                }
            }
        });

        if (targetImageView == null)
            return;

        if (getUrl() == null) {
            return;
        }

        AppContext.getImageLoader().displayImage(getUrl(), targetImageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(v.findViewById(R.id.loading_bar) != null){
                    v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(v.findViewById(R.id.loading_bar) != null){
                    v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(v.findViewById(R.id.loading_bar) != null){
                    v.findViewById(R.id.loading_bar).setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
