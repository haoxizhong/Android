package com.ihandy.a2014011384;

import java.util.Iterator;
import java.util.Map;

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
        HttpUrl.Builder builder = HttpUrl.parse(url).newBuilder();
        Iterator it = map.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();
            builder.addQueryParameter(pair.getKey().toString(),pair.getValue().toString());
            it.remove();
        }
        return new Request.Builder().url(builder.build()).build();
    }

    public static void newCall(String url, Map<String,String> map, Callback callback)
    {
        Request request = buildRequest(url,map);
        client.newCall(request).enqueue(callback);
    }
}
