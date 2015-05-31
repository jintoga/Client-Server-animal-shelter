package com.example.dat.testmap3;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity {

    //Khai báo đối tượng Google Map
    GoogleMap map;
    //Khai báo Progress Bar dialog để làm màn hình chờ
    ProgressDialog myProgress;
    Spinner spinner_maps_type;

    ArrayAdapter<String> adapterMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getID();
        showMap();
        setEvents();

    }

    public void showMap() {
//Tạo Progress Bar
        myProgress = new ProgressDialog(this);
        myProgress.setTitle("Đang tải Map ...");
        myProgress.setMessage("Vui lòng chờ...");
        myProgress.setCancelable(true);
        //Hiển thị Progress Bar
        myProgress.show();
        //Lấy đối tượng Google Map ra:
        map = ((MapFragment) getFragmentManager().
                findFragmentById(R.id.map)).getMap();
        map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                //Đã tải thành công thì tắt Dialog Progress đi
                myProgress.dismiss();
            }
        });
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.setMyLocationEnabled(true);

        //move Camera to Barnaul
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(BARNAUL, 15));
        map.addMarker(new MarkerOptions().position(BARNAUL).title("Barnaul").snippet("My Barnaul"));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(HANOI, 15));
        map.addMarker(new MarkerOptions().position(HANOI).title("Hà Nội").snippet("Home sweet home"));

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
    }

    public void locationName(double lat, double lng) {
        try {
            Geocoder geo = new Geocoder(MainActivity.this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(lat, lng, 1);
            if (addresses.isEmpty()) {
                //Toast.makeText(MainActivity.this, "Waiting for Location" + "lat-long:" + lat + "-" + lng, Toast.LENGTH_SHORT).show();
            } else {
                if (addresses.size() > 0) {
                    String fullAddress = addresses.get(0).getFeatureName() + "-" + addresses.get(0).getAddressLine(0) + ", " + addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAdminArea() + ", " + addresses.get(0).getCountryName();
                    Toast.makeText(MainActivity.this, "Address: " + fullAddress, Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail
        }
    }

    private void getID() {
        spinner_maps_type = (Spinner) findViewById(R.id.spinner_map_type);
        Resources res = MainActivity.this.getResources();

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
                        type = GoogleMap.MAP_TYPE_SATELLITE;
                        break;
                    case 2:
                        type = GoogleMap.MAP_TYPE_TERRAIN;
                        break;
                    case 3:
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

    static final LatLng BARNAUL = new LatLng(53.3547792, 83.7697832);
    static final LatLng HANOI = new LatLng(21.0277644, 105.8341598);


}
