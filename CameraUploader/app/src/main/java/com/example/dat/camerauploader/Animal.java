package com.example.dat.camerauploader;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by DAT on 5/23/2015.
 */
public class Animal implements Serializable {
    private static final long serialVersionUID = 1L;

    String imageString;
    String pk, name;

    String imageURLthumbnail, imageURL;

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURLthumbnail() {
        return imageURLthumbnail;
    }

    public void setImageURLthumbnail(String imageURLthumbnail) {
        this.imageURLthumbnail = imageURLthumbnail;
    }

    public Animal(String pk, String name, String imageString) {

        this.pk = pk;
        this.name = name;
        this.imageString = imageString;
        this.imageURLthumbnail = "http://10.0.3.2:4445/img/animals/thumbnails/" + pk + ".jpg";
        this.imageURL = "http://10.0.3.2:4445/img/animals/" + pk + ".jpg";
    }

    public Animal() {
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
