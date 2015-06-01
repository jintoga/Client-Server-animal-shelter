package com.dat.animalsshelter;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dat.animalsshelter.custom_adapters.CustomAdapterDrawer;
import com.dat.animalsshelter.get_list_animals.GetListAnimals;
import com.dat.animalsshelter.help_about.AboutUs;
import com.dat.animalsshelter.help_about.HelpUs;
import com.dat.animalsshelter.send_animal.SendAnimal;
import com.dat.animalsshelter.send_lost_animal.SendLostAnimal;


public class MainActivity extends ActionBarActivity {

    private DrawerLayout drawerLayout;
    private ListView listItemDrawer;
    private CustomAdapterDrawer adapterDrawer;
    private ActionBarDrawerToggle drawerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIDs();
        setEvents();

    }


    private void getIDs() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listItemDrawer = (ListView) findViewById(R.id.drawerList);
        adapterDrawer = new CustomAdapterDrawer(MainActivity.this);

        listItemDrawer.setAdapter(adapterDrawer);
    }

    public void setEvents() {
        listItemDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                selectActivityInDrawer(position);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle("Closed!");
                //Toast.makeText(MainActivity.this, "Drawer Closed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Open!");
                //Toast.makeText(MainActivity.this, "Drawer Open", Toast.LENGTH_LONG).show();
            }
        };
        drawerListener.setDrawerIndicatorEnabled(true);

        drawerLayout.setDrawerListener(drawerListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerListener.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerListener.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        // Activate the navigation drawer toggle
        if (drawerListener.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //close Drawer when back from other activities


    protected void closeDrawer() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT))
            drawerLayout.closeDrawer(Gravity.LEFT);
    }

    public void selectActivityInDrawer(int position) {
        listItemDrawer.setItemChecked(position, true);
        //Toast.makeText(MainActivity.this, listItemDrawer.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
        Intent intent;
        switch (position) {
            case 0:
                closeDrawer();
                intent = new Intent(MainActivity.this, GetListAnimals.class);
                startActivity(intent);
                break;
            case 1:
                closeDrawer();
                intent = new Intent(MainActivity.this, SendAnimal.class);
                startActivity(intent);
                break;
            case 2:
                break;
            case 3:
                closeDrawer();
                intent = new Intent(MainActivity.this, SendLostAnimal.class);
                startActivity(intent);
                break;
            case 4:
                closeDrawer();
                intent = new Intent(MainActivity.this, HelpUs.class);
                startActivity(intent);
                break;
            case 5:
                closeDrawer();
                intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
