package com.cyanogenlabs.clubomnia;


import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by Ammar on 10/25/2017.
 */

public class Categories implements Serializable {

    public long id;
    public String name;
    public Bitmap backGround;
    public String image;
    public byte bit[];
    public SerialBitmap bg;

    public Categories(long id, String name, byte bit[]){

        this.id = id;
        this.name = name;
        this.bit = bit;
    }

    public Categories(long id, String name, SerialBitmap bg){

        this.id = id;
        this.name = name;
        this.bg = bg;

    }

    public SerialBitmap getBg(){ return bg; }

    public int getBgLength(){ return bg.length; }

    public Categories(long id, String name, Bitmap backGround){

        this.id = id;
        this.name = name;
        this.backGround = backGround;

    }

    public Categories(long id, String name, String image){

        this.id = id;
        this.name = name;
        this.image = image;

    }

    public String getImage(){ return image; }

    public long getId(){ return id; }

    public String getName(){ return name; }

    public Bitmap getBackGround(){ return backGround; }
}
