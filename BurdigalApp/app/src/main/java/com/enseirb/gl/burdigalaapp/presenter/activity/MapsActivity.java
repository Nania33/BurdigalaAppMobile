package com.enseirb.gl.burdigalaapp.presenter.activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.presenter.fragment.PointDetailFragment;
import com.enseirb.gl.burdigalaapp.presenter.fragment.PointListFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        PointListFragment.OnFragmentInteractionListener,
        PointDetailFragment.OnFragmentInteractionListener {

    private GoogleMap mMap;
    private Button btnShowList;
    private Button btnShowPoint;

    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        btnShowList = (Button) findViewById(R.id.btn_show_list);
        btnShowPoint = (Button) findViewById(R.id.btn_show_point_details);

        if (findViewById(R.id.fragment_container_left) != null && findViewById(R.id.fragment_container_right) != null) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = SupportMapFragment.newInstance();
            PointListFragment itemFragment = PointListFragment.newInstance("param1", "param2");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container_left, mapFragment);
            ft.add(R.id.fragment_container_right, itemFragment);
            mapFragment.getMapAsync(this);
            ft.commit();
        } else {

            btnShowPoint.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PointDetailFragment blankFragment = PointDetailFragment.newInstance("param1", "param2");
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, blankFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });

            btnShowList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PointListFragment itemFragment = PointListFragment.newInstance("param1", "param2");
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.fragment_container, itemFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = SupportMapFragment.newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.fragment_container, mapFragment);
            mapFragment.getMapAsync(this);
            ft.commit();
        }
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
    public void onFragmentInteraction(String id) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}