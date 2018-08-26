package me.mundane.acceleratedwebview.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import me.mundane.acceleratedwebview.R;
import me.mundane.acceleratedwebview.utils.DataHelper;
import me.mundane.acceleratedwebview.utils.WebViewUtil;

public class YuGangShuoWebActivity extends AppCompatActivity {
    
    private static final String TAG = "MainActivity";
    private final String IP_DOT_CN = "https://ip.cn/";
    private final String BAI_DU = "https://www.baidu.com/";
    private final String PNG_URL = "http://renyugang.io/wp-content/uploads/2018/06/cropped-ryg.png";
    private final String YU_GANG_SHUO_URL = "http://renyugang.io/post/75";
    private WebView mWebview;
    private TextView mTvLoadingTime;
    private long mStartTime;
    private long mEndTime;
    private static final String KEY_IS_LOAD_LOCAL = "is_load_local";
    private boolean mIsLoadLocal;
    private DataHelper mDataHelper;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获取从外面带过来的标记
        mIsLoadLocal = getIntent().getBooleanExtra(KEY_IS_LOAD_LOCAL, false);
        
        mWebview = (WebView) findViewById(R.id.web_view);
        mTvLoadingTime = (TextView) findViewById(R.id.tv_loading_time);
        WebViewUtil.configWebView(mWebview);
        mDataHelper = new DataHelper();
        mWebview.setWebViewClient(new WebViewClient() {
            
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }
            
            @Override
            public void onPageFinished(WebView view, String url) {
                mEndTime = System.currentTimeMillis();
                long loadingTime = mEndTime - mStartTime;
                mTvLoadingTime.setText(String.format("加载耗时%d毫秒", loadingTime));
                mStartTime = 0;
                mEndTime = 0;
            }
            
            // 设置不用系统浏览器打开,直接显示在当前Webview
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading1: url = " + url);
                view.loadUrl(url);
                return true;
            }
            
            // 该方法在5.0版本上可使用
            @RequiresApi(api = VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d(TAG, "shouldOverrideUrlLoading1: url = " + request.getUrl());
                return super.shouldOverrideUrlLoading(view, request);
            }
            
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d(TAG, "shouldInterceptRequest1: url = " + url);
                if (!mIsLoadLocal) {
                    return super.shouldInterceptRequest(view, url);
                }
                if (mDataHelper.hasLocalResource(url)) {
                    Log.d(TAG, "shouldInterceptRequest1: 资源命中");
                    WebResourceResponse response =
                            mDataHelper.getReplacedWebResourceResponse(getApplicationContext(),
                                    url);
                    if (response != null) {
                        return response;
                    }
                }
                return super.shouldInterceptRequest(view, url);
            }
            
            @TargetApi(VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                    WebResourceRequest request) {
                String url = request.getUrl().toString();
                Log.d(TAG, "shouldInterceptRequest2: url = " + url);
                if (!mIsLoadLocal) {
                    return super.shouldInterceptRequest(view, request);
                }
                if (mDataHelper.hasLocalResource(url)) {
                    Log.d(TAG, "shouldInterceptRequest2: 资源命中");
                    WebResourceResponse response =
                            mDataHelper.getReplacedWebResourceResponse(getApplicationContext(),
                                    url);
                    if (response != null) {
                        return response;
                    }
                }
                return super.shouldInterceptRequest(view, request);
            }
            
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        
        // 设置WebChromeClient类
        mWebview.setWebChromeClient(new WebChromeClient() {
            // 获取网站标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
            }
            
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    String progress = newProgress + "%";
                } else if (newProgress == 100) {
                    String progress = newProgress + "%";
                }
            }
        });
        String url = YU_GANG_SHUO_URL;
        mWebview.loadUrl(url);
        mStartTime = System.currentTimeMillis();
    }
    
    @NonNull
    private WebResourceResponse getReplacedWebResourceResponse() {
        InputStream is = null;
        // 步骤2:创建一个输入流
        try {
            is = getApplicationContext().getAssets().open("images/cropped-ryg.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 步骤4:替换资源
        WebResourceResponse response = new WebResourceResponse("image/png", "utf-8", is);
        return response;
    }
    
    // 点击返回上一页面而不是退出浏览器
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
            return true;
        }
        
        return super.onKeyDown(keyCode, event);
    }
    
    // 销毁Webview
    @Override
    protected void onDestroy() {
        if (mWebview != null) {
            mWebview.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebview.clearHistory();
            
            ViewParent parent = mWebview.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebview);
            }
            mWebview.destroy();
            mWebview = null;
        }
        super.onDestroy();
    }
    
    /**
     * @param isLoadLocal 是否从本地加载资源
     */
    public static void go2YuGangShuoActivity(Context context, boolean isLoadLocal) {
        Intent intent = new Intent(context, YuGangShuoWebActivity.class);
        intent.putExtra(KEY_IS_LOAD_LOCAL, isLoadLocal);
        context.startActivity(intent);
    }
}
