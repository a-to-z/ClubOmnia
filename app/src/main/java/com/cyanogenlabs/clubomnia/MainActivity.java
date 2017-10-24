package com.cyanogenlabs.clubomnia;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);


        listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(itemsAdapter);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        fetchPosts();


    }

    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);

        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            List<Post> posts = Arrays.asList(gson.fromJson(response, Post[].class));

            Log.i("PostActivity", posts.size() + " posts loaded.");
            ArrayList<ListItem> listMockData = new ArrayList<ListItem>();
            for (Post post : posts) {
                Log.i("PostActivity", post.ID + ": " + post.Name + " : " + post.price);

                ListItem newsData = new ListItem();
                newsData.setUrl(post.Image);
                newsData.setHeadline(post.Name);
                newsData.setReporterName("Pankaj Gupta");
                newsData.setDate("May 26, 2013, 13:35");
                listMockData.add(newsData);


                //itemsAdapter.add(post.Name);

            }
            //listView.setAdapter(itemsAdapter);

            listView.setAdapter(new CustomListAdapter(getApplicationContext(), listMockData));
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };

}
