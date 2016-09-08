package com.ihandy.a2014011384;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/9/1.
 */

@DatabaseTable(tableName = "News")
public class News {
    @DatabaseField
    String category;
    @DatabaseField
    String images;
    @DatabaseField(id = true)
    Long news_id;
    @DatabaseField
    String origin;
    @DatabaseField
    String source;
    @DatabaseField
    String title;
    @DatabaseField
    int prefer = 0;
    News()
    {
    }
    News(JSONObject obj) throws JSONException {
        try{category = obj.getString("category").toLowerCase();}catch (Exception e){category = null;}
        try
        {
            JSONArray arr = obj.getJSONArray("imgs");
            for (int a=0;a<arr.length();a++)
                images = arr.getJSONObject(0).getString("url");
            if (images.length() == 0 || images == null) images = "http://static.hdslb.com/error/404.png";
        }catch (Exception e)
        {
            images = "http://static.hdslb.com/error/404.png";
        }
        try{category = obj.getString("category").toLowerCase();}catch (Exception e){category = null;}
        try{origin = obj.getString("origin");}catch (Exception e){origin = null;}
        try{source = obj.getJSONObject("source").getString("url"); if (source == null) source = "http://static.hdslb.com/error/404.png";}catch (Exception e){source ="http://static.hdslb.com/error/404.png";}
        try{title = obj.getString("title");}catch (Exception e){title = null;}
        news_id = obj.getLong("news_id");
    }
    @Override
    public String toString()
    {
        return "{Category:"+category+",Images:"+images+",News_id:"+news_id+",Origin:"+origin+",Source:"+source+",Title:"+title+" , Prefer : " + prefer + "}";
    }
}
