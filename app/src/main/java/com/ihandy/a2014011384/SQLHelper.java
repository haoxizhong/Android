package com.ihandy.a2014011384;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;

/**
 * Created by lenovo on 2016/9/1.
 */
public class SQLHelper {
    public static SQLiteDatabase categoryDB = null;
    public static SQLiteDatabase newsDB = null;
    public static SQLiteDatabase imageDB = null;
    public static void onCreate(Activity app)
    {
        categoryDB = app.openOrCreateDatabase("category",android.content.Context.MODE_PRIVATE ,null);
        newsDB = app.openOrCreateDatabase("news.db",android.content.Context.MODE_PRIVATE ,null);
        imageDB = app.openOrCreateDatabase("image.db",android.content.Context.MODE_PRIVATE ,null);
        categoryDB.execSQL("CREATE TABLE IF NOT EXISTS Category(Name VARCHAR , Title VARCHAR , Prefer VARCHAR)");
        newsDB.execSQL("CREATE TABLE IF NOT EXISTS News(Category VARCHAR,Images VARCHAR,NewsId BIGINT,Origin VARCHAR,Source VARCHAR,Title VARCHAR)");
        imageDB.execSQL("CREATE TABLE IF NOT EXISTS Image(Url VARCHAR,Image VARCHAR)");
    }

    public static void dropAll()
    {
    }

    public static void saveCategory(List<Category> arr)
    {
        for (int a=0;a<arr.size();a++)
        {
            Category cat = arr.get(a);
            Cursor cur = categoryDB.rawQuery("Select * from Category where Name = '" + cat.name+ "'",null);
            cur.moveToFirst();
            if (cur.getCount() == 0) categoryDB.execSQL("INSERT INTO Category VALUES('" + cat.name + "','" + cat.title + "','1')");
        }
    }

    public static void saveNews(List<News> arr)
    {
        for (int a=0;a<arr.size();a++)
        {
            News news = arr.get(a);
            Cursor cur = newsDB.rawQuery("Select * from News where NewsId = " + news.news_id ,null);
            cur.moveToFirst();
            if (cur.getCount() == 0) newsDB.execSQL("INSERT INTO Category VALUES('" + news.category + "','" + news.images + "'," + news.news_id + ",'" + news.origin + "','" + news.source + "','" + news.title +"')");
        }
    }

    public static List<Category> readPreferCategory()
    {
        Cursor cur = categoryDB.rawQuery("Select * from Category where Prefer='1'",null);
        cur.moveToFirst();
        int size = cur.getCount();
        List<Category> arr = new ArrayList<Category>();
        for (int a=0;a<size;a++)
        {
            arr.add(new Category(cur.getString(0),cur.getString(1)));
            cur.moveToNext();
        }
        return arr;
    }

    public static List<News> readNews(Category category)
    {
        Cursor cur = newsDB.rawQuery("Select * from News WHERE category='" + category.name +"'ORDER by NewsId",null);
        cur.moveToFirst();
        int size = cur.getCount();
        List<News> arr = new ArrayList<News>();

        for (int a=0;a<size;a++)
        {
            News news = new News();
            news.category = cur.getString(0);
            news.images = cur.getString(1);
            news.news_id = cur.getLong(2);
            news.origin = cur.getString(3);
            news.source = cur.getString(4);
            news.title = cur.getString(5);
            arr.add(news);
        }

        return arr;
    }
}
