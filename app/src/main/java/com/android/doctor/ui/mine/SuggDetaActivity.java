package com.android.doctor.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ZoomButtonsController;

import com.android.doctor.R;
import com.android.doctor.app.AppConfig;
import com.android.doctor.app.AppContext;
import com.android.doctor.helper.UIHelper;
import com.android.doctor.model.ArticleList;
import com.android.doctor.model.RespEntity;
import com.android.doctor.model.User;
import com.android.doctor.rest.ApiService;
import com.android.doctor.rest.RestClient;
import com.android.doctor.ui.base.BaseActivity;
import com.android.doctor.app.DataCacheManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.HashMap;
import java.util.Map;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yong on 2016/5/13.
 */
public class SuggDetaActivity extends BaseActivity {

    @InjectView(R.id.fl_container)
    protected FrameLayout mWebViewContainer;
    @InjectView(R.id.img_favorite)
    protected ImageButton favorBtn;

    private WebView mWebView;
    private ArticleList.SuggestsEntity extra;

    public static void startAty(Context context, ArticleList.SuggestsEntity extra) {
        Intent intent = new Intent(context, SuggDetaActivity.class);
        intent.putExtra("extra", extra);
        context.startActivity(intent);
    }

    @Override
    protected void setContentLayout() {
        setContentView(R.layout.activity_web_deta);
    }

    @Override
    protected void init() {
        super.init();
        extra = getIntent().getParcelableExtra("extra");
    }

    @Override
    protected void initView() {
        super.initView();
        setActionBar(extra.getTitle());
        initWebView();
    }

    @Override
    protected void initData() {
        super.initData();
        boolean stat = checkIsCollected();
        favorBtn.setImageResource(stat ? R.drawable.star_pressed : R.drawable.star_unpressed);
        mWebView.loadUrl(geturl());
    }


    private void initWebView() {
        mWebView = new WebView(getApplicationContext());
        setWebView(mWebView);
        mWebView.setScrollContainer(true);
        mWebViewContainer.addView(mWebView);
    }

    public  void setWebView(final WebView webView) {
        final WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(14);
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setTextSize(WebSettings.TextSize.NORMAL);
        int sysVersion = Build.VERSION.SDK_INT;
        if (sysVersion >= 11) {
            settings.setDisplayZoomControls(false);
        } else {
            ZoomButtonsController zbc = new ZoomButtonsController(webView);
            zbc.getZoomControls().setVisibility(View.GONE);
        }
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(AppConfig.TAG, "setWebView-> onPageStarted()");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    private void recycleWebView() {
        mWebViewContainer.removeAllViews();
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        System.gc();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        recycleWebView();
        if (mWebView != null) {
            mWebView.destroy();
        }
    }

    public String geturl() {
        String url = RestClient.API_BASE_URL + "api/v1/"
                + "knowledge/" + extra.getUniquecode() + "/show.html";
        return url;
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.d(AppConfig.TAG, "[UMShareListener-> onError] " + t.getMessage());
            UIHelper.showToast(platform + " 分享失败啦");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    };

    private boolean checkIsCollected() {
        if (extra == null) return false;
        ArticleList.SuggestsEntity e = DataCacheManager.getInstance().findArticlesByID(extra.getUniquecode());
        return  e != null;
    }

    private void onCollectOper() {
        if (extra == null) return;
        final boolean stat = checkIsCollected();

        User.UserEntity u = AppContext.context().getUser();
        Map<String, String> param = new HashMap<>();
        param.put("uid", u.getDuid());
        param.put("suggid", "" + extra.getSuggid());
        param.put("status", String.valueOf(stat ? 1 : 0));
        ApiService apiService = RestClient.createService(ApiService.class);
        Call<RespEntity> call = apiService.collect(param);
        call.enqueue(new Callback<RespEntity>() {
            @Override
            public void onResponse(Call<RespEntity> call, Response<RespEntity> response) {
                UIHelper.showToast(stat ? "取消收藏成功" : "收藏成功");
                DataCacheManager.getInstance().onLoadCollectArticles();
                favorBtn.setImageResource(stat ? R.drawable.star_unpressed : R.drawable.star_pressed);
            }

            @Override
            public void onFailure(Call<RespEntity> call, Throwable t) {
                UIHelper.showToast(stat ? "取消收藏失败" : "收藏失败");
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.img_share)
    protected void onSocialShare() {
        if (isFastDoubleClick()) return;
        UMImage image = new UMImage(this, "http://www.umeng.com/images/pic/social/integrated_3.png");
        new ShareAction(this).setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.EMAIL)
                .withText(extra.getTitle())
                .withTitle(extra.getTitle())
                .withMedia(image)
                .setCallback(umShareListener)
                .open();
    }

    @OnClick(R.id.img_favorite)
    protected void onFavorite() {
        if (isFastDoubleClick()) return;
        onCollectOper();
    }
}
