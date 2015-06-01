package com.dat.animalsshelter.send_lost_animal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.dat.animalsshelter.R;

import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends Activity {
    static final LatLng BARNAUL = new LatLng(53.3547792, 83.7697832);
    static final LatLng HANOI = new LatLng(21.0277644, 105.8341598);
    GoogleMap map;
    ProgressDialog myProgress;
    Spinner spinner_maps_type;

    ArrayAdapter<String> adapterMap;

    EditText editTextAddress;

    Button accept, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        getID();
        showMap();
        setEvents();
    }

    public void showMap() {
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("Loading map ...");
        myProgress.setMessage("Please wait...");
        myProgress.setCancelable(true);
        myProgress.show();
        map = ((MapFragment) getFragmentManager().
                findFragmentById(R.id.map)).getMap();
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                myProgress.dismiss();
            }
        });
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);

        /*//move Camera to Barnaul
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(BARNAUL, 15));
        map.addMarker(new MarkerOptions().position(BARNAUL).title("Barnaul").snippet("My Barnaul"));*/

        // GPSTracker class
        GPSTracker gps = new GPSTracker(GoogleMapActivity.this);
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

    private void getID() {
        accept = (Button) findViewById(R.id.buttonAcceptAddress);
        cancel = (Button) findViewById(R.id.buttonCancelMap);
        editTextAddress = (EditText) findViewById(R.id.editTextAddressInMap);
        spinner_maps_type = (Spinner) findViewById(R.id.spinner_map_type);
        Resources res = GoogleMapActivity.this.getResources();

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
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                map.clear();

                // Animating to the touched position
                map.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                map.addMarker(markerOptions);
                locationName(markerOptions.getPosition().latitude, markerOptions.getPosition().longitude);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addressResult = editTextAddress.getText() + "";
                if (!addressResult.isEmpty()) {
                    Intent intent = new Intent(GoogleMapActivity.this, SendLostAnimal.class);
                    Bundle bundle = new Bundle();
                    bundle.putString(SendLostAnimal.KEY_ADDRESS, addressResult);
                    intent.putExtras(bundle);
                    setResult(SendLostAnimal.RESULT_LOCATION, intent);
                    finish();
                } else {
                    Toast.makeText(GoogleMapActivity.this, "Пожалуйста выбрать адрес на карте", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void locationName(double lat, double lng) {
        try {
            Geocoder geo = new Geocoder(GoogleMapActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(lat, lng, 1);
            if (addresses.isEmpty()) {
                //Toast.makeText(MainActivity.this, "Waiting for Location" + "lat-long:" + lat + "-" + lng, Toast.LENGTH_SHORT).show();
            } else {
                if (addresses.size() > 0) {
                    String fullAddress = addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
                    //Toast.makeText(GoogleMapActivity.this, "Address: " + fullAddress, Toast.LENGTH_SHORT).show();
                    editTextAddress.setText(fullAddress);
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }
}
