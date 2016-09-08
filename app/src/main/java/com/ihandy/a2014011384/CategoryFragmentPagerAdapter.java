package com.ihandy.a2014011384;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/9/1.
 */
public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {
    private Context context;

    public static void setCategory(List<Category> arr)
    {
        InfStorage.category = SQLHelper.readPreferCategory();
    }
    public CategoryFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return InfStorage.category.size();
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1,InfStorage.category.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        //return tabTitles[position];
        return InfStorage.category.get(position).title;
    }

    public static int getSize()
    {
        return InfStorage.category.size();
    }
}