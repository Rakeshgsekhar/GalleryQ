package com.webatron.rakesh.galleryq;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Home extends AppCompatActivity {

    int MY_PERMISSION=0;
    GridView gallerygrid;
    LoadAlbum loadAlbum;
    ArrayList<HashMap<String,String>> albumlist = new ArrayList<HashMap<String, String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        CheckPermission();

        gallerygrid = (GridView)findViewById(R.id.gallerygrid);
        int dispwidth = getResources().getDisplayMetrics().widthPixels;
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        Float dp = dispwidth / (metrics.densityDpi/160f);

        if(dp<360){
            dp = (dp-17)/2;
            float px = Funtion.convertDptoPixel(dp,getApplicationContext());
            gallerygrid.setColumnWidth(Math.round(px));
        }

        new LoadAlbum().execute();
    }

    public void CheckPermission(){
        if(ContextCompat.checkSelfPermission(Home.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Home.this,new String[]{Manifest.permission.INTERNET,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            },MY_PERMISSION);
        }
    }

    class LoadAlbum extends AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            albumlist.clear();
        }

        @Override
        protected String doInBackground(String... strings) {
            String xml="";
            String path =null;
            String album = null;
            String timestamp = null;
            String countPhoto = null;
            Uri external = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri internal = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

            String[] imgdisp ={MediaStore.MediaColumns.DATA,MediaStore.Images.Media.BUCKET_DISPLAY_NAME,MediaStore.MediaColumns.DATE_MODIFIED};
            Cursor cursorExt = getContentResolver().query(external,imgdisp,"_data IS NOT NULL ) GROUP BY " +
                    "(bucket_display_name",null,null);
            Cursor cursorInt = getContentResolver().query(internal,imgdisp,"_data IS NOT NULL ) GROUP BY " +
                    "(bucket_display_name",null,null);
            Cursor cursor = new MergeCursor(new Cursor[]{cursorExt,cursorInt});

            while(cursor.moveToNext()){
                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
                album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));
                countPhoto = Funtion.getCount(getApplicationContext(),album);

                albumlist.add(Funtion.mappingInbox(album,path,timestamp,Funtion.convertToTime(timestamp),countPhoto));
            }
            cursor.close();

            Collections.sort(albumlist,new SortingFunction(Funtion.KEY_TIMESTAMP,"dsc"));

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            GalleryAdaptor adaptor = new GalleryAdaptor(Home.this,albumlist);
            gallerygrid.setAdapter(adaptor);
            gallerygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent intent = new Intent(Home.this,AlbumActivity.class);
                    intent.putExtra("name",albumlist.get(+position).get(Funtion.KEY_ALBUM));
                    Toast.makeText(Home.this,"Clicked",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                    finish();
                }
            });

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       // new LoadAlbum().execute();
    }
}
