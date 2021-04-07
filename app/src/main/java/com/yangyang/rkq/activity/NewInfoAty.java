package com.yangyang.rkq.activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.yangyang.rkq.R;
import com.yangyang.rkq.base.MyActivity;
import com.yangyang.rkq.base.MyApplication;

/**
 * 新闻详情
 */

public class NewInfoAty extends MyActivity {
    private WebView wv_content;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_info);
        intView();
        intData();
    }


    private void intView() {
        wv_content=findViewById(R.id.wv_content);
        /**
         * 获得传递过来的数据
         */
        Intent intent = this.getIntent();

        String newsUrl = intent.getStringExtra("newsUrl");


        /**
         * 显示新闻信息
         */
        wv_content.loadUrl(newsUrl);
    }

    private void intData() {
    }

}
