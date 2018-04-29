package com.webatron.rakesh.galleryq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rakesh on 3/4/18.
 */

public class GalleryAdaptor extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> data;

    public GalleryAdaptor(Context context, ArrayList<HashMap<String, String>> data) {
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
        AlbumViewHolder holder = null;
        if(convertView == null){
            holder = new AlbumViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.album_layout,parent,false);

            holder.galleryImage = (ImageView)convertView.findViewById(R.id.galleryimage);
            holder.count = (TextView)convertView.findViewById(R.id.gallerycount);
            holder.tittle = (TextView)convertView.findViewById(R.id.gallerytittle);

            convertView.setTag(holder);
        }else {
            holder = (AlbumViewHolder)convertView.getTag();
        }

        holder.galleryImage.setId(position);
        holder.count.setId(position);
        holder.tittle.setId(position);


        HashMap<String,String> temp = new HashMap<String, String>();

        temp = data.get(position);

        try{
            holder.tittle.setText(temp.get(Funtion.KEY_ALBUM));
            holder.count.setText(temp.get(Funtion.KEY_COUNT));

            Glide.with(context).load(new File(temp.get(Funtion.KEY_PATH))).into(holder.galleryImage);
        }catch (Exception e){

        }
        return convertView;
    }


    class AlbumViewHolder {
        ImageView galleryImage;
        TextView count,tittle;
    }

}
