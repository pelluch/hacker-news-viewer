package com.pelluch.hackernewsviewer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import static com.pelluch.hackernewsviewer.adapters.ArticleAdapter.EXTRA_URL;

/**
 * Normally, if we wanted to support more layouts a fragment would be used
 * instead of just an activity
 * This mostly holds a web view
 */
public class ArticleActivity extends AppCompatActivity {

    private WebView webView;
    private TextView errorMessage;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        webView = (WebView)findViewById(R.id.web_view);
        errorMessage = (TextView)findViewById(R.id.error_message);

        String url = getIntent().getStringExtra(EXTRA_URL);
        // Setup back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView.loadUrl(url);
        // Set callbacks for showing error message and hiding progress bar
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                webView.setVisibility(View.INVISIBLE);
                errorMessage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                webView.setVisibility(View.VISIBLE);
                errorMessage.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }
}
