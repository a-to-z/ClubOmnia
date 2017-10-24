package com.cyanogenlabs.clubomnia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String ENDPOINT = "http://cyanogenlabs.com/omnia/categories.php";

    private RequestQueue requestQueue;

    private Gson gson;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private ImageView imageView;
    private ArrayList<ListItem> arrayList;
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

        //imageView = (ImageView) findViewById(R.id.imageView);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);


        listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);

        //textView = (TextView) findViewById(R.id.textView);

        Bundle extra = getIntent().getBundleExtra("extra");
        arrayList = (ArrayList<ListItem>) extra.getSerializable("listData");


        for(ListItem listItem : arrayList){

                Log.i("PostExecute", listItem.getHeadline());
            itemsAdapter.add(listItem.getHeadline());


        }



    }



}
