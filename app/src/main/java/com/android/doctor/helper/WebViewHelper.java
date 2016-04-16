package com.android.doctor.helper;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ZoomButtonsController;

/**
 * Created by Yong on 2016/3/1.
 */
public class WebViewHelper {
    public final static String linkCss0 = "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common.css\">";
    public final static String linkCss = "<script type=\"text/javascript\" src=\"file:///android_asset/js/shCore.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/js/brush.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/js/client.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/js/detail_page.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/js/jquery.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/js/jquery.lazyload.min.js\"></script>"
            + "<script type=\"text/javascript\" src=\"file:///android_asset/js/lazyload.js\"></script>"
            + "<script type=\"text/javascript\">SyntaxHighlighter.all();</script>"
            + "<script type=\"text/javascript\">function showImagePreview(var url){window.location.url= url;}</script>"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/shCore.css\">"
            + "<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_asset/css/common.css\">";
    public final static String WEB_STYLE = linkCss;

    public static final String WEB_LOAD_IMAGES = "<script type=\"text/javascript\"> var allImgUrls = getAllImgSrc(document.body.innerHTML);</script>";

    private static final String SHOWIMAGE = "ima-api:action=showImage&data=";

    public static String setHtmlLazyLoadImg(String body) {

        body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
                "$1file:///android_asset/loading.gif\" data-original=\"$2\"");
        return body;
    }

    public static String setHtmlCotentSupportImagePreview(String body) {
        if (DeviceHelper.isWifiOpen()) {
            body = body.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1");
            body = body.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
            body = body.replaceAll("(<img[^>]+src=\")(\\S+)\"",
                    "$1$2\" onClick=\"showImagePreview('$2')\"");
        } else {
            body = body.replaceAll("<\\s*img\\s+([^>]*)\\s*>", "");
        }
        return body;
    }

    public static String getWebStyle(String body, float dpr) {
        return "<html><header><meta name=\"viewport\" content=\"width=device-width, initial-scale="
                + dpr +", user-scaleble=no \" /></header>" + body + "</html>";
    }

    @SuppressLint("SetJavaScriptEnabled")
    public static void initWebView(final WebView webView) {
        final WebSettings settings = webView.getSettings();
        settings.setDefaultFontSize(20);
        settings.setJavaScriptEnabled(true);
        //settings.setBlockNetworkLoads(true);
        //settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        //settings.setSupportZoom(true);
        //settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setTextSize(WebSettings.TextSize.LARGER);
        int sysVersion = Build.VERSION.SDK_INT;
        if (sysVersion >= 11) {
            settings.setDisplayZoomControls(false);
        } else {
            ZoomButtonsController zbc = new ZoomButtonsController(webView);
            zbc.getZoomControls().setVisibility(View.GONE);
        }
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out
                        .println("UIHelper.initWebView(...).new WebViewClient() {...}.onPageStarted()");
            }
            @Override
            public void onPageFinished(WebView view, String url) {

                //webView.loadUrl("javascript:jintao.resize(document.body.getBoundingClientRect().height)");
                webView.loadUrl("javascript:jintao.resize(document.body.scrollHeight)");
                //webView.loadUrl("javascript:jintao.resize(document.body.clientHeight)");
                super.onPageFinished(view, url);
            }
        });
        //webView.setWebViewClient(UIHelper.getWebViewClient());
    }
}
