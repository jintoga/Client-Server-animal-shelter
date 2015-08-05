package com.app.animalsshelter.content.get_list_lost_animals;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.animalsshelter.R;
import com.app.animalsshelter.model.Animal;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class ShowOneLostAnimal extends Activity {
    ImageView imageViewSolo2;
    TextView textViewPK2, textViewSpecies2, textViewDate, textViewLocation, textViewDescription;
    ProgressBar progressBar2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_one_lost_animal);
        getID();
        Intent callerIntent = getIntent();
        Bundle bundle = callerIntent.getBundleExtra("DATA");
        Animal animal = (Animal) bundle.getSerializable("ANIMAL");

        textViewPK2.setText(animal.getPk());
        textViewPK2.setVisibility(View.GONE);

        textViewSpecies2.setText(animal.getSpecies());
        textViewDate.setText(animal.getLast_date_seen());
        textViewLocation.setText(animal.getLast_location());
        textViewDescription.setText(animal.getDescription());
        progressBar2.setVisibility(View.VISIBLE);


        Picasso.with(this)
                .load(animal.getImageURL())
                .placeholder(R.drawable.placeholder)
                .noFade()
                .fit()
                .centerInside()
                .error(R.drawable.missing).memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageViewSolo2, new ImageLoadedCallback(progressBar2) {
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
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);


        imageViewSolo2 = (ImageView) findViewById(R.id.imageViewSolo);

        textViewPK2 = (TextView) findViewById(R.id.textViewPK);

        textViewSpecies2 = (TextView) findViewById(R.id.textViewSpecies);
        textViewDate = (TextView) findViewById(R.id.textViewDate);
        textViewLocation = (TextView) findViewById(R.id.textViewLocation);
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
