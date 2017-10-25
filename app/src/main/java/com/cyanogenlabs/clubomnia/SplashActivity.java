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
    private ArrayList<ListItem> listData;
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
            test = new ArrayList<Categories>();


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

                test.add(new Categories(post.ID , post.Name, post.Image));


            }
/*
            for(String[] ab : test){
                for(String cd : ab) {
                    Log.i("String test", cd);
                }
                Log.i("KEK", Arrays.toString(ab));
            }*/
/*
            Bundle extra = new Bundle();
            extra.putSerializable("listData",listData);
            Intent ii = new Intent(getApplicationContext(), MainActivity.class);
            ii.putExtra("extra", extra);
            startActivity(ii);
            finish();*/
            //listView.setAdapter(itemsAdapter);

            GetXMLTask xmlTask = new GetXMLTask();
            xmlTask.execute(test);
        }
    };

    private final Response.ErrorListener onPostsError = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("PostActivity", error.toString());
        }
    };

    private class GetXMLTask extends AsyncTask<ArrayList<Categories>, Void, ArrayList<Categories>> {
        @Override
        protected ArrayList<Categories> doInBackground(ArrayList<Categories>... urls) {
            //Bitmap map = null;

            for (ArrayList<Categories> url : urls) {



                last.add(new Categories(url.get(0).getId(), url.get(0).getName(), downloadImage(url.get(0).getImage()) ));
            }
            return last;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(ArrayList<Categories> result)
        {



            //            imageView.setImageBitmap(bitmapArray.get(0));
            Toast.makeText(SplashActivity.this, "downloaded", Toast.LENGTH_SHORT).show();
            Log.i("bitmap", "bitmap ");
            Bundle extra = new Bundle();
            extra.putSerializable("listData",result);
            Intent ii = new Intent(getApplicationContext(), MainActivity.class);
            ii.putExtra("extra", extra);
            startActivity(ii);
            finish();
        }

        // Creates Bitmap from InputStream and returns it
        private SerialBitmap downloadImage(String url) {
            SerialBitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = new SerialBitmap(stream, null, bmOptions);
                stream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            //bitmapArray.add(new Categories(1," ",bitmap));
            return bitmap;

        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString)
                throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }

    }

}
