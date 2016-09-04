package com.ihandy.a2014011384;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by lenovo on 2016/9/4.
 */
public class ListAdapter extends BaseAdapter{
    static Context context = null;
    public Category category = null;

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
            News news = InfStorage.news.get(category).get(position);
            view = LayoutInflater.from(context).inflate(R.layout.list_item,null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return view;
    }
}
