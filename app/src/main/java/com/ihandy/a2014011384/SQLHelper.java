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
    static SQLiteDatabase categoryDB = null;
    static SQLiteDatabase newsDB = null;
    static SQLiteDatabase imageDB = null;
    static void onCreate(Activity app)
    {
        categoryDB = app.openOrCreateDatabase("category",android.content.Context.MODE_PRIVATE ,null);
        newsDB = app.openOrCreateDatabase("news.db",android.content.Context.MODE_PRIVATE ,null);
        imageDB = app.openOrCreateDatabase("image.db",android.content.Context.MODE_PRIVATE ,null);
        categoryDB.execSQL("CREATE TABLE IF NOT EXISTS Category(Name VARCHAR , Title VARCHAR , Prefer VARCHAR)");
        newsDB.execSQL("CREATE TABLE IF NOT EXISTS News(Category VARCHAR,Images VARCHAR,NewsId VARCHAR,Origin VARCHAR,Source VARCHAR,Title VARCHAR)");
        imageDB.execSQL("CREATE TABLE IF NOT EXISTS Image(Url VARCHAR,Image VARCHAR)");
    }

    static void dropAll()
    {
    }

    static void saveCategory(List<Category> arr)
    {
        for (int a=0;a<arr.size();a++)
        {
            Category cat = arr.get(a);
            Cursor cur = categoryDB.rawQuery("Select * from Category where Name = '" + cat.name+ "'",null);
            cur.moveToFirst();
            if (cur.getCount() == 0) categoryDB.execSQL("INSERT INTO Category VALUES('" + cat.name + "','" + cat.title + "','1')");
        }
    }

    static List<Category> readPreferCategory()
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
}
