package com.app.animalsshelter.content.get_list_animals;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.app.animalsshelter.BaseFragment;
import com.app.animalsshelter.R;
import com.app.animalsshelter.camera.BaseAlbumDirFactory;
import com.app.animalsshelter.internet.ServerRequest;
import com.app.animalsshelter.model.Animal;

import java.util.ArrayList;

public class GetListAnimalsFragment extends BaseFragment {
    GridView gridview;
    public CustomAdapterGridView customAdapterGridView = null;
    private ArrayList<Animal> listAnimal = new ArrayList<>();

    EditText editTextSearch;
    public static Spinner spinnerFilterSpecies, spinnerFilterGender, spinnerFilterAge;

    public static boolean searchOn = false;
    public static String filterSpecies, filterGender, filterAge;
    CharSequence all = "--Все--";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_get_list_animals, container, false);
        getID(root);
        setEvents();

        if (listAnimal.size() == 0) {
            RequestQueue mRequestQueue = Volley.newRequestQueue(root.getContext());
            ServerRequest serverRequest = new ServerRequest(GetListAnimalsFragment.this);
            serverRequest.downloadingFromServer(root.getContext());
        }
        /*customAdapterGridView.notifyDataSetChanged();*/
        return root;
    }

    /*@Override
    public void onViewCreated(View root, Bundle savedInstanceState) {


    }*/


    private void getID(View root) {

        gridview = (GridView) root.findViewById(R.id.gridView);
        editTextSearch = (EditText) root.findViewById(R.id.editTextSearch);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                searchOn = true;
                GetListAnimalsFragment.this.customAdapterGridView.getFilter().filter(charSequence);
                spinnerFilterSpecies.setSelection(0);
                spinnerFilterGender.setSelection(0);
                spinnerFilterAge.setSelection(0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        spinnerFilterSpecies = (Spinner) root.findViewById(R.id.spinnerFilterSpecies);
        spinnerFilterGender = (Spinner) root.findViewById(R.id.spinnerFilterGender);
        spinnerFilterAge = (Spinner) root.findViewById(R.id.spinnerFilterAge);

    }


    private void setEvents() {
        customAdapterGridView = new CustomAdapterGridView(getActivity(), R.layout.custom_item_gridview, listAnimal);
        gridview.setAdapter(customAdapterGridView);
        Log.e("listanimal", listAnimal.toString());

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Animal animal = (Animal) adapterView.getItemAtPosition(position);

                /*Intent intent = new Intent(getActivity(), ShowOneAnimal.class);*/
                Bundle bundle = new Bundle();


                bundle.putSerializable("ANIMAL", animal);
                /*intent.putExtra("DATA", bundle);
                GetListAnimalsFragment.this.startActivity(intent);*/

                BaseFragment fragment = new ShowOneAnimalFragment();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getActivity().getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_frame, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        spinnerFilterSpecies.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                filterSpecies = spinnerFilterSpecies.getSelectedItem().toString();
                CharSequence charSequence = spinnerFilterSpecies.getSelectedItem().toString();

                GetListAnimalsFragment.this.customAdapterGridView.getFilter().filter(charSequence);
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

                GetListAnimalsFragment.this.customAdapterGridView.getFilter().filter(charSequence);
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

                GetListAnimalsFragment.this.customAdapterGridView.getFilter().filter(charSequence);
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
