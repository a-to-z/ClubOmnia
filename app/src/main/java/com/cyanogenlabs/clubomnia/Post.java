package com.cyanogenlabs.clubomnia;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Ammar on 10/23/2017.
 */

public class Post {
    @SerializedName("id")
    long ID;

    @SerializedName("date")
    Date dateCreated;

    @SerializedName("name")
    String Name;

    @SerializedName("image")
    String Image;


    int price ;
}
