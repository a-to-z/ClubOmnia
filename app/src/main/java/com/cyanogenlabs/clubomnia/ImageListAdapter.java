package com.cyanogenlabs.clubomnia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static com.bumptech.glide.Glide.with;

/**
 * Created by Ammar on 10/25/2017.
 */

public class ImageListAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;

    private ArrayList<Categories> urls;

    public ImageListAdapter(Context context, ArrayList<Categories> urls) {
        super(context, R.layout.list_row_layout, urls);

        this.context = context;
        this.urls = urls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //final ImageView myImageView;
        RecyclerView.ViewHolder holder;
        //ImageView kek = (ImageView) inflater.inflate(R.layout.activity_main,null,false).findViewById(R.id.imageView);
        if (null == convertView) {
            convertView =  inflater.inflate(R.layout.list_row_layout, parent, false);

        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.thumbImage);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.fitCenter();
        requestOptions.centerCrop();

        Glide
                .with(context)
                .load(urls.get(position).getImage())
                .apply(requestOptions)
                .into(imageView);

        return convertView;
    }
}
