package com.ihandy.a2014011384;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2016/9/1.
 */
public class News {
    String category;
    String images;
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
        images = obj.getJSONObject("imgs").getString("url");
        news_id = obj.getLong("news_id");
        origin = obj.getString("origin");
        source = obj.getString("source");
        title = obj.getString("title");
    }
}
