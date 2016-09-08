package com.ihandy.a2014011384;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQuery;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lenovo on 2016/9/1.
 */
class OrmHelper extends OrmLiteSqliteOpenHelper
{
    public OrmHelper(Context context, String databaseName, CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try
        {
            TableUtils.createTable(connectionSource,Category.class);
            TableUtils.createTable(connectionSource,News.class);
            TableUtils.createTable(connectionSource,ImgHelper.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
        try
        {
            TableUtils.dropTable(connectionSource,Category.class,true);
            TableUtils.dropTable(connectionSource,News.class,true);
            TableUtils.dropTable(connectionSource,ImgHelper.class,true);
            onCreate(sqLiteDatabase,connectionSource);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

public class SQLHelper
{
    public static Dao<Category,String> categoryDB = null;
    public static Dao<News,String> newsDB = null;
    public static Dao<ImgHelper,String> imageDB = null;
    public static OrmLiteSqliteOpenHelper helper = null;
    public static void onCreate(Context context)
    {
        helper = new OrmHelper(context, "database.db", null , 5);
        try
        {
            categoryDB = helper.getDao(Category.class);
            newsDB = helper.getDao(News.class);
            imageDB = helper.getDao(ImgHelper.class);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /*public static void onCreate()
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
    }*/

    public static void saveCategory(List<Category> arr)
    {
        for (int a=0;a<arr.size();a++)
        {
            Category cat = arr.get(a);
            try {
                List<Category> cate = categoryDB.queryBuilder().where().eq("name",cat.name).query();
                if (cate.size()!=0) cat.prefer = cate.get(0).prefer;
                categoryDB.createOrUpdate(cat);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void saveNews(List<News> arr)
    {
        for (int a=0;a<arr.size();a++)
        {
            News news = arr.get(a);
            try {
                List<News> newses = newsDB.queryBuilder().where().eq("news_id",news.news_id).query();
                if (newses.size() != 0) news.prefer = newses.get(0).prefer;
                newsDB.createOrUpdate(news);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void saveNews(News news)
    {
        try {
            newsDB.createOrUpdate(news);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void saveImage(ImgHelper img)
    {
        try {
            imageDB.createOrUpdate(img);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static List<Category> readPreferCategory()
    {
        try {
            return categoryDB.queryBuilder().where().eq("prefer",1).query();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static List<News> readNews(Category category) {
        //Log.d("test",category.toString());
        try
        {
            return newsDB.queryBuilder().orderBy("news_id",false).where().eq("category",category.name.toLowerCase()).or().eq("category",category.title.toLowerCase()).query();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static List<News> readPreferNews() {
        //Log.d("test",category.toString());
        try
        {
            return newsDB.queryBuilder().orderBy("news_id",false).where().eq("prefer",1).query();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap readImage(String url)
    {
        //Log.d("Image",url);
        try
        {
            //Log.d("ZZ",imageDB.queryBuilder().where().eq("url",url).query().toString());
            //Log.d("Image",imageDB.queryBuilder().where().eq("url",url).query().get(0).image);
            byte[] arr = Base64.decode(imageDB.queryBuilder().where().eq("url",url).query().get(0).image,0);
            return BitmapFactory.decodeByteArray(arr,0,arr.length);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}

