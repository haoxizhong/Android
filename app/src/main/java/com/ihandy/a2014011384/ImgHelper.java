package com.ihandy.a2014011384;

import android.graphics.Bitmap;
import android.util.Base64;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.ByteArrayOutputStream;

/**
 * Created by lenovo on 2016/9/6.
 */

/**
 * The image class
 */
@DatabaseTable(tableName = "Image")
public class ImgHelper {
    /**
     * The url of image
     */
    @DatabaseField(id = true)
    public String url;
    /**
     * The image content
     */
    @DatabaseField
    public String image;
    ImgHelper(){}
    ImgHelper(String str1,Bitmap str2)
    {
        url = str1;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        str2.compress(Bitmap.CompressFormat.PNG, 100, stream);
        image = Base64.encodeToString(stream.toByteArray(),0);
    }
    ImgHelper(String str1,String str2)
    {
        url = str1;
        image = str2;
    }
}
