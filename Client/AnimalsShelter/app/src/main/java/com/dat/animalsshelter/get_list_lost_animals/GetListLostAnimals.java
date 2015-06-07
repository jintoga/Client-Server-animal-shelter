package com.dat.animalsshelter.get_list_lost_animals;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dat.animalsshelter.BaseActivity;
import com.dat.animalsshelter.R;
import com.dat.animalsshelter.get_list_animals.CustomAdapterGridview;
import com.dat.animalsshelter.get_list_animals.ShowOneAnimal;
import com.dat.animalsshelter.internet_stuff.ServerRequest;
import com.dat.animalsshelter.model.Animal;

import java.util.ArrayList;

public class GetListLostAnimals extends BaseActivity {

    GridView gridview2;
    public CustomAdapterGridview2 customAdapterGridview2 = null;
    private ArrayList<Animal> listAnimal2 = new ArrayList<>();

    EditText editTextSearch2;

    ImageButton imageButtonOpenMap;


    public static boolean searchOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_get_list_lost_animals);
        getLayoutInflater().inflate(R.layout.activity_get_list_lost_animals, frameLayout);

        listItemDrawer.setItemChecked(position, true);

        setTitle(listItemDrawer.getItemAtPosition(position) + "");
        getID();
        setEvents();

        //  RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        ServerRequest serverRequest = new ServerRequest(GetListLostAnimals.this);
        serverRequest.downloadingLostAnimalsFromServer(GetListLostAnimals.this);
    }

    public void getID() {
        imageButtonOpenMap = (ImageButton) findViewById(R.id.imageButtonLostAnimalsMap);
        gridview2 = (GridView) findViewById(R.id.gridView2);
        editTextSearch2 = (EditText) findViewById(R.id.editTextSearch2);
        editTextSearch2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                searchOn = true;
                GetListLostAnimals.this.customAdapterGridview2.getFilter().filter(charSequence);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    public void setEvents() {
        customAdapterGridview2 = new CustomAdapterGridview2(GetListLostAnimals.this, R.layout.custom_item_gridview2, listAnimal2);
        gridview2.setAdapter(customAdapterGridview2);

        gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Animal animal = (Animal) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(GetListLostAnimals.this, ShowOneLostAnimal.class);
                Bundle bundle = new Bundle();


                bundle.putSerializable("ANIMAL", animal);
                intent.putExtra("DATA", bundle);
                GetListLostAnimals.this.startActivity(intent);
            }
        });

        imageButtonOpenMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GetListLostAnimals.this, GoogleMapLostAnimals.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("LIST_ANIMALS", listAnimal2);
                intent.putExtra("DATA", bundle);
                GetListLostAnimals.this.startActivity(intent);
            }
        });


    }

    public ArrayList<Animal> getListAnimal() {
        return listAnimal2;
    }
}
