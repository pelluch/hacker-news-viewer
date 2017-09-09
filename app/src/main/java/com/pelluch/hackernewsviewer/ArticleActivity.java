package com.pelluch.hackernewsviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class ArticleActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        webView = (WebView)findViewById(R.id.web_view);
        String url = getIntent().getStringExtra("url");
        webView.loadUrl(url);
    }
}
