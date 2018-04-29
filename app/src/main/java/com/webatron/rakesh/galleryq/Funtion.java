package com.webatron.rakesh.galleryq;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.DisplayMetrics;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by rakesh on 3/4/18.
 */

public class Funtion {

    static final String KEY_ALBUM = "album_name";
    static final String KEY_PATH = "path";
    static final String KEY_TIMESTAMP = "timestamp";
    static final String KEY_TIME = "date";
    static final String KEY_COUNT = "count";

    public static float convertDptoPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp*(metrics.densityDpi/160f);
        return px;
    }

    public static HashMap<String,String> mappingInbox(String album,String path,String timestamp,String time,String count){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put(KEY_ALBUM,album);
        map.put(KEY_PATH,path);
        map.put(KEY_TIMESTAMP,timestamp);
        map.put(KEY_TIME,time);
        map.put(KEY_COUNT,count);
        return map;
    }
    public static String getCount(Context context,String album_name){
        Uri Ext = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Uri Inter = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        String[] imgdisp = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
        MediaStore.MediaColumns.DATE_MODIFIED};

        Cursor cursorExt = context.getContentResolver().query(Ext,imgdisp,"bucket_display_name =\""+album_name+"\"",null,null);
        Cursor cursorInt = context.getContentResolver().query(Inter,imgdisp,"bucket_display_name =\""+album_name+"\"",null,null);
        Cursor cursor = new MergeCursor(new Cursor[]{cursorExt,cursorInt});


        return cursor.getCount()+"Photos";
    }

    public static String convertToTime(String timestamp){
        long datetime = Long.parseLong(timestamp);
        Date date = new Date(datetime);
        DateFormat format = new SimpleDateFormat("dd/MM HH:mm");
        return format.format(date);
    }


}
