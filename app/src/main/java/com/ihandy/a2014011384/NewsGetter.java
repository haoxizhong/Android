package com.ihandy.a2014011384;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInput;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by lenovo on 2016/8/30.
 */
public class NewsGetter {
    public static List<Category> getCategory(String content)
    {
        List<Category> result = null;
        try {

            JSONObject json = new JSONObject(content).getJSONObject("data").getJSONObject("categories");
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<String, Object>();
            map = mapper.readValue(json.toString(), new TypeReference<Map<String, String>>() {
            });
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if (result == null) result = new ArrayList<Category>();
                result.add(new Category(pair.getKey().toString(), pair.getValue().toString()));
                it.remove();
            }
            System.out.println(result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public static List<News> getNewsByCategory(String content)
    {
        List<News> result = null;
        try {
            JSONArray json = new JSONObject(content).getJSONObject("data").getJSONArray("news");
            for (int a=0;a<json.length();a++)
            {
                JSONObject obj = json.getJSONObject(a);
                if (result == null) result = new ArrayList<News>();
                result.add(new News(obj));
                System.out.println(result);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        Log.d("Log",result+"");
        return result;
    }
}
