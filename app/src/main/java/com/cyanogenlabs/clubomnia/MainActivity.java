package com.cyanogenlabs.clubomnia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.google.gson.Gson;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    private static final String ENDPOINT = "http://cyanogenlabs.com/omnia/categories.php";

    private RequestQueue requestQueue;

    private Gson gson;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private ImageView imageView;
    private ArrayList<Categories> arrayList;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        //gson = gsonBuilder.create();

        imageView = (ImageView) findViewById(R.id.imageView);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);


        listView = (ListView) findViewById(R.id.lvItems);
        //listView.setAdapter(itemsAdapter);

        //textView = (TextView) findViewById(R.id.textView);

        Bundle extra = getIntent().getBundleExtra("extra");
        arrayList = (ArrayList<Categories>) extra.getSerializable("listData");


        /*for(Categories listItem : arrayList){

                Log.i("MainActivity", listItem.getName());
            itemsAdapter.add(listItem.getName());



            //imageView.setImageBitmap(listItem.getBg());

            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            //options.inSampleSize = 1;

            //bitmap = BitmapFactory.decodeByteArray(listItem.getBg().bytes, 0, listItem.getBg().length,options);

            //imageView.setImageBitmap(bitmap);


        }*/

        listView.setAdapter(
                new ImageListAdapter(
                        MainActivity.this,
                        arrayList
                )
        );
/*
        Glide.with(this)
                .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")
                .into(imageView);*/



    }



}
