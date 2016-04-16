package com.android.doctor.ui.fragment;

import android.view.View;

import com.daimajia.slider.library.SliderLayout;
import com.android.doctor.ui.base.BaseFragment;
import com.android.doctor.ui.widget.TextSliderView;
import com.android.doctor.R;

import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-14.
 */
public class FragmentBanner extends BaseFragment {
    @InjectView(R.id.slider)
    protected SliderLayout mSlider;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_banner;
    }

    @Override
    public void initView(View view) {
        fillView();
    }

    private  void initAdsView() {
        /*if (ad == null || ad.getMain_ads() == null || ad.getMain_ads().isEmpty()) {
            return;
        }
        mSlider.removeAllSliders();
        List<Ads.MainAdsEntity> list = ad.getMain_ads();
        for (int i = 0; i < list.size(); ++i) {
            Ads.MainAdsEntity entity = list.get(i);
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.description(entity.getAds_name())
                    .image(entity.getAds_image());
            //View v = textSliderView.getView();
            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", entity.getAds_name());
            mSlider.addSlider(textSliderView);
        }

        int interval = 3;
        try {
            interval = (int) Float.parseFloat(ad.getAd_interval());
        } catch (NumberFormatException exception) {
        }
        //mSlider.startAutoCycle(1000, interval * 1000, true);*/
        mSlider.startAutoCycle(1000, 3 * 1000, true);
    }

    public void fillView() {
        TextSliderView textSliderView = new TextSliderView(getContext());
        textSliderView.description("")
                .image("http://image01.baixingstatic.com/01/5090d9860f748045bb0b79807dbb9be3e0a3.jpg");
        //View v = textSliderView.getView();
        //add your extra information
        mSlider.addSlider(textSliderView);
    }

}
