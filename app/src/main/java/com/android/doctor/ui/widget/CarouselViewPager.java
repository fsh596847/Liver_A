package com.android.doctor.ui.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.doctor.app.AppContext;
import com.android.doctor.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.List;


/**
 *
 * 
 * @author
 * 
 */
public class CarouselViewPager extends RelativeLayout {
	
	private CustomDurationViewPager viewPager;
	
	private LinearLayout indicateLayout;

	private Handler refreshHandler;

	private OnItemChangeListener onItemChangeListener;

	private OnItemClickListener onItemClickListener;
	
	private int totelCount = 0;
	
	private int currentIndex = 0;

	public static final int INDICATE_ARROW_ROUND_STYLE = 0;

	public static final int INDICATE_USERGUIDE_STYLE = 1;

	private int indicatorStyle = INDICATE_ARROW_ROUND_STYLE;

	private long refreshTime = 0l;
	
	private MyPagerAdapter adapter;
	
	public interface OnItemChangeListener {
		void onPosition(int position, int totalCount);
	}

	public interface OnItemClickListener {
		void OnItemClick(View view, int position);
	}

	public CarouselViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.init(context);
	}

	public CarouselViewPager(Context context) {
		super(context);
		this.init(context);
	}

	/**
	 * @param context
	 */
	private void init(Context context) {
		LayoutInflater.from(context).inflate(R.layout.image_indicator_layout, this);
		this.viewPager = (CustomDurationViewPager) findViewById(R.id.custom_view_pager);
		this.viewPager.setScrollDurationFactor(2);
		this.indicateLayout = (LinearLayout) findViewById(R.id.indicater_layout);

		this.viewPager.setOnPageChangeListener(new PageChangeListener());
		this.refreshHandler = new ScrollIndicateHandler(CarouselViewPager.this);
		this.adapter = new MyPagerAdapter(context);
		this.viewPager.setAdapter(adapter);
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public ViewPager getViewPager() {
		return viewPager;
	}

	/**
	 *
	 */
	public int getCurrentIndex() {
		return this.currentIndex;
	}

	/**
	 *
	 */
	public int getTotalCount() {
		return this.totelCount;
	}

	/**
	 * 
	 */
	public long getRefreshTime() {
		return this.refreshTime;
	}



	/**
	 * 
	 */
	private class ItemClickListener implements View.OnClickListener {
		private int position = 0;

		public ItemClickListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View view) {
			if (onItemClickListener != null) {
				onItemClickListener.OnItemClick(view, position);
			}
		}
	}

	
	public void setupImgURLs(final List<String> urlList) {
		if (urlList == null)
			throw new NullPointerException();

		final int len = urlList.size();
		this.totelCount = len;
		if (len > 0) {
			final LayoutParams params = (LayoutParams) indicateLayout.getLayoutParams();
			if (INDICATE_USERGUIDE_STYLE == this.indicatorStyle) {//
				params.bottomMargin = 45;
			}
			this.indicateLayout.setLayoutParams(params);
			this.indicateLayout.removeAllViews();
			// 
			for (int index = 0; index < this.totelCount; index++) {
				final ImageView indicater = new ImageView(getContext());
				
				this.indicateLayout.addView(indicater, index);
			}
			this.refreshHandler.sendEmptyMessage(currentIndex);
			// 
			this.viewPager.setCurrentItem(currentIndex, false);
			this.adapter.setURLs(urlList);
		}
	}
	/**
	 *
	 * 
	 * @param index
	 *            postion
	 */
	public void setCurrentItem(int index) {
		this.currentIndex = index;
	}

	/**
	 * 
	 * 
	 * @param style
	 * 
	 */
	public void setIndicateStyle(int style) {
		this.indicatorStyle = style;
	}

	/**
	 * 
	 * 
	 *
	 */
	public void setOnItemChangeListener(OnItemChangeListener onItemChangeListener) {
		if (onItemChangeListener == null) {
			throw new NullPointerException();
		}
		this.onItemChangeListener = onItemChangeListener;
	}

	/**
	 *
	 * 
	 * @param onItemClickListener
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	
	private class PageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int index) {
			currentIndex = index;
			refreshHandler.sendEmptyMessage(index);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	}

	/**
	 *
	 */
	protected void refreshIndicateView() {
		this.refreshTime = System.currentTimeMillis();

		for (int index = 0; index < totelCount; index++) {
			final ImageView imageView = (ImageView) this.indicateLayout.getChildAt(index);
			if (this.currentIndex == index) {
				imageView.setBackgroundResource(R.drawable.image_indicator_focus);
			} else {
				imageView.setBackgroundResource(R.drawable.image_indicator);
			}
		}

		if (this.onItemChangeListener != null) {// 
			try {
				this.onItemChangeListener.onPosition(this.currentIndex, this.totelCount);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ScrollIndicateHandler
	 */
	private static class ScrollIndicateHandler extends Handler {
		private final WeakReference<CarouselViewPager> scrollIndicateViewRef;

		public ScrollIndicateHandler(CarouselViewPager scrollIndicateView) {
			this.scrollIndicateViewRef = new WeakReference<CarouselViewPager>(
					scrollIndicateView);

		}

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			CarouselViewPager scrollIndicateView = scrollIndicateViewRef.get();
			if (scrollIndicateView != null) {
				scrollIndicateView.refreshIndicateView();
			}
		}
	}

	private class MyPagerAdapter extends PagerAdapter {
		
		private List<String> mURLs;
		private ImageLoader imageLoader;
		private LayoutInflater inflater;
		
		public MyPagerAdapter(Context context) {
			imageLoader = AppContext.getImageLoader();
			inflater = LayoutInflater.from(context);
		}
		
		public void setURLs(List<String> uRLs) {
			mURLs = uRLs;
			notifyDataSetChanged();
		}
		
		@Override
		public int getCount() {
			if (mURLs != null) {
				return mURLs.size();
			}
			return 0;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public int getItemPosition(Object object) {
			return super.getItemPosition(object);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			((ViewPager) container).removeView((View)object);
			System.gc();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView imageView = null;
			imageView = (ImageView)inflater.inflate(R.layout.imageview, null);
    		imageView.setScaleType(ScaleType.FIT_XY);
    		String url = mURLs.get(position);
    		if (url != null) {
    			//String thumbUrl = ApiService.get_image_thumbnails_500x500_url(url);
    			AppContext.getImageLoader().displayImage(url, imageView);
    			imageView.setOnClickListener(new ItemClickListener(position));		
    			container.addView(imageView);
    		}
			return imageView;
		}
	}
}
