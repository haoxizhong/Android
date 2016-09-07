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
        helper = new OrmHelper(context, "database.db", null , 4);
        try
        {
            categoryDB = helper.getDao(Category.class);
            newsDB = helper.getDao(News.class);
            imageDB = helper.getDao(ImgHelper.class);
            /*String str = "";
            str = str +"iVBORw0KGgoAAAANSUhEUgAAAoAAAAGrCAYAAABQTiZLAAAABHNCSVQICAgIfAhkiAAAIABJREFU\n";
            str = str +"eJzs3XecHVX5+PHPOTO3b2/Z3Wz6Zje9FxJCCkkAIYEElCKooKKgKPi1Ysev5WsD7KCiCCgEpUho\n";
            str = str +"IiWh+ZNOIL0TUnY32b63zsz5/TGb3iHJ7maf9+s1hNvmnrmz98xzT3kOCCGEEEIIIYQQQgghhBBC\n";
            str = str +"CCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQggh\n";
            str = str +"hBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQ\n";
            str = str +"QgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEII\n";
            str = str +"IYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGE\n";
            str = str +"EEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBC\n";
            str = str +"CCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQggh\n";
            str = str +"hBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQ\n";
            str = str +"QgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEII\n";
            str = str +"IYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGE\n";
            str = str +"EEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBC\n";
            str = str +"CCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQgghhBBCCCGEEEIIIYQQQggh\n";
            str = str +"hBBCCCGEEEIIIYQQQgghxNGyOroAQrwPk4ASYGtHF0QIIYToSuyOLoAQ78NoIAy81tEFEUIIIYQQ\n";
            str = str +"x58FPAtkgPEdXBYhhBBCCHECTC3oNyo9/aLrDPAKkN3RBRJCCCGEEMePAh6uOvcr5oZnHDNxyukG\n";
            str = str +"+HFHF0oIIYQQQhw/Z9uhmDvjW0+YTzyQMr99dJnJLyhMAbPaH9cdWTghhBCis5MLpehqIsB3y0ef\n";
            str = str +"qUuqJlJbswOnaDCf+vL3gsDNQB7g4bcSCiGEEOIAJAAUXc01wPjiwVNQlo2t4b8rdzD1gqs4ZdoZ\n";
            str = str +"Q4GvdHQBhRBCiM5OWklEVzIKWFw8cELOlC/9AysQxnguKcdjwsAieqXf5qr5U1pTycQk4O2OLqwQ\n";
            str = str +"QgjRWUkAKN6rIvw8fD2BYUCVHQwFLTuslLYsAwbP9dKphGfcdBJYDywDWoF3gZVAGxA/wvcbBvwj\n";
            str = str +"VtS7esY3HiVa2BN/t2CM3+d74aRSHrrx89zzp1/dA1xyLA9WCCGEOJlIACiOVBEwFpgBTNTaqs4p\n";
            str = str +"ryrLLquiYMBYYkW9yerRj0A0D2VZKAPG80g217FjzUu01qyjrW4D8fottGxb57qZRB1+ALhMwxqg\n";
            str = str +"0YPl+MFhBqgF3ml/74uAm3J6Diob89GfUTpsOplEy16Fc1xDj4IsppTs4AsXTHB2bK+bDrxwQj4Z\n";
            str = str +"IYQQoouRAFAcSgSYBnwIOCNW2KuisHI8xdWTKOg/luyySuxoLtqyMZ6HcR2M8fwmOQClUNpCWwGM\n";
            str = str +"8TCeg5NspaVmA9tWvEL9mldprm8k2dJIpmElpOr8l0FaKbXOGFNvQAVjeZMGnfN5Kmd+Ejuai5s6\n";
            str = str +"cKNhyjFMG17Kqvtu4A833rAQOA8wJ+BzEkKI40khdZk4xiQAFAdSDFwGXB7K7z2iz8S5lA6fQX7f\n";
            str = str +"0YRzS1Ba4zkZPCeN8dyj2K1CaY22A2grgGcUyXiKhrpGajdsIFm3HLf2ZWrfeCCejjfVK6Ureo0/\n";
            str = str +"jyHzv0Ze3xG4yVY81zno3j1jiITDnNEvzTcumWg2b9p4BvDk+/wshBDiRIsBFyul5il/mI1RkDFK\n";
            str = str +"Pe153i3s7h0R4j2TAFDsqQT4tIJP6oKRvXuM/zDDZ8+noLwnnufiplMY7+AB2HuhtEJrjbZDtG3f\n";
            str = str +"zEu/v5rmzcsoHjyVAadfQcng0zDG2zXe73DSrmF8VQ82PvwDbvv5tx/CbwUUQoiuYopW6veA4xmz\n";
            str = str +"wIK3AMeFQqXUhxWM9oy5Anisg8spujgJAAX4Xb1XKrjOKhzRLzzwQ/SdeDYDh/cnEHBxM5njXgCl\n";
            str = str +"LdJtjbz70oMUDZxIfr9RgMJJx3d3KR+BZMbQpySbSUV1XHPeuHhTY/0oYPVxK7gQQhw7s7RSD3jG\n";
            str = str +"fB+4EX889L4+ppX6lWfMucCiE1o6cVKRAFCcoeCHOpQ/Nlh1KTnV51I5spLyiiwwHp534oadKKWx\n";
            str = str +"QlGM6+BmUhzNkBfPgOsZKkvCzBicS3mx5n8+chGLH733WuCXx63QQghxbJRopd70jPlf4LeHee4H\n";
            str = str +"lVKXG2MuBZpOQNnEScju6AKIDlOh4QcGPmrnVRMa/jlKBk2gamgJOXlRPNc9moa3Y8IYDyfZetSv\n";
            str = str +"c1yDbSmmD85lYmWUSAyeX/QKK5a8AjDmmBdUCCGOMa31N4wxSzh88AfwD2PMNqAcCQDFeyQtgN3T\n";
            str = str +"xzR830BFoOcMYqOuoe+QavoOLCAQsHBdr6PLd8QyrqEgZjNndBGV5Yp1a7fwwF9+yT9u+zWJeBsK\n";
            str = str +"kgru8uCH+LkIhRCis8nXSi3xjPkY8HRHF0Z0DxIAdi8VWusf4XmXoRTBqsvIH30F1SP7UFKahWcM\n";
            str = str +"5gR2+b5fBhhUHqW6PId31r3Diw/8gv88eieN8Syigy4gkNMTr2UTybX/xGlau83A94Hf4eeNFkKI\n";
            str = str +"zuJMrdSvPWOGAamOLozoHiQA7D5masWtxjBAR4oJDf0UBUPOYcTYCrLzwjiZo0nn0jlYWuM0t/LK\n";
            str = str +"vx/mnWfvADwilWcQ7T0VZUfBuKBtTLqZ5Jr7Sa66F8+JP2zgc8CGDi6+EELsdL2C0wyc3dEFEd2H\n";
            str = str +"BIDdw5e0Uj8wxgSDPacSHvopskr6MXJ8T7JzozhO1wv+lFK4Hqx/ezmJmqVYWX2IO4UkEmnS8VY8\n";
            str = str +"1wUFWim0baMCMZwdb9P2xq9w6ldsNnAF8O+OPg4hhNBa/wlo9jzvuo4ui+g+JAA8uUW01r/A8640\n";
            str = str +"QLjqIqLDrkThMXJiX4p65HXJ4G9Plm2h7RDGc3HSKVLJDPGWBK3NCVoa22htihNvS+FkXHQgijJx\n";
            str = str +"4ktuIbXhsaSBrwM3dfQxCCG6N6317cAOz/O+2NFlEd2H1dEFEMdNnlLqXmXMBw0QrJimYiM/i5Nx\n";
            str = str +"qOhbQJ+BZV0++AMwnvFXJXFdlFIEghaxnCj5xdmU9CyktKKIorI8YtlhPDdNOmWwepyKHcqyve2v\n";
            str = str +"nekZE0NaAoUQHUgZM9oYUwXc29FlEd2HBIAnp0JLqYeMMVMMNEerLo5ljfo8xhgCNgwZ2w87YGNO\n";
            str = str +"dJ6XE8CY9qDQ8ye0aEsTiYYoKMmhtFcRBSXZaGXIRAZjRQrx6l491XXdMuBxZHKIEKIDGMjVSl1s\n";
            str = str +"/ElqJ1/FLDolCQBPPgWWUg97xpxqUMmsEVfnRYZ8FOM5uJk0PfuV0LNfCa7TfWIdY/yAEAXRrDAl\n";
            str = str +"5fkUlkQxOUNQwR5ktvxnnOM6vYGHkcpXCHHitSmlrjHwFLCtowsjugcJAE8u2Uqp+4wxU7HDiewx\n";
            str = str +"X4yF+5+LcZMYz8WybKpH9SUYDpyUrX+HYww4rr+6STgSpKQ0RnafMYSzymhc9fQo13PL8YNAIYQ4\n";
            str = str +"kZpRaprWup8x5vHDPFcB0fZtADAIqN5jGwj0B3oDxfjXea/9dV1/3I84ZmQSyMlDa7jbwIVYoUTW\n";
            str = str +"2C9FQr1nYzJtgMFxXEorChl5ysAulej5WHE9g9aK7JAm7RraUh5aQTBoY3SUmmdv";

            saveImage(new ImgHelper("404",str));*/
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
                newsDB.createOrUpdate(news);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
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

