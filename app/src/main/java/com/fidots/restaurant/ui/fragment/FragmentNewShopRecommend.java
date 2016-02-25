package com.fidots.restaurant.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fidots.restaurant.R;
import com.fidots.restaurant.ui.widget.CarouselViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Yong on 2016-02-14.
 */
public class FragmentNewShopRecommend extends Fragment{
    @InjectView(R.id.view_pager)
    protected CarouselViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend_new_shop, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        fillUI(null);
    }

    public void initView(View view) {

    }

    public void fillUI(Object obj) {
        /*if (obj == null) {
            return;
        }*/
        //autoBrocastManager.stop();
        //adsImageView.setupImgURLs(obj.getAds());
        //autoBrocastManager.setBroadCastTimes(5);//ѭ������
        List urls = new ArrayList();
        urls.add("http://img3.imgtn.bdimg.com/it/u=4011160966,1486942323&fm=21&gp=0.jpg");
        viewPager.setupImgURLs(urls);
        int interval = 3;
        try {
            //interval = (int) Float.parseFloat(ads.getCircle_interval());
        } catch (NumberFormatException exception) {
            //Log.e(MainAdsViewHolder.class.getSimpleName(), exception.toString());
        }
        //autoBrocastManager.setBroadcastEnable(true);
        //autoBrocastManager.setBroadcastTimeIntevel(interval * 1000, interval * 1000);//�״�����ʱ�估���
        //autoBrocastManager.loop();
    }

}
