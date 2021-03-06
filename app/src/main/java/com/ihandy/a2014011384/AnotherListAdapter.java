package com.ihandy.a2014011384;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by lenovo on 2016/9/8.
 */

/**
 * Adapter of Favorite news
 */
public class AnotherListAdapter extends BaseAdapter{
    static Context context = null;
    static List<News> prefer =null;

    @Override
    public int getCount()
    {
        return prefer.size();
    }

    @Override
    public Object getItem(int position)
    {
        return  ""+position;
    }

    /**
     * get the ID
     * @param position the postion
     * @return the position
     */
    @Override
    public long getItemId(int position)
    {
        return position;
    }

    /**
     * Get the view at postion
     * @param position Override Value
     * @param convertView Overrode value
     * @param parent Override value
     * @return the view of the item at position
     */
    @Override
    public View getView(int position, View convertView , ViewGroup parent) {
        View view = null;
        final News news = prefer.get(position);
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
        return view;
    }
}
