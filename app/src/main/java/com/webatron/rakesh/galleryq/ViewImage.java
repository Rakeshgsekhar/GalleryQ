package com.webatron.rakesh.galleryq;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ViewImage extends AppCompatActivity {

    ImageView image,back;
    TextView tittle;
    String path,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.imageviewtoolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        name = intent.getStringExtra("name");
        //images =(ArrayList<HashMap<String,String>>) intent.getSerializableExtra("mylist");
        //position = intent.getIntExtra("position",0);
        //Log.i("String",""+images);
        tittle = (TextView)findViewById(R.id.tiitleimage);
        tittle.setText(name);
        back = (ImageView) findViewById(R.id.imageviewback);
        image = (ImageView)findViewById(R.id.viewimagpreview);
        Glide.with(ViewImage.this).load(new File(path)).into(image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.imagemenu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
