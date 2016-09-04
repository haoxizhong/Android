package com.ihandy.a2014011384;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by lenovo on 2016/9/4.
 */
public class ListAdapter extends BaseAdapter{
    static Context context = null;
    @Override
    public int getCount()
    {
        return 10;
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
    public View getView(int position, View converView , ViewGroup parent)
    {
        System.out.println("WK");
        TextView view = new TextView(context);
        view.setText("aaa");
        return view;
    }
}
