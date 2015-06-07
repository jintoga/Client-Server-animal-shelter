package com.dat.animalsshelter.help_about;

import android.os.Bundle;
import android.view.View;

import com.dat.animalsshelter.BaseActivity;
import com.dat.animalsshelter.R;

public class AboutUs extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_about_us);
        getLayoutInflater().inflate(R.layout.activity_about_us, frameLayout);

        listItemDrawer.setItemChecked(position, true);

        setTitle(listItemDrawer.getItemAtPosition(position) + "");
    }


}
