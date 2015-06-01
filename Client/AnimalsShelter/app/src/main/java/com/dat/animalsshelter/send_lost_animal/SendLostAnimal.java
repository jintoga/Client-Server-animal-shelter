package com.dat.animalsshelter.send_lost_animal;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dat.animalsshelter.R;
import com.dat.animalsshelter.camera_stuff.AlbumStorageDirFactory;
import com.dat.animalsshelter.camera_stuff.BaseAlbumDirFactory;
import com.dat.animalsshelter.camera_stuff.FroyoAlbumDirFactory;
import com.dat.animalsshelter.internet_stuff.ServerRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SendLostAnimal extends Activity {

    Spinner spinnerSpecies2;
    EditText editTextDescription2, editTextPhone2, editTextDate, editTextAddress;

    ImageButton btnCapture2, btnUpload2, btnBrowse2, btnOpenMap;
    public ImageView imageView2;
    public LinearLayout linearLayoutBtnUpload2;

    String picturePath;
    private Bitmap mImageBitmap; //Bitmap for Sending to Server

    static final int REQUEST_TAKE_PHOTO = 999;
    static final int REQUEST_BROWSE_PHOTO = 1000;
    String mCurrentPhotoPath;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private static final String TAG_SPECIES = "species";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PHONE = "phone";
    private static final String TAG_DATE = "date";
    private static final String TAG_LOCATION = "location";

    private String animalSpecies, animalDescription, phoneNumber, location, date;

    Map<String, String> informationAnimal = new HashMap<>();

    public static final int RESULT_LOCATION = 119;
    public static final int REQUEST_ADDRESS_FROM_MAP = 120;
    public static final String KEY_ADDRESS = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_lost_animal);
        addControls();
        addEvents();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
            mAlbumStorageDirFactory = new FroyoAlbumDirFactory();
        } else {
            mAlbumStorageDirFactory = new BaseAlbumDirFactory();
        }
    }

    private void addControls() {
        btnCapture2 = (ImageButton) findViewById(R.id.btnCapture2);
        btnUpload2 = (ImageButton) findViewById(R.id.btnUpload2);
        imageView2 = (ImageView) findViewById(R.id.Imageprev2);
        btnBrowse2 = (ImageButton) findViewById(R.id.btnBrowse2);
        linearLayoutBtnUpload2 = (LinearLayout) findViewById(R.id.layoutforImgBtnUpload2);
        linearLayoutBtnUpload2.setVisibility(View.INVISIBLE);


        editTextDescription2 = (EditText) findViewById(R.id.editTextDescription2);
        editTextPhone2 = (EditText) findViewById(R.id.editTextPhone2);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        spinnerSpecies2 = (Spinner) findViewById(R.id.spinnerSpecies2);

        btnOpenMap = (ImageButton) findViewById(R.id.imageButtonOpenMap);

        editTextAddress = (EditText) findViewById(R.id.editTextAddress);

    }

    public void addEvents() {
        btnCapture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dispatchTakePictureIntent();
            }
        });
        btnUpload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editTextPhone2.getText().toString().isEmpty())
                    uploadPictureToServer();
                else {
                    Toast.makeText(SendLostAnimal.this, "Пожалуйста ввести номер телефона!", Toast.LENGTH_SHORT).show();
                    editTextPhone2.requestFocus();
                }
            }
        });

        btnBrowse2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processBrowsePicture();
            }
        });


        spinnerSpecies2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                /*Toast.makeText(getBaseContext(), spinnerSpecies.getSelectedItem().toString() + "Position:" + position,
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        btnOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SendLostAnimal.this, GoogleMapActivity.class);
                startActivityForResult(intent, REQUEST_ADDRESS_FROM_MAP);
            }
        });
    }


    /**
     * Encode image and send to Server
     */
    private void uploadPictureToServer() {
        Log.e("path", "----------------" + picturePath);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        //selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        mImageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bao);
        byte[] ba = bao.toByteArray();
        String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

        Log.e("base64", "-----" + ba1);

        informationAnimal.put("image", ba1);

        createInformationToSend();
        //Toast.makeText(SendAnimal.this, phoneNumber + "", Toast.LENGTH_SHORT).show();
        ServerRequest serverRequest = new ServerRequest();
        serverRequest.uploadingToServerLostAnimal(this, informationAnimal);
    }

    private void createInformationToSend() {
        phoneNumber = editTextPhone2.getText() + "";

        date = editTextDate.getText() + "";
        location = editTextAddress.getText() + "";
        animalSpecies = spinnerSpecies2.getSelectedItem().toString();

        animalDescription = editTextDescription2.getText() + "";


        informationAnimal.put(TAG_DATE, date + "");
        informationAnimal.put(TAG_LOCATION, location + "");
        informationAnimal.put(TAG_SPECIES, animalSpecies + "");
        informationAnimal.put(TAG_DESCRIPTION, animalDescription);
        informationAnimal.put(TAG_PHONE, phoneNumber + "");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Toast.makeText(SendAnimal.this, "requestCode:" + resultCode + "resultCode:" + resultCode + "", Toast.LENGTH_SHORT).show();


        switch (requestCode) {
            case REQUEST_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (mCurrentPhotoPath != null) {
                        setPic();
                        galleryAddPic();  //add image to Android Gallery
                        mCurrentPhotoPath = null;
                        linearLayoutBtnUpload2.setVisibility(View.VISIBLE);
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
                        imageView2.setImageBitmap(bitmap);

                        linearLayoutBtnUpload2.setVisibility(View.VISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
                break;
            case REQUEST_ADDRESS_FROM_MAP:
                if (resultCode == RESULT_LOCATION) {
                    String address = data.getStringExtra(KEY_ADDRESS);
                    editTextAddress.setText(address);

                }
                break;
            default:
                break;
        }

    }


    /**
     * Browse image from device
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
        int targetW = imageView2.getWidth();
        int targetH = imageView2.getHeight();

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
        imageView2.setImageBitmap(bitmap);

        imageView2.setVisibility(View.VISIBLE);

    }


    public void clearAfterSend() {
        editTextPhone2.setText("");
        editTextDescription2.setText("");
        spinnerSpecies2.setSelection(0);
        editTextDate.setText("");
        editTextAddress.setText("");
    }

}
