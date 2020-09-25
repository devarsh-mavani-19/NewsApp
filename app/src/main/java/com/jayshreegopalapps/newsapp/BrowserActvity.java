package com.jayshreegopalapps.newsapp;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class BrowserActvity extends AppCompatActivity {
    WebView webView;
    NewsModel model;
    String url;
    ProgressBar progressBar;
    long secondsSpent = 0;
    Timer t;
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser_actvity);
        // Makes Progress bar Visible
        initDB();
        webView=findViewById(R.id.web_view);
        progressBar = findViewById(R.id.progressBar);
        extractBundle();

        if(url!=null) {
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(url);
            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    setProgress(newProgress * 100);
                    if(newProgress == 100) {
                    }
                    System.out.println(newProgress);
                    super.onProgressChanged(view, newProgress);
                }
            });
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    progressBar.setVisibility(View.VISIBLE);
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                    t = new Timer();
                    t.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            secondsSpent++;
                        }
                    }, 0, 1000);
                    super.onPageFinished(view, url);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(request.getUrl().toString());
                    return true;
                }
            });
        }
    }

    private void initDB() {
        database = openOrCreateDatabase("UsageDB", MODE_PRIVATE, null);
    }

    private void extractBundle() {
        model = (NewsModel) getIntent().getSerializableExtra("NewsModel");
        url = getIntent().getStringExtra("url");
    }

    @Override
    protected void onDestroy() {
        t.cancel();
        String query = "update CategoryTable set value = value + " + secondsSpent + " where category_name = '" + model.getCategory() + "'";
        database.execSQL(query);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yy");
        long timestamp = System.currentTimeMillis();
        String time = sdf.format(new Date(timestamp));

        query = "update UsageStats set usage = usage + " + secondsSpent + " where u_day = '" + time + "'";
        database.execSQL(query);
        super.onDestroy();
    }
}
