package com.ihandy.a2014011384;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import okhttp3.internal.Util;

public class Main2Activity extends AppCompatActivity{
    static News news = null;
    private static final String appid = "wx01cee72f6183d77d";
    private static IWXAPI api = null;
    MenuItem love;

    void updateStatus(int prefer)
    {
        if (prefer == 0) love.setIcon(R.drawable.love_false);
        else love.setIcon(R.drawable.love_true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        love = menu.findItem(R.id.action_love);
        updateStatus(news.prefer);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //getActionBar().setTitle("");

        api = WXAPIFactory.createWXAPI(this,appid,true);
        api.registerApp(appid);

        Log.d("URL",news.source);
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
        web.loadUrl(news.source);
        //web.loadData();
    }

    void Share(int flag)
    {
        if (api.isWXAppInstalled() == false)
        {
            Toast.makeText(this, "您还未安装微信客户端",Toast.LENGTH_SHORT).show();
            return;
        }
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = news.source;

        WXMediaMessage msg = new WXMediaMessage();
        msg.title = news.title;
        msg.description = news.title;
        Bitmap thumb = BitmapFactory.decodeResource(getResources(),R.drawable.png404);
        msg.setThumbImage(thumb);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        if (flag == 0) req.scene = SendMessageToWX.Req.WXSceneSession;
        else req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);
    }

    void change_love()
    {
        news.prefer = 1 - news.prefer;
        updateStatus(news.prefer);
        SQLHelper.saveNews(news);
    }

    @Override
    public void onBackPressed()
    {
        if (Main3Activity.on3)
        {
            Intent intent = new Intent(getBaseContext(),Main3Activity.class);
            startActivity(intent);
            finish();
        }
        else super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_love:
                change_love();
                return true;
            case R.id.action_haoyou:
                Share(0);
                return true;

            //case R.id.action_pengyouquan:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
            //    Share(1);
            //    return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
