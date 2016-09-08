package com.ihandy.a2014011384;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by lenovo on 2016/9/8.
 */
public class OtherListAdapter extends BaseAdapter {
    static Context context = null;

    @Override
    public int getCount()
    {
        return SQLHelper.readPreferNews().size();
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
        final News news = SQLHelper.readPreferNews().get(position);
        final String url = news.images;
        view = LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
        return view;
    }
}
