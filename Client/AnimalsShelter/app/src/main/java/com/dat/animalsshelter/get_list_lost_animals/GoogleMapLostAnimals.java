package com.dat.animalsshelter.get_list_lost_animals;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dat.animalsshelter.R;
import com.dat.animalsshelter.model.Animal;
import com.dat.animalsshelter.send_lost_animal.GPSTracker;
import com.dat.animalsshelter.send_lost_animal.SendLostAnimal;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class GoogleMapLostAnimals extends Activity {
    GoogleMap map;
    ProgressDialog myProgress;
    Spinner spinner_maps_type;

    ArrayAdapter<String> adapterMap;
    ArrayList<Animal> listAnimals = new ArrayList<>();

    MyInfoWindowAdapter infoWindowAdapter;

    /*ArrayList<MarkerOptions> listMarkers = new ArrayList<>();*/

    public HashMap<String, Animal> listMarkers = new HashMap<String, Animal>();

    public HashMap<String, Animal> getListMarkers() {
        return listMarkers;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map_lost_animals);
        getID();
        Intent callerIntent = getIntent();
        Bundle bundle = callerIntent.getBundleExtra("DATA");
        listAnimals = (ArrayList) bundle.getSerializable("LIST_ANIMALS");

        showMap();
        setEvents();
    }

    private void getID() {
        spinner_maps_type = (Spinner) findViewById(R.id.spinner_map_type2);
        Resources res = GoogleMapLostAnimals.this.getResources();

        String[] arrMap = res.getStringArray(R.array.maps_type);
        adapterMap = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrMap);
        adapterMap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_maps_type.setAdapter(adapterMap);
        spinner_maps_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                int type = GoogleMap.MAP_TYPE_NORMAL;
                switch (position) {

                    case 0:
                        type = GoogleMap.MAP_TYPE_NORMAL;
                        break;
                    case 1:
                        type = GoogleMap.MAP_TYPE_HYBRID;
                        break;

                }
                map.setMapType(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }


    public void setEvents() {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {



               /* map.moveCamera(center);
                map.animateCamera(center);*/
                marker.showInfoWindow();
                //Black magic... showInfoWindow again when Picasso load full image... temp solution
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Projection projection = map.getProjection();
                        Point markerScreenPosition = projection.toScreenLocation(marker.getPosition());
                        Point pointHalfScreenAbove = new Point(markerScreenPosition.x, (int) (markerScreenPosition.y - 200));
                        LatLng aboveMarkerLatLng = projection.fromScreenLocation(pointHalfScreenAbove);
                        CameraUpdate center = CameraUpdateFactory.newLatLng(aboveMarkerLatLng);
                        map.moveCamera(center);
                        map.animateCamera(center);
                        marker.showInfoWindow();
                    }
                }, 200);


                Toast.makeText(GoogleMapLostAnimals.this, "CLicked marker", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }


    public void showMap() {
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("Loading map ...");
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(true);
        myProgress.show();
        map = ((MapFragment) getFragmentManager().
                findFragmentById(R.id.map_lost_animals)).getMap();
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                myProgress.dismiss();
            }
        });
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);
        infoWindowAdapter = new MyInfoWindowAdapter(this);
        map.setInfoWindowAdapter(infoWindowAdapter);
        /*//move Camera to Barnaul
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(BARNAUL, 15));
        map.addMarker(new MarkerOptions().position(BARNAUL).title("Barnaul").snippet("My Barnaul"));*/

        //ADD MARKERS OF LOST ANIMALS
        for (Animal animal : listAnimals) {
            if (!animal.getLatitude().isEmpty() && !animal.getLongitude().isEmpty()) {
                if (!animal.getLatitude().equals("null") && !animal.getLongitude().equals("null")) {
                    double latitude = Double.parseDouble(animal.getLatitude().toString());
                    double longitude = Double.parseDouble(animal.getLongitude().toString());
                    LatLng location = new LatLng(latitude, longitude);
                    BitmapDescriptor icon;
                    if (animal.getSpecies().equals("Собака"))
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.dog);
                    else if (animal.getSpecies().equals("Кот"))
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.cat);
                    else {
                        icon = BitmapDescriptorFactory.fromResource(R.drawable.alien);
                    }
                    MarkerOptions markerOptions = new MarkerOptions().position(location).title(animal.getSpecies()).snippet("Lost animal!").icon(icon);

                    Marker marker = map.addMarker(markerOptions);
                    listMarkers.put(marker.getId(), animal);

                }
            }
        }

        // GPSTracker class
        GPSTracker gps = new GPSTracker(GoogleMapLostAnimals.this);
        // Check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            //
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            LatLng currentLocation = new LatLng(latitude, longitude);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
            //map.addMarker(new MarkerOptions().position(currentLocation).title("Location").snippet("Your Current Location"));
        } else {
            // Can't get location.
            // GPS or network is not enabled.
            // Ask user to enable GPS/network in settings.
            gps.showSettingsAlert();
        }


        /*map.moveCamera(CameraUpdateFactory.newLatLngZoom(HANOI, 15));
        map.addMarker(new MarkerOptions().position(HANOI).title("Hà Nội").snippet("Home sweet home"));
*/
    }
}
