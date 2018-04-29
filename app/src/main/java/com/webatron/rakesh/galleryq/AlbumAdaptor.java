package com.webatron.rakesh.galleryq;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rakesh on 4/4/18.
 */

public class AlbumAdaptor extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> data;

    public AlbumAdaptor(Context context, ArrayList<HashMap<String, String>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumImageViewHolder holder =null;
        if(convertView == null){
            holder = new AlbumImageViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.single_image_layout,parent,false);

            holder.albumimages = (ImageView)convertView.findViewById(R.id.albumimage);
            convertView.setTag(holder);
        }else holder = (AlbumImageViewHolder) convertView.getTag();

        holder.albumimages.setId(position);

        HashMap<String,String> temp = new HashMap<String, String>();
        temp = data.get(position);

        try{
            Glide.with(context).load(new File(temp.get(Funtion.KEY_PATH))).into(holder.albumimages);

        }catch (Exception e){
            Log.i("Exception",""+e);
        }
        return convertView;
    }

    class AlbumImageViewHolder {
        ImageView albumimages;
    }
}
