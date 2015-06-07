package com.dat.animalsshelter.internet_stuff;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.dat.animalsshelter.R;
import com.dat.animalsshelter.get_list_animals.GetListAnimals;
import com.dat.animalsshelter.get_list_lost_animals.GetListLostAnimals;
import com.dat.animalsshelter.model.Animal;
import com.dat.animalsshelter.send_animal.SendAnimal;
import com.dat.animalsshelter.send_lost_animal.SendLostAnimal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

//  create requests to server
public class ServerRequest {

    private ProgressDialog progressDialog = null;

    private final String TAG_NAME = "name";
    private final String TAG_PK = "pk";
    private final String TAG_SPECIES = "species";
    private final String TAG_BREED = "breed";
    private final String TAG_AGE = "age";
    private final String TAG_GENDER = "gender";
    private final String TAG_WEIGHT = "weight";
    private final String TAG_STERILIZE = "sterilize";
    private final String TAG_DESCRIPTION = "description";
    private final String TAG_DATE = "date";
    private final String TAG_LOCATION = "location";
    private final String TAG_LATITUDE = "latitude";
    private final String TAG_LONGITUDE = "longitude";

    private GetListAnimals getListAnimals = null;
    private GetListLostAnimals getListLostAnimals = null;
    private ArrayList<Bitmap> listBitmap = new ArrayList<>();


    public ServerRequest(GetListAnimals getListAnimals) {
        this.getListAnimals = getListAnimals;
    }

    public ServerRequest(GetListLostAnimals getListLostAnimals) {
        this.getListLostAnimals = getListLostAnimals;
    }

    public ServerRequest() {
    }

    public void uploadingToServerLostAnimal(final Context context, final Map<String, String> informationAnimal) {

        final String myUrl = "http://10.0.3.2:4445/lost_animal";


        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Uploading to SERVER...Please Wait!");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, myUrl, informationAnimal, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonString) {
                /*try {
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
                }*/

                Toast.makeText(context, "Uploaded Successfully. Thanks!", Toast.LENGTH_SHORT).show();
                ((SendLostAnimal) context).linearLayoutBtnUpload2.setVisibility(View.INVISIBLE);    //disable Upload button after uploaded successfully
                ((SendLostAnimal) context).imageView2.setImageResource(R.drawable.placeholder);  //empty the imageView
                ((SendLostAnimal) context).clearAfterSend();
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

    public void uploadingToServer(final Context context, final Map<String, String> informationAnimal) {

        final String myUrl = "http://10.0.3.2:4445/json";


        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Uploading to SERVER...Please Wait!");
        progressDialog.show();

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, myUrl, informationAnimal, new Response.Listener<String>() {
            @Override
            public void onResponse(String jsonString) {
                /*try {
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
                }*/

                Toast.makeText(context, "Uploaded Successfully. Thanks!", Toast.LENGTH_SHORT).show();
                ((SendAnimal) context).linearLayoutBtnUpload.setVisibility(View.INVISIBLE);    //disable Upload button after uploaded successfully
                ((SendAnimal) context).imageView.setImageResource(R.drawable.placeholder);  //empty the imageView
                ((SendAnimal) context).clearAfterSend();
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

                            String pk = jsonObject.getString(TAG_PK);
                            String name = jsonObject.getString(TAG_NAME);
                            String species = jsonObject.getString(TAG_SPECIES);
                            String breed = jsonObject.getString(TAG_BREED);
                            String gender = jsonObject.getString(TAG_GENDER);
                            String age = jsonObject.getString(TAG_AGE);
                            String weight = jsonObject.getString(TAG_WEIGHT);
                            String sterilize = jsonObject.getString(TAG_STERILIZE);
                            String description = jsonObject.getString(TAG_DESCRIPTION);


                            //String image = jsonObject.getString(TAG_IMAGE);

                           /* byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
                            decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            getListAnimals.getListBitmap().add(decodedByte);*/

                            Animal animal = new Animal(pk, species, name, breed, gender, age, weight, sterilize, description);
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

    public ArrayList<Bitmap> downloadingLostAnimalsFromServer(final Context context) {

        final String myUrl = "http://10.0.3.2:4445/loading_lost_animals";
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

                            String pk = jsonObject.getString(TAG_PK);
                            String species = jsonObject.getString(TAG_SPECIES);
                            String date = jsonObject.getString(TAG_DATE);
                            String location = jsonObject.getString(TAG_LOCATION);
                            String description = jsonObject.getString(TAG_DESCRIPTION);
                            String latitude = jsonObject.getString(TAG_LATITUDE);
                            String longitude = jsonObject.getString(TAG_LONGITUDE);


                            // Animal animal = new Animal(pk, species, name, breed, gender, age, weight, sterilize, description);
                            Animal animal = new Animal(pk, species, date, location, description, latitude, longitude);
                            getListLostAnimals.getListAnimal().add(animal);

                        }

                        getListLostAnimals.customAdapterGridview2.notifyDataSetChanged();

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
