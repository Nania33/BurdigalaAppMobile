package com.enseirb.gl.burdigalaapp.presenter.activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.presenter.fragment.detail.PointDetailFragment;
import com.enseirb.gl.burdigalaapp.presenter.fragment.PointListFragment;
import com.enseirb.gl.burdigalaapp.presenter.service.ChoiceFactory;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;
import com.enseirb.gl.burdigalaapp.presenter.manager.ServiceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        PointListFragment.OnFragmentInteractionListener,
        PointDetailFragment.OnFragmentInteractionListener {

    private static final String LIST_OF_SERVICES = "list_of_services";
    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;
    private Button btnShowList;

    private PointDetailFragment detailFragment;
    private PointListFragment listFragment;
    private SupportMapFragment mapFragment;

    private ServiceManager serviceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        detailFragment = PointDetailFragment.newInstance("param1");
        listFragment = PointListFragment.newInstance(ChoiceFactory.makeGarden());
        mapFragment = SupportMapFragment.newInstance();

        initializeServiceManager();

        if (findViewById(R.id.fragment_container_map) != null && findViewById(R.id.fragment_container) != null) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            initializeTablet();
        } else {
            initializePhone();
        }
    }

    private void initializePhone() {
        btnShowList = (Button) findViewById(R.id.btn_show_list);

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putFragmentInContainer(MapsActivity.this.listFragment, R.id.fragment_container);
                btnShowList.setVisibility(View.GONE);
            }
        });

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, mapFragment);
        mapFragment.getMapAsync(this);
        ft.commit();
    }

    private void initializeTablet() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container_map, mapFragment);
        ft.add(R.id.fragment_container, listFragment);
        mapFragment.getMapAsync(this);
        ft.commit();
    }

    private void initializeServiceManager(){
        Intent intent = getIntent();
        ArrayList<Service> listOfServices = intent.getParcelableArrayListExtra(LIST_OF_SERVICES);
        serviceManager = new ServiceManager();
        for (Service service : listOfServices) {
            Log.d(TAG, service.toString() + " " + service.getType());
        }
        serviceManager.initializeServices(listOfServices);
    }

    private void putFragmentInContainer(Fragment fragment, int fragment_container) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    @Override
    public void onListItemClick(String id) {
        putFragmentInContainer(PointDetailFragment.newInstance(id), R.id.fragment_container);
    }

    @Override
    public void onButtonReturnToMapClick() {
        onBackPressed();
        btnShowList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onButtonReturnClick() {
        onBackPressed();
    }


    public static Intent getIntent(Context ctx, ArrayList<Service> itemsToDisplay){
        Intent i = new Intent(ctx, MapsActivity.class);
        i.putParcelableArrayListExtra(LIST_OF_SERVICES, itemsToDisplay);
        return i;
    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }*/
}