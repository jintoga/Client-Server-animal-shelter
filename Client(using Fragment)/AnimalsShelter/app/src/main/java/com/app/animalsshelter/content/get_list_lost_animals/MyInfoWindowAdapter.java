package com.app.animalsshelter.content.get_list_lost_animals;

import android.app.Activity;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.animalsshelter.BaseFragment;
import com.app.animalsshelter.R;
import com.app.animalsshelter.model.Animal;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DAT on 6/7/2015.
 */
public class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private BaseFragment context;
    ProgressBar progressBar = null;
    //ArrayList<MarkerOptions> listMarkerOptions = null;

    public MyInfoWindowAdapter(BaseFragment context/*, ArrayList<MarkerOptions> listMarkerOptions*/) {
        this.context = context;
        /*this.listMarkerOptions = listMarkerOptions;*/
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    boolean not_first_time_showing_info_window = false;

    private static View view;

    @Override
    public View getInfoContents(final Marker marker) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = context.getActivity().getLayoutInflater().inflate(R.layout.custom_info_window_google_map, null);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }

        //View view = context.getActivity().getLayoutInflater().inflate(R.layout.custom_info_window_google_map, null);
        // Set desired height and width
        view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        String markerID = marker.getId();

        HashMap<String, Animal> listMarkers = ((GoogleMapLostAnimalsFragment) context).getListMarkers();

        Animal animal = listMarkers.get(markerID);
        //progressBar = (ProgressBar) view.findViewById(R.id.progressBar3);
        ImageView imageViewSolo = (ImageView) view.findViewById(R.id.imageViewSolo2);
        TextView textViewDate = (TextView) view.findViewById(R.id.textViewDate2);
        TextView textViewLocation = (TextView) view.findViewById(R.id.textViewLocation2);

        textViewDate.setText(animal.getLast_date_seen() + "");
        textViewLocation.setText(animal.getLast_location() + "");

        Picasso.with(context.getActivity()).load(animal.getImageURLthumbnail()).into(imageViewSolo);

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
