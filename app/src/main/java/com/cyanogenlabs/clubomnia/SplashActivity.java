package com.cyanogenlabs.clubomnia;

import java.io.ByteArrayOutputStream;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

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
import java.net.URLConnection;
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
    private ArrayList<Categories> bitmapArray;
    private ArrayList<Categories> test;
    private ArrayList<Categories> last;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
        gson = gsonBuilder.create();

        bitmapArray = new ArrayList<Categories>();
        last = new ArrayList<Categories>();


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

            test = new ArrayList<Categories>();


            Log.i("PostActivity", posts.size() + " posts loaded.");
            for (final Post post : posts) {
                Log.i("PostActivity", post.ID + ": " + post.Name + " : " + post.price);

                test.add(new Categories(post.ID , post.Name, post.Image));

            }

            Bundle extra = new Bundle();
            extra.putSerializable("listData",test);
            Intent ii = new Intent(getApplicationContext(), MainActivity.class);
            ii.putExtra("extra", extra);
            startActivity(ii);
            finish();
/*
            GetXMLTask xmlTask = new GetXMLTask();
            xmlTask.execute(test);*/
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };

}
