package com.dat.animalsshelter.help_about;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.dat.animalsshelter.BaseActivity;
import com.dat.animalsshelter.R;

public class HelpUs extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_help_us);

        getLayoutInflater().inflate(R.layout.activity_help_us, frameLayout);

        listItemDrawer.setItemChecked(position, true);

        setTitle(listItemDrawer.getItemAtPosition(position) + "");
    }


}
