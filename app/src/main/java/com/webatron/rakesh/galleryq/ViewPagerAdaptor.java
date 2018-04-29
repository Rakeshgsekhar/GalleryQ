package com.webatron.rakesh.galleryq;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rakesh on 4/4/18.
 */

public class ViewPagerAdaptor extends PagerAdapter {
    Context context;
    ArrayList<HashMap<String,String>> data;
    LayoutInflater inflater;
    static int flag =0;
    int pos;

    public ViewPagerAdaptor(Context context, ArrayList<HashMap<String, String>> data,int position) {
        this.context = context;
        this.data = data;
        this.pos = position;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemview = inflater.inflate(R.layout.image_preview_layout,container,false);

        ImageView image = (ImageView)itemview.findViewById(R.id.imagpreview);

        HashMap<String,String> temp = new HashMap<String, String>();
        if(flag == 0){
            position = pos;
            flag=1;

        }

        temp = data.get(position);
        try{

            Glide.with(context).load(new File(temp.get(Funtion.KEY_PATH))).into(image);
        }catch (Exception e){
            Log.i("Exception Load Image",""+e);
        }

        container.addView(itemview);
        return itemview;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager)container).removeView((View)object);
    }
}
