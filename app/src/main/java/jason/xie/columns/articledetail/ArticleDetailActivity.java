package jason.xie.columns.articledetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.classic.common.MultipleStatusView;

import jason.xie.columns.R;
import jason.xie.columns.defaultcolumns.DefaultColumnsContract;

/**
 * Created by Jason Xie on 2016/6/4.
 */

public class ArticleDetailActivity extends AppCompatActivity implements ArticleDetailContract.View {

    private MultipleStatusView mMutipleStatusView;
    private WebView mWebView;
    private String slug;
    private ArticleDetailPresenter mPresenter;

    @Override
    protected void onCreate(Bundle onSavedInstance){
        super.onCreate(onSavedInstance);
        setContentView(R.layout.activity_article_detail);
        mMutipleStatusView = (MultipleStatusView) findViewById(R.id.view_multiple_status);
        mWebView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(false);
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress > 10) {
                    mMutipleStatusView.showContent();
                    mWebView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {

            }

        });

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setProgressBarIndeterminateVisibility(false);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                setProgressBarIndeterminateVisibility(false);
                mWebView.setVisibility(View.INVISIBLE);
                if (errorCode == WebViewClient.ERROR_CONNECT || errorCode == ERROR_HOST_LOOKUP) {
                    mMutipleStatusView.showNoNetwork();
                } else {
                    mMutipleStatusView.showError();
                }
            }
        });

        slug = getIntent().getStringExtra("slug");
        mPresenter = new ArticleDetailPresenter(this);
        mPresenter.loadContent(slug);
    }

    @Override
    public void showLoading() {
        mMutipleStatusView.showLoading();
    }

    @Override
    public void showContent(String content) {
        mMutipleStatusView.showContent();
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/master.css\" type=\"text/css\">";
        String html = "<!DOCTYPE html>\n"
                + "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n"
                + "<head>\n"
                + "\t<meta charset=\"utf-8\" />\n</head>\n"
                + css
                + "\n<body>"
                + content
                + "</body>\n</html>";

        mWebView.loadDataWithBaseURL(null, html,"text/html","utf-8", null);
    }

    @Override
    public void showError() {
        mMutipleStatusView.showError();
    }

    @Override
    public void setPresenter(DefaultColumnsContract.Presenter presenter) {

    }

    @Override
    public Context getContext(){
        return this;
    }
}
