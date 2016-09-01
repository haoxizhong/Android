package com.ihandy.a2014011384;

/**
 * Created by lenovo on 2016/8/30.
 */
public class Category {
    String name,title;
    Category()
    {
    }
    Category(String s1,String s2)
    {
        name = s1;
        title = s2;
    }
    @Override
    public String toString() {
        return "{ name : "+name+" , title : "+title+" }";
    }
}
