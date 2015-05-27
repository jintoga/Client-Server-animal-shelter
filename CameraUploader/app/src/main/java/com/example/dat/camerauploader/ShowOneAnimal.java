package com.example.dat.camerauploader;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;


public class ShowOneAnimal extends Activity {
    ImageView imageViewSolo;
    TextView textViewPK, textViewSpecies, textViewName, textViewBreed, textViewGender, textViewAge, textViewWeight, textViewSterilize, textViewDescription;
    ProgressBar progressBar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_one_animal);
        getID();
        Intent callerIntent = getIntent();
        Bundle bundle = callerIntent.getBundleExtra("DATA");
        Animal animal = (Animal) bundle.getSerializable("ANIMAL");

        textViewPK.setText(animal.getPk());
        textViewPK.setVisibility(View.GONE);

        textViewName.setText(animal.getName());
        textViewSpecies.setText(animal.getSpecies());
        textViewBreed.setText(animal.getBreed());
        textViewGender.setText(animal.getGender() + "");
        textViewAge.setText(animal.getAge());
        textViewWeight.setText(animal.getWeight() + "кг");
        textViewSterilize.setText(animal.getSterilize());
        textViewDescription.setText(animal.getDescription());
        progressBar.setVisibility(View.VISIBLE);


        Picasso.with(this)
                .load(animal.getImageURL())
                .placeholder(R.drawable.placeholder)
                .noFade()
                .fit()
                .centerInside()
                .error(R.drawable.missing).memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageViewSolo, new ImageLoadedCallback(progressBar) {
                    @Override
                    public void onSuccess() {

                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }

                    }

                    @Override
                    public void onError() {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }


                });
    }


    public void getID() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        imageViewSolo = (ImageView) findViewById(R.id.imageViewSolo);

        textViewPK = (TextView) findViewById(R.id.textViewPK);

        textViewSpecies = (TextView) findViewById(R.id.textViewSpecies);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewBreed = (TextView) findViewById(R.id.textViewBreed);
        textViewGender = (TextView) findViewById(R.id.textViewGender);
        textViewAge = (TextView) findViewById(R.id.textViewAge);
        textViewWeight = (TextView) findViewById(R.id.textViewWeight);
        textViewSterilize = (TextView) findViewById(R.id.textViewSterilize);
        textViewDescription = (TextView) findViewById(R.id.textViewDescription);
    }

    class ImageLoadedCallback implements Callback {
        ProgressBar progressBar;

        public ImageLoadedCallback(ProgressBar progBar) {
            progressBar = progBar;
        }

        @Override
        public void onSuccess() {

        }

        @Override
        public void onError() {

        }
    }
}


