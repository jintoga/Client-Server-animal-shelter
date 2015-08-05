package com.app.animalsshelter;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.app.animalsshelter.adapters.CustomAdapterDrawer;
import com.app.animalsshelter.content.get_list_animals.GetListAnimals;
import com.app.animalsshelter.content.get_list_lost_animals.GetListLostAnimals;
import com.app.animalsshelter.content.help_about.AboutUs;
import com.app.animalsshelter.content.help_about.HelpUs;
import com.app.animalsshelter.content.send_animal.SendAnimal;
import com.app.animalsshelter.content.send_lost_animal.SendLostAnimal;


public class BaseActivity extends ActionBarActivity {
    protected FrameLayout frameLayout;
    private DrawerLayout drawerLayout;
    protected ListView listItemDrawer;
    private CustomAdapterDrawer adapterDrawer;
    private ActionBarDrawerToggle drawerListener;
    protected static int position = 0;
    private static boolean isLaunch = true;
    private static int lastClicked = 0;

    public static int getPosition() {
        return position;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getIDsHere();
        setEventsHere();

    }


    private void getIDsHere() {
        //frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        frameLayout = (FrameLayout) findViewById(R.id.drawer_layout);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //listItemDrawer = (ListView) findViewById(R.id.drawerList);
        listItemDrawer = (ListView) findViewById(R.id.drawer_layout);
        adapterDrawer = new CustomAdapterDrawer(BaseActivity.this);

        listItemDrawer.setAdapter(adapterDrawer);
        //adapterDrawer.setSelectedPosition(position);
    }

    private void setEventsHere() {
        listItemDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //selectActivityInDrawer(position);
                //view.setClickable(false);
                //view.setEnabled(false);
                //adapterDrawer.setSelectedPosition(position);
                //openActivity(position);
                if (lastClicked != position) {
                    openActivity(position);
                }
                lastClicked = position;
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerListener = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle("Closed!");
                invalidateOptionsMenu();
                super.onDrawerClosed(drawerView);
                //Toast.makeText(MainActivity.this, "Drawer Closed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Open!");
                invalidateOptionsMenu();
                super.onDrawerOpened(drawerView);
                //Toast.makeText(MainActivity.this, "Drawer Open", Toast.LENGTH_LONG).show();
            }
        };
        drawerListener.setDrawerIndicatorEnabled(true);

        drawerLayout.setDrawerListener(drawerListener);

        if (isLaunch) {
            /**
             *Setting this flag false so that next time it will not open our first activity.
             *We have to use this flag because we are using this BaseActivity as parent activity to our other activity.
             *In this case this base activity will always be call when any child activity will launch.
             */
            isLaunch = false;
            openActivity(0);
        }

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


    protected void openActivity(int position) {

        //if (position != BaseActivity.position) {
        drawerLayout.closeDrawer(listItemDrawer);
        BaseActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.

        switch (position) {
            case 0:
                startActivity(new Intent(this, BaseActivity.class));
                break;
            case 1:
                startActivity(new Intent(this, GetListAnimals.class));
                break;
            case 2:
                startActivity(new Intent(this, SendAnimal.class));
                break;
            case 3:
                startActivity(new Intent(this, GetListLostAnimals.class));
                break;
            case 4:
                startActivity(new Intent(this, SendLostAnimal.class));
                break;
            case 5:
                startActivity(new Intent(this, HelpUs.class));
                break;
            case 6:
                startActivity(new Intent(this, AboutUs.class));
                break;
            default:
                break;
        }
        //}

    }


    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = drawerLayout.isDrawerOpen(listItemDrawer);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    // We can override onBackPressed method to toggle navigation drawer
    @Override
    public void onBackPressed() {
        //if (position == 0) {
        // finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
        startActivity(intent);
        finish();
        System.exit(0);
        /*} else {
            super.onBackPressed();
        }*/
    }



    /*public void selectActivityInDrawer(int position) {
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
                closeDrawer();
                intent = new Intent(MainActivity.this, GetListLostAnimals.class);
                startActivity(intent);
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
    }*/
}
