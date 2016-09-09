package com.ihandy.a2014011384;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tencent.mm.sdk.openapi.IWXAPI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by lenovo on 2016/9/4.
 */

/**
 * The list adapter class
 */
public class ListAdapter extends BaseAdapter{
    static Context context = null;
    public Category category = null;
    Set<String> se = new HashSet<String>();

    @Override
    public int getCount()
    {
        if (category == null) return 0;
        if (InfStorage.news.containsKey(category)) return InfStorage.news.get(category).size();
        else return 0;
    }

    @Override
    public Object getItem(int position)
    {
        return  ""+position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    @Override
    public View getView(int position, View convertView , ViewGroup parent) {
        View view = null;
        try
        {
            final News news = InfStorage.news.get(category).get(position);
            final String url = news.images;
            view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
            TextView textView = (TextView) view.findViewById(R.id.title);
            textView.setText(news.title);
            final ImageView imageView = (ImageView) view.findViewById(R.id.image);
            Bitmap img = null;
            try
            {
                img = SQLHelper.readImage(url);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                img = null;
            }
            if (img == null) {
                Picasso.with(context).load(url).into(imageView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Bitmap map = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        SQLHelper.saveImage(new ImgHelper(url , map));
                    }

                    @Override
                    public void onError() {
                        final Bitmap map = SQLHelper.readImage("http://static.hdslb.com/error/404.png");

                        MainRunner.run(context.getMainLooper(), new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(map);
                            }
                        });
                    }
                });
            }
            else
            {
                imageView.setImageBitmap(img);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        if (position+1 >= getCount() && category != null && se.contains(position+"") == false) {
            se.add(position+"");
            createNewsByCategory(category,InfStorage.news.get(category).get(position).news_id);
        }
        return view;
    }

    /**
     * Generate the news by category
     * @param category The category needed to be done
     * @param id the news id
     */
    protected void createNewsByCategory(final Category category,final long id) {
        Map<String, String> map = new HashMap<>();
        map.put("locale", "en");
        map.put("category", category.name);
        map.put("max_news_id", id + "");
        OkHttpUtil.newCall("http://assignment.crazz.cn/news/query", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("No Network");

                List<News> arr = SQLHelper.readNews(category);

                InfStorage.news.put(category, SQLHelper.readNews(category));

                MainRunner.run(context.getMainLooper(), new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                int code = response.code();

                if (code == 200) SQLHelper.saveNews(NewsGetter.getNewsByCategory(str));
                else Log.d("Error Code", response.code() + "");

                List<News> arr = SQLHelper.readNews(category);

                InfStorage.news.put(category, SQLHelper.readNews(category));

                MainRunner.run(context.getMainLooper(), new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
