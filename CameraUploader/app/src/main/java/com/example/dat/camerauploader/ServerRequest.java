package com.example.dat.camerauploader;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

//  create requests to server
public class ServerRequest {

    private ProgressDialog progressDialog = null;

    private final String TAG_NAME = "name";
    private final String TAG_PK = "pk";
    private final String TAG_IMAGE = "image";
    private GetListAnimals getListAnimals = null;
    private MainActivity activity = null;
    private ArrayList<Bitmap> listBitmap = new ArrayList<>();


    public ServerRequest(GetListAnimals getListAnimals) {
        this.getListAnimals = getListAnimals;
    }

    public ServerRequest(MainActivity activity) {
        this.activity = activity;
    }

    public void uploadingToServer(final Context context, final Map<String, String> informationAnimal) {

        final String myUrl = "http://10.0.3.2:4445/json";


        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Uploading to SERVER...Please Wait!");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, myUrl, informationAnimal, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonString) {


                Toast.makeText(context, "jsonObject" + jsonString, Toast.LENGTH_SHORT).show();
                try {

                    // Getting JSON Array node
                    JSONArray jsonArray = new JSONArray(jsonString);
                    Toast.makeText(context, jsonArray.toString(), Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString(TAG_NAME);
                        String pk = jsonObject.getString(TAG_PK);

                        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, pk, Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.hide();
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof ServerError) {
                    progressDialog.hide();
                    progressDialog.dismiss();
                    ServerError serverError = (ServerError) error;
                    Toast.makeText(context, "Server Err:" + serverError.toString() + informationAnimal.toString(), Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.hide();
                    progressDialog.dismiss();
                    Toast.makeText(context, "Other Err:" + error.toString() + informationAnimal.toString(), Toast.LENGTH_LONG).show();

                }
            }
        });

        AppController.getInstance(context).addToRequestQueue(jsObjRequest);

    }

    Context context = null;

    public ArrayList<Bitmap> downloadingFromServer(final Context context) {

        final String myUrl = "http://10.0.3.2:4445/loading";
        this.context = context;


        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading from SERVER...Please Wait!");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.GET, myUrl, null, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonString) {

                if (jsonString != null) {
                    try {

                        // Getting JSON Array node
                        JSONArray jsonArray = new JSONArray(jsonString);

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String name = jsonObject.getString(TAG_NAME);
                            String pk = jsonObject.getString(TAG_PK);
                            //String image = jsonObject.getString(TAG_IMAGE);

                           /* byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            getListAnimals.getListBitmap().add(decodedByte);*/

                            Animal animal = new Animal(pk, name, null);
                            /*try {
                                animal.convertImage();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }*/
                            getListAnimals.getListAnimal().add(animal);

                        }

                        getListAnimals.customAdapterGridview.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                progressDialog.hide();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof ServerError) {
                    progressDialog.hide();
                    progressDialog.dismiss();
                    ServerError serverError = (ServerError) error;
                    Toast.makeText(context, "Server Err:" + serverError.toString(), Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.hide();
                    progressDialog.dismiss();
                    Toast.makeText(context, "Other Err:" + error.toString(), Toast.LENGTH_LONG).show();

                }
            }
        });

        AppController.getInstance(context).addToRequestQueue(jsObjRequest);
        return listBitmap;
    }
}
