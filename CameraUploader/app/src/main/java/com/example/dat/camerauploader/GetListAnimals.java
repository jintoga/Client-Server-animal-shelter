package com.example.dat.camerauploader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GetListAnimals extends Activity {
    GridView gridview;
    public CustomAdapterGridview customAdapterGridview = null;
    private ArrayList<Animal> listAnimal = new ArrayList<>();

    EditText editTextSearch;
    public static Spinner spinnerFilterSpecies, spinnerFilterGender, spinnerFilterAge;

    public static boolean searchOn = false;
    public static String filterSpecies, filterGender, filterAge;
    CharSequence all = "--Все--";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_list_animals);
        getID();
        setEvents();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        ServerRequest serverRequest = new ServerRequest(GetListAnimals.this);
        serverRequest.downloadingFromServer(GetListAnimals.this);

    }


    public void getID() {
        gridview = (GridView) findViewById(R.id.gridView);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                searchOn = true;
                GetListAnimals.this.customAdapterGridview.getFilter().filter(charSequence);
                spinnerFilterSpecies.setSelection(0);
                spinnerFilterGender.setSelection(0);
                spinnerFilterAge.setSelection(0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spinnerFilterSpecies = (Spinner) findViewById(R.id.spinnerFilterSpecies);
        spinnerFilterGender = (Spinner) findViewById(R.id.spinnerFilterGender);
        spinnerFilterAge = (Spinner) findViewById(R.id.spinnerFilterAge);

       /* filterWithSpinnersSpecies = false;
        filterWithSpinnersGender = false;
        filterWithSpinnersAge = false;*/
    }


    public void setEvents() {
        customAdapterGridview = new CustomAdapterGridview(GetListAnimals.this, R.layout.custom_listview, listAnimal);
        gridview.setAdapter(customAdapterGridview);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Animal animal = (Animal) adapterView.getItemAtPosition(position);

                Intent intent = new Intent(GetListAnimals.this, ShowOneAnimal.class);
                Bundle bundle = new Bundle();


                bundle.putSerializable("ANIMAL", animal);
                intent.putExtra("DATA", bundle);
                GetListAnimals.this.startActivity(intent);
            }
        });
        spinnerFilterSpecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterSpecies = spinnerFilterSpecies.getSelectedItem().toString();
                CharSequence charSequence = spinnerFilterSpecies.getSelectedItem().toString();

                GetListAnimals.this.customAdapterGridview.getFilter().filter(charSequence);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerFilterGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterGender = spinnerFilterGender.getSelectedItem().toString();
                CharSequence charSequence = spinnerFilterGender.getSelectedItem().toString();

                GetListAnimals.this.customAdapterGridview.getFilter().filter(charSequence);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerFilterAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterAge = spinnerFilterAge.getSelectedItem().toString();
                CharSequence charSequence = spinnerFilterAge.getSelectedItem().toString();

                GetListAnimals.this.customAdapterGridview.getFilter().filter(charSequence);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public ArrayList<Animal> getListAnimal() {
        return listAnimal;
    }

}
