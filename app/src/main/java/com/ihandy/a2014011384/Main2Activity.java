package com.ihandy.a2014011384;

import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    static String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.d("URL",url);
        WebView web = (WebView) findViewById(R.id.webpage);
        WebSettings settings = web.getSettings();
        //settings.setJavaScriptEnabled(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        if (Build.VERSION.SDK_INT >= 19) {
            // chromium, enable hardware acceleration
            web.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        } else {
            // older android version, disable hardware acceleration
            web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        /*final Activity activity = this;
        web.setWebChromeClient(new WebChromeClient(){
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                activity.setProgress(progress * 1000);
            }
        });*/
        web.loadUrl(url);
        //web.loadData();
    }
}
