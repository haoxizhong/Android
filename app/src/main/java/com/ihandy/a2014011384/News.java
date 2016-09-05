package com.ihandy.a2014011384;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/9/1.
 */
public class News {
    String category;
    List<String> images;
    Long news_id;
    String origin;
    String source;
    String title;
    News()
    {
    }
    News(JSONObject obj) throws JSONException
    {
        category = obj.getString("category");
        JSONArray arr = obj.getJSONArray("imgs");
        images = new ArrayList<String>();
        for (int a=0;a<arr.length();a++)
            images.add(arr.getJSONObject(a).getString("url"));
        news_id = obj.getLong("news_id");
        origin = obj.getString("origin");
        source = obj.getJSONObject("source").getString("url");
        title = obj.getString("title");
    }
}
