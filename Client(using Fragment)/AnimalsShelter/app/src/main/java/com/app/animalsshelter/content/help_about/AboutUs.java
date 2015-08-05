package com.app.animalsshelter.content.help_about;

import android.os.Bundle;

import com.app.animalsshelter.BaseActivity;
import com.app.animalsshelter.R;

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
