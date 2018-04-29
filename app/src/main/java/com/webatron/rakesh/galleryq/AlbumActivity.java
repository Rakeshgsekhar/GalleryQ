package com.webatron.rakesh.galleryq;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AlbumActivity extends AppCompatActivity {

    GridView albumGridview;
    ArrayList<HashMap<String,String>> imagelist = new ArrayList<HashMap<String, String>>();
    String albumname = "";
    TextView tittle;
    ImageView imgback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        tittle = (TextView)findViewById(R.id.albumtittle);
        Intent intent = getIntent();
        albumname = intent.getStringExtra("name");
        tittle.setText(albumname);

        imgback = (ImageView)findViewById(R.id.albumbackbtn);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        albumGridview = (GridView)findViewById(R.id.albumgrid);
        int dispwidth = getResources().getDisplayMetrics().widthPixels;
        Resources resources = getApplicationContext().getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = dispwidth/(metrics.densityDpi/160f);

        if(dp < 360){
            dp = (dp-17)/2;
            float px = Funtion.convertDptoPixel(dp,getApplicationContext());
            albumGridview.setColumnWidth(Math.round(px));
        }
        new LoadAlbumImages().execute();

    }


    class LoadAlbumImages extends AsyncTask<String,Void, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            imagelist.clear();
        }

        @Override
        protected String doInBackground(String... strings) {
            String path =null;
            String album = null;
            String timestamp = null;

            Uri Ext= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri Int = MediaStore.Images.Media.INTERNAL_CONTENT_URI;

            String[] images = {MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME, MediaStore.MediaColumns.DATE_MODIFIED};

            Cursor cursorExternal = getContentResolver().query(Ext,images,"bucket_display_name =\""+albumname+"\"",null,null);
            Cursor cursorInternal = getContentResolver().query(Int,images,"bucket_display_name =\""+albumname+"\"",null,null);

            Cursor cursor = new MergeCursor(new Cursor[]{cursorExternal,cursorInternal});
            while (cursor.moveToNext()){

                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));

                album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));

                timestamp = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATE_MODIFIED));

                imagelist.add(Funtion.mappingInbox(album,path,timestamp,Funtion.convertToTime(timestamp),null));

            }
            cursor.close();

            Collections.sort(imagelist,new SortingFunction(Funtion.KEY_TIMESTAMP,"dsc"));
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            AlbumAdaptor adaptor = new AlbumAdaptor(AlbumActivity.this,imagelist);
            albumGridview.setAdapter(adaptor);
            albumGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent imgintent = new Intent(AlbumActivity.this,ViewImage.class);
                    imgintent.putExtra("path",imagelist.get(+position).get(Funtion.KEY_PATH));
                    imgintent.putExtra("name",imagelist.get(+position).get(Funtion.KEY_ALBUM));
                    imgintent.putExtra("mylist",imagelist);
                    imgintent.putExtra("position",position);
                    startActivity(imgintent);
                   // finish();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //new LoadAlbumImages().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AlbumActivity.this,Home.class);
        startActivity(intent);
        finish();
    }
}
