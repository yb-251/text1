package com.example.text1.view.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.text1.R;
import com.example.text1.view.customs.RichWebView;

public class NoticeActivity extends AppCompatActivity {

    private RichWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        initView();
    }

    private void initView() {
        webView = (RichWebView) findViewById(R.id.webView);
        webView.loadUrl(getIntent().getStringExtra("url"));
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onCloseWindow(WebView window) {
                super.onCloseWindow(window);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.destroy();
        webView = null;
    }
}
