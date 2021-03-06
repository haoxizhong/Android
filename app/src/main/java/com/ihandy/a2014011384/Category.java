package com.ihandy.a2014011384;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by lenovo on 2016/8/30.
 */

/**
 * The class if Category
 */
@DatabaseTable(tableName = "Category")
public class Category {
    /**
     * Category name
     */
    @DatabaseField(id = true)
    public String name;
    /**
     * Category title
     */
    @DatabaseField
    public String title;
    @DatabaseField
    /**
     * Whether to show category
     */
    public int prefer = 1;
    Category()
    {
    }
    Category(String s1,String s2)
    {
        name = s1;
        title = s2;
        prefer = 1;
    }
    @Override
    public String toString() {
        return "{ name : "+name+" , title : "+title+" , prefer : " + prefer + "}";
    }
}
