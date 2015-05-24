package com.example.dat.camerauploader;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;


public class ShowOneAnimal extends Activity {
    ImageView imageViewSolo;
    TextView textViewPK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_one_animal);
        getID();
        Intent callerIntent = getIntent();
        Bundle bundle = callerIntent.getBundleExtra("DATA");
        Animal animal = (Animal) bundle.getSerializable("ANIMAL");

        textViewPK.setText(animal.getPk());
        Picasso.with(this)
                .load(animal.getImageURL())
                .placeholder(R.drawable.placeholder)
                .noFade()
                .fit()
                .centerInside()
                .error(R.drawable.missing).memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(imageViewSolo);
    }


    public void getID() {
        imageViewSolo = (ImageView) findViewById(R.id.imageViewSolo);
        textViewPK = (TextView) findViewById(R.id.textViewPK);
    }
}
