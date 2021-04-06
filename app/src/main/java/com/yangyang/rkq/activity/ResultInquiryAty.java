package com.yangyang.rkq.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yangyang.rkq.R;
import com.yangyang.rkq.Utils.ConstantsUtil;
import com.yangyang.rkq.Utils.WebLoader;

public class ResultInquiryAty extends AppCompatActivity {
    private WebView webView;

    public static void start(Context context, String url){
        Intent intent = new Intent(context, ResultInquiryAty.class);
        intent.putExtra(ConstantsUtil.getInstance().INTENT_KEY_URL, url);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_result_inquiry);
        webView = findViewById(R.id.webView);

        initData();
    }

    private void initData() {
        String url = getIntent().getStringExtra(ConstantsUtil.getInstance().INTENT_KEY_URL);

        new WebLoader.Builder(webView)
                .baseUrl("https")
                .url(url)
                .javaScriptEnabled(true)
                .pageLoadListener(new WebLoader.OnWebPageLoadListener() {
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        Log.d("EasyAndroid", "---->" + url);
                    }
                })
                .shouldOverrideUrlLoadingListener(new WebLoader.OnShouldOverrideUrlLoadingListener() {
                    @Override
                    public void onShouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        Log.d("EasyAndroid", "-网页Url拦截--->" + request.getUrl());
                    }
                })
                .supportZoom(false)
                .build();
    }
}

