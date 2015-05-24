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

/**
 * Created by DAT on 5/23/2015.
 */
public class Animal {
    String imageString;
    String pk, name;

    String imageURL;

    File imageFile;
    Bitmap decodedByte = null;
    Context context;

    public Bitmap optimize(byte[] data) {
        BitmapFactory.Options opt;

        opt = new BitmapFactory.Options();
        opt.inTempStorage = new byte[16 * 1024];


        int height11 = 200;
        int width11 = 200;
        float mb = (width11 * height11) / 1024000;

        if (mb > 4f)
            opt.inSampleSize = 4;
        else if (mb > 3f)
            opt.inSampleSize = 2;

        //preview from camera
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opt);
        return bitmap;
    }

    public void convertImage() throws IOException {
        byte[] decodedString = Base64.decode(this.imageString, Base64.DEFAULT);
        //decodedByte = optimize(decodedString);
        decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        File file = new File(context.getCacheDir(), pk);
        file.createNewFile();

        Bitmap bitmap = decodedByte;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        this.imageFile = file;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Animal(String pk, String name, String imageString, Context context) {
        this.context = context;
        this.pk = pk;
        this.name = name;
        this.imageString = imageString;
        this.imageURL = "http://10.0.3.2:4445/img/animals/thumbnails/" + pk + ".jpg";
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

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    public static Bitmap decodeSampledBitmap(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // BitmapFactory.decodeResource(res, resId, options);
        BitmapFactory.decodeFile(path, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }
}
