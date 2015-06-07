package com.dat.animalsshelter.get_list_lost_animals;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dat.animalsshelter.R;
import com.dat.animalsshelter.get_list_animals.RoundedTransformation;
import com.dat.animalsshelter.model.Animal;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by DAT on 6/7/2015.
 */
public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;
    ProgressBar progressBar = null;
    //ArrayList<MarkerOptions> listMarkerOptions = null;

    public MyInfoWindowAdapter(Activity context/*, ArrayList<MarkerOptions> listMarkerOptions*/) {
        this.context = context;
        /*this.listMarkerOptions = listMarkerOptions;*/
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    boolean not_first_time_showing_info_window = false;

    @Override
    public View getInfoContents(final Marker marker) {
        View view = this.context.getLayoutInflater().inflate(R.layout.custom_info_window_google_map, null);
        // Set desired height and width
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        Animal animal = ((GoogleMapLostAnimals) context).getListMarkers().get(marker.getId());
        //progressBar = (ProgressBar) view.findViewById(R.id.progressBar3);
        ImageView imageViewSolo = (ImageView) view.findViewById(R.id.imageViewSolo2);
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate2);
        TextView textViewLocation = (TextView) view.findViewById(R.id.textViewLocation2);

        textViewDate.setText(animal.getLast_date_seen() + "");
        textViewLocation.setText(animal.getLast_location() + "");

        Picasso.with(context).load(animal.getImageURLthumbnail()).into(imageViewSolo);
        /*if (not_first_time_showing_info_window) {
            Picasso.with(context).load(animal.getImageURLthumbnail()).into(imageViewSolo);
        } else { // if it's the first time, load the image with the callback set
            not_first_time_showing_info_window = true;
            Picasso.with(context).load(animal.getImageURLthumbnail()).into(imageViewSolo, new InfoWindowRefresher(marker));
        }*/
        return view;
    }


    class InfoWindowRefresher implements Callback {
        private Marker markerToRefresh;

        private InfoWindowRefresher(Marker markerToRefresh) {
            this.markerToRefresh = markerToRefresh;
        }

        @Override
        public void onSuccess() {
            markerToRefresh.showInfoWindow();
        }

        @Override
        public void onError() {
        }
    }
}
