package com.app.animalsshelter.content.get_list_animals;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.app.animalsshelter.BaseActivity;
import com.app.animalsshelter.R;
import com.app.animalsshelter.internet.ServerRequest;
import com.app.animalsshelter.model.Animal;

import java.util.ArrayList;

public class GetListAnimals extends BaseActivity {
    GridView gridview;
    public CustomAdapterGridView customAdapterGridView = null;
    private ArrayList<Animal> listAnimal = new ArrayList<>();

    EditText editTextSearch;
    public static Spinner spinnerFilterSpecies, spinnerFilterGender, spinnerFilterAge;

    public static boolean searchOn = false;
    public static String filterSpecies, filterGender, filterAge;
    CharSequence all = "--Все--";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_get_list_animals);
        getLayoutInflater().inflate(R.layout.activity_get_list_animals, frameLayout);

        listItemDrawer.setItemChecked(position, true);

        setTitle(listItemDrawer.getItemAtPosition(position) + "");
        getID();
        setEvents();
        RequestQueue mRequestQueue = Volley.newRequestQueue(this);
        //ServerRequest serverRequest = new ServerRequest(GetListAnimals.this);
        //serverRequest.downloadingFromServer(GetListAnimals.this);

    }


    private void getID() {
        gridview = (GridView) findViewById(R.id.gridView);
        editTextSearch = (EditText) findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                searchOn = true;
                GetListAnimals.this.customAdapterGridView.getFilter().filter(charSequence);
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


    private void setEvents() {
        customAdapterGridView = new CustomAdapterGridView(GetListAnimals.this, R.layout.custom_item_gridview, listAnimal);
        gridview.setAdapter(customAdapterGridView);

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

                GetListAnimals.this.customAdapterGridView.getFilter().filter(charSequence);
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

                GetListAnimals.this.customAdapterGridView.getFilter().filter(charSequence);
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

                GetListAnimals.this.customAdapterGridView.getFilter().filter(charSequence);
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
