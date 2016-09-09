package com.ihandy.a2014011384;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by lenovo on 2016/9/8.
 */

/**
 * Category prefer adpater
 */
public class OtherListAdapter extends BaseAdapter {
    static Context context = null;
    static List<Category> category = null;

    @Override
    public int getCount()
    {
        return category.size();
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
        view = LayoutInflater.from(context).inflate(R.layout.drawer_layout,parent,false);
        TextView text = (TextView) view.findViewById(R.id.ttii);
        text.setText(category.get(position).title);
        if (category.get(position).prefer == 1) text.setBackgroundColor(Color.GREEN);
        else text.setBackgroundColor(Color.RED);
        return text;
    }
}
