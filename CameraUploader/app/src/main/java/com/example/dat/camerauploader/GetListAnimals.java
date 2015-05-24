package com.example.dat.camerauploader;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class GetListAnimals extends Activity {
    GridView gridview;
    public CustomAdapterGridview customAdapterGridview = null;
    private ArrayList<Animal> listAnimal = new ArrayList<>();
    Button btnBack;


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
        btnBack = (Button) findViewById(R.id.buttonBack);
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

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public ArrayList<Animal> getListAnimal() {
        return listAnimal;
    }

}
