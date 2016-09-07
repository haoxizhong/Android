package com.ihandy.a2014011384;

import android.util.Log;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Exchanger;

import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by lenovo on 2016/8/31.
 */
public class OkHttpUtil {
    private static final OkHttpClient client = new OkHttpClient();

    private static Request buildRequest(String url,Map<String,String> map)
    {
        String str = url;
        Iterator it = map.entrySet().iterator();
        boolean first = true;
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            if (first) str = str + "?" + pair.getKey().toString() + "=" + pair.getValue().toString();
            else str =  str + "&" + pair.getKey().toString() + "=" + pair.getValue().toString();
            first=false;
            it.remove();
        }
        //Log.d("Log",str);
        //try{Thread.sleep(1000);}catch (Exception e){e.printStackTrace();}
        Request.Builder builder = new Request.Builder().url(str);
        return builder.build();
    }

    public static void newCall(String url, Map<String,String> map, Callback callback)
    {
        Request request = buildRequest(url,map);
        client.newCall(request).enqueue(callback);
    }
}
