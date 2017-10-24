package com.cyanogenlabs.clubomnia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final String ENDPOINT = "http://cyanogenlabs.com/omnia/categories.php";

    private RequestQueue requestQueue;

    private Gson gson;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private ImageView imageView;
    private ArrayList<ListItem> listData;
    //private ListItem newsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();


        requestQueue = Volley.newRequestQueue(getApplicationContext());
        fetchPosts();



        // Start home activity
        //startActivity(new Intent(SplashActivity.this, MainActivity.class));
        // close splash activity
        //finish();
    }


    private void fetchPosts() {
        StringRequest request = new StringRequest(Request.Method.GET, ENDPOINT, onPostsLoaded, onPostsError);

        requestQueue.add(request);
    }

    private final Response.Listener<String> onPostsLoaded = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            List<Post> posts = Arrays.asList(gson.fromJson(response, Post[].class));
            listData = new ArrayList<ListItem>();
            //final int i = 0;


            Log.i("PostActivity", posts.size() + " posts loaded.");
            for (final Post post : posts) {
                Log.i("PostActivity", post.ID + ": " + post.Name + " : " + post.price);

                //itemsAdapter.add(post.Name);
                //final ListItem newsData = new ListItem();
                //newsData = new ListItem();



                ListItem newsData = new ListItem();
                newsData.setHeadline(post.Name);
                newsData.setUrl(post.Image);
                newsData.setReporterName("Pankaj Gupta");
                newsData.setDate("May 26, 2013, 13:35");
                listData.add(newsData);



            }

            Bundle extra = new Bundle();
            extra.putSerializable("listData",listData);
            Intent ii = new Intent(getApplicationContext(), MainActivity.class);
            ii.putExtra("extra", extra);
            startActivity(ii);
            finish();
            //listView.setAdapter(itemsAdapter);
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };

}
