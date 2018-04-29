package com.webatron.rakesh.galleryq;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ImagePreview extends AppCompatActivity {

    ViewPager viewPager;
    ViewPagerAdaptor adaptor;
    ArrayList<HashMap<String,String>> images = new ArrayList<HashMap<String, String>>();
    ImageView image;
    String path,name;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);

        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        name = intent.getStringExtra("name");
        images =(ArrayList<HashMap<String,String>>) intent.getSerializableExtra("mylist");
        position = intent.getIntExtra("position",0);
        //Log.i("String",""+images);
        //image = (ImageView)findViewById(R.id.imagpreview);
        //Glide.with(ImagePreview.this).load(new File(path)).into(image);

        ViewPagerAdaptor.flag=0;
        viewPager = (ViewPager)findViewById(R.id.gallerypager);
        adaptor = new ViewPagerAdaptor(ImagePreview.this,images,position);
        viewPager.setAdapter(adaptor);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
