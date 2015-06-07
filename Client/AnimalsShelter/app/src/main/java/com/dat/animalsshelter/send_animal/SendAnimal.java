package com.dat.animalsshelter.send_animal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
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
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dat.animalsshelter.BaseActivity;
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

public class SendAnimal extends BaseActivity {

    Spinner spinnerSpecies, spinnerAge;
    EditText editTextName, editTextBreed, editTextWeight, editTextDescription, editTextPhone;
    RadioGroup radioGroupGender;
    CheckBox checkBoxSterilize;

    ImageButton btnCapture, btnUpload, btnBrowse;
    public ImageView imageView;
    public LinearLayout linearLayoutBtnUpload;

    CheckBox checkBoxMoreInfo;    //add more information about animal
    ScrollView scrollViewMoreInfo; //view to add more information

    String picturePath;

    private Bitmap mImageBitmap; //Bitmap for Sending to Server

    static final int REQUEST_TAKE_PHOTO = 999;
    static final int REQUEST_BROWSE_PHOTO = 1000;

    String mCurrentPhotoPath;
    private AlbumStorageDirFactory mAlbumStorageDirFactory = null;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private static final String TAG_SPECIES = "species";
    private static final String TAG_NAME = "name";
    private static final String TAG_BREED = "breed";
    private static final String TAG_GENDER = "gender";
    private static final String TAG_WEIGHT = "weight";
    private static final String TAG_AGE = "age";
    private static final String TAG_STERILIZE = "sterilize";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PHONE = "phone";

    private String animalSpecies, animalName, animalBreed, animalDescription, phoneNumber;
    private String animalWeight;
    private int animalAge, animalGender, animalSterilize;

    Map<String, String> informationAnimal = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_send_animal);
        getLayoutInflater().inflate(R.layout.activity_send_animal, frameLayout);

        listItemDrawer.setItemChecked(position, true);

        setTitle(listItemDrawer.getItemAtPosition(position) + "");

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
        imageView = (ImageView) findViewById(R.id.Imageprev1);
        btnBrowse = (ImageButton) findViewById(R.id.btnBrowse);
        linearLayoutBtnUpload = (LinearLayout) findViewById(R.id.layoutforImgBtnUpload);
        linearLayoutBtnUpload.setVisibility(View.INVISIBLE);

        checkBoxMoreInfo = (CheckBox) findViewById(R.id.checkBoxMoreInformation);

        scrollViewMoreInfo = (ScrollView) findViewById(R.id.scrollViewMoreInfo);
        scrollViewMoreInfo.setVisibility(View.INVISIBLE);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextBreed = (EditText) findViewById(R.id.editTextBreed);
        editTextWeight = (EditText) findViewById(R.id.editTextWeight);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);

        spinnerSpecies = (Spinner) findViewById(R.id.spinnerSpecies);
        spinnerAge = (Spinner) findViewById(R.id.spinnerAge);

        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);
        checkBoxSterilize = (CheckBox) findViewById(R.id.checkBoxSterilize);
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
                if (!editTextPhone.getText().toString().isEmpty())
                    uploadPictureToServer();
                else {
                    Toast.makeText(SendAnimal.this, "Пожалуйста ввести номер телефона!", Toast.LENGTH_SHORT).show();
                    editTextPhone.requestFocus();
                }
            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processBrowsePicture();
            }
        });


        final Animation animationExpand = AnimationUtils.loadAnimation(SendAnimal.this, R.anim.expand_more_infomation);
        final Animation animationCollapse = AnimationUtils.loadAnimation(SendAnimal.this, R.anim.collapse_more_information);

        checkBoxMoreInfo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    scrollViewMoreInfo.clearAnimation();
                    scrollViewMoreInfo.startAnimation(animationExpand);
                    scrollViewMoreInfo.setVisibility(View.VISIBLE);
                } else {
                    scrollViewMoreInfo.clearAnimation();
                    scrollViewMoreInfo.startAnimation(animationCollapse);
                    scrollViewMoreInfo.setVisibility(View.INVISIBLE);
                }
            }
        });

        spinnerSpecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                /*Toast.makeText(getBaseContext(), spinnerSpecies.getSelectedItem().toString() + "Position:" + position,
                        Toast.LENGTH_SHORT).show();*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                /*Toast.makeText(getBaseContext(), spinnerAge.getSelectedItem().toString() + "Position:" + position,
                        Toast.LENGTH_SHORT).show();*/
                //animalAge = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int memberID) {
                /*if (memberID == R.id.radioButtonMale) {
                    //Toast.makeText(MainActivity.this, "Male", Toast.LENGTH_SHORT).show();
                    animalGender = 1;
                } else if (memberID == R.id.radioButtonFemale) {
                    //Toast.makeText(MainActivity.this, "Female", Toast.LENGTH_SHORT).show();
                    animalGender = 2;
                }*/
            }
        });

        checkBoxSterilize.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                /*if (checked) {
                    //Toast.makeText(MainActivity.this, "Sterilized", Toast.LENGTH_SHORT).show();
                    animalSterilize = 1;
                } else {
                    animalSterilize = 2;
                }*/
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
        serverRequest.uploadingToServer(this, informationAnimal);
    }

    private void createInformationToSend() {
        phoneNumber = editTextPhone.getText() + "";

        if (checkBoxMoreInfo.isChecked()) {
            animalSpecies = spinnerSpecies.getSelectedItem().toString();
            animalName = editTextName.getText() + "";
            animalBreed = editTextBreed.getText() + "";
            if (radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonMale) {
                animalGender = 1;
            } else if (radioGroupGender.getCheckedRadioButtonId() == R.id.radioButtonFemale) {
                animalGender = 2;
            }
            animalAge = spinnerAge.getSelectedItemPosition() + 1;
            animalWeight = editTextWeight.getText() + "";
            if (animalWeight.isEmpty()) {
                animalWeight = "0";
            }
            if (checkBoxSterilize.isChecked()) {
                animalSterilize = 1;
            } else if (!checkBoxSterilize.isChecked()) {
                animalSterilize = 2;
            }
            animalDescription = editTextDescription.getText() + "";


        } else {
            animalSpecies = "Прочие";
            animalName = "";
            animalBreed = "";
            animalGender = 0;
            animalAge = 0;
            animalWeight = "0";
            animalSterilize = 0;
            animalDescription = "";


        }
        informationAnimal.put(TAG_SPECIES, animalSpecies + "");
        informationAnimal.put(TAG_NAME, animalName);
        informationAnimal.put(TAG_BREED, animalBreed);
        informationAnimal.put(TAG_GENDER, animalGender + "");
        informationAnimal.put(TAG_AGE, animalAge + "");
        informationAnimal.put(TAG_WEIGHT, animalWeight + "");
        informationAnimal.put(TAG_STERILIZE, animalSterilize + "");
        informationAnimal.put(TAG_DESCRIPTION, animalDescription);
        informationAnimal.put(TAG_PHONE, phoneNumber + "");
    }

    public void clearAfterSend() {
        editTextPhone.setText("");
        editTextName.setText("");
        editTextBreed.setText("");
        editTextWeight.setText("");
        editTextDescription.setText("");
        checkBoxMoreInfo.setChecked(false);
        checkBoxSterilize.setChecked(false);
        radioGroupGender.clearCheck();
        spinnerAge.setSelection(0);
        spinnerSpecies.setSelection(0);
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
                        linearLayoutBtnUpload.setVisibility(View.VISIBLE);
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

                        linearLayoutBtnUpload.setVisibility(View.VISIBLE);
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

}
