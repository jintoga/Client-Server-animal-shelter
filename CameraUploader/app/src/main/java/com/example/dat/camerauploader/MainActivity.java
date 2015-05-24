package com.example.dat.camerauploader;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {
    ImageButton btnCapture, btnUpload;
    private ImageView imageView;
    Button btnBrowse, btnListAnimals;

    String picturePath;

    private Bitmap mImageBitmap; //Bitmap for Sending to Server

    static final int REQUEST_TAKE_PHOTO = 999;
    static final int REQUEST_BROWSE_PHOTO = 1000;

    String mCurrentPhotoPath;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    Bitmap bmFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }


    }


    public void addControls() {
        btnCapture = (ImageButton) findViewById(R.id.btnCapture);
        btnUpload = (ImageButton) findViewById(R.id.btnUpload);
        imageView = (ImageView) findViewById(R.id.Imageprev);
        btnBrowse = (Button) findViewById(R.id.btnBrowseFile);
        btnListAnimals = (Button) findViewById(R.id.btnListAnimals);
        btnUpload.setEnabled(false);
    }

    public void addEvents() {
        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPictureToServer();
            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processBrowsePicture();
            }
        });

        btnListAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GetListAnimals.class);
                startActivity(intent);
            }
        });
    }


    /**
     * Hàm xử ly lấy encode hình để gửi lên Server
     */
    private void uploadPictureToServer() {
        Log.e("path", "----------------" + picturePath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        //selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

        Log.e("base64", "-----" + ba1);
        Map<String, String> informationAnimal = new HashMap<>();
        informationAnimal.put("description", ba1);
        ServerRequest serverRequest = new ServerRequest(MainActivity.this);
        serverRequest.uploadingToServer(this, informationAnimal);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Toast.makeText(MainActivity.this, "requestCode:" + resultCode + "resultCode:" + resultCode + "", Toast.LENGTH_SHORT).show();

        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (mCurrentPhotoPath != null) {
                        setPic();
                        galleryAddPic();  //add image to Android Gallery
                        mCurrentPhotoPath = null;
                        btnUpload.setEnabled(true);
                    }
                } else {
                    if (mCurrentPhotoPath != null) {
                        File fileToDelte = new File(mCurrentPhotoPath);
                        fileToDelte.delete();
                        mCurrentPhotoPath = null;

                    }
                }
                break;
            case REQUEST_BROWSE_PHOTO:
                if (resultCode == RESULT_OK) {

                    Uri selectedimg = data.getData();
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg);
                        mImageBitmap = bitmap;
                        imageView.setImageBitmap(bitmap);
                        btnUpload.setEnabled(true);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                break;
            default:
                break;
        }

    }

    /**
     * Hàm hiển thị Camera folder và cho phép hiển thị hình người sử dụng chọn
     * lên giao diện, hình này sẽ được gửi lên Server nếu muốn
     */
    public void processBrowsePicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_BROWSE_PHOTO);
    }


    /* Photo album for this application */
    private String getAlbumName() {
        return getString(R.string.album_name);
    }

    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = mAlbumStorageDirFactory.getAlbumStorageDir(getAlbumName());

            if (storageDir != null) {
                if (!storageDir.mkdirs()) {
                    if (!storageDir.exists()) {
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);
        return imageF;
    }


    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File f = null;

        try {
            f = createImageFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException e) {
            e.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }


        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
    }

    //add Img to Android Gallery
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {

		/* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        mImageBitmap = bitmap;
        /* Associate the Bitmap to the ImageView */
        imageView.setImageBitmap(bitmap);

        imageView.setVisibility(View.VISIBLE);

    }

    public void setPic2(Bitmap bitmap) {
        Picasso.with(this)
                .load("http://localhost:4445/img/animals/109.jpg")
                .placeholder(R.drawable.ic_stub)
                .error(R.drawable.ic_error).fit()
                .noFade()

                .into(imageView);
       /* There isn't enough memory to open up more than a couple camera photos */
        /* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        /*int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
        imageView.setImageBitmap(resized);

        imageView.setVisibility(View.VISIBLE);*/

    }
}
