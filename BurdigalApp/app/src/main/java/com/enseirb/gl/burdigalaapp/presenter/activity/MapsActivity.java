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
import android.widget.Toast;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.presenter.BlockingQueueData;
import com.enseirb.gl.burdigalaapp.presenter.fragment.PointListFragment;
import com.enseirb.gl.burdigalaapp.presenter.fragment.detail.PointDetailFragment;
import com.enseirb.gl.burdigalaapp.presenter.manager.ServiceManager;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        PointListFragment.OnFragmentInteractionListener,
        PointDetailFragment.OnFragmentInteractionListener {

    private static final String LIST_OF_SERVICES = "list_of_services";
    private static final String TAG = "MapsActivity";

    private GoogleMap mMap;
    private Button btnShowList;

    private PointDetailFragment detailFragment;
    private List<PointListFragment> listFragment = new ArrayList<>();
    private int currentListFragment = 0;

    private SupportMapFragment mapFragment;

    private ArrayList<Service> listOfServices = new ArrayList<>();

    private ServiceManager serviceManager;

    private BlockingQueue<BlockingQueueData> queue = new LinkedBlockingQueue<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        detailFragment = PointDetailFragment.newInstance("param1");

        listOfServices = getIntent().getParcelableArrayListExtra(LIST_OF_SERVICES);

        for (Service service : listOfServices)
            if (service.isSelected())
                listFragment.add(PointListFragment.newInstance(service));

        mapFragment = SupportMapFragment.newInstance();

        initializeServiceManager();

        if (findViewById(R.id.fragment_container_map) != null && findViewById(R.id.fragment_container) != null) {
            initializeTablet();
        } else {
            initializePhone();
        }
    }



    /*******************************
     *   Initialization functions  *
     *******************************/

    private void initializePhone() {
        btnShowList = (Button) findViewById(R.id.btn_show_list);

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putFragmentInContainer(MapsActivity.this.listFragment.get(currentListFragment), R.id.fragment_container);
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
        ft.add(R.id.fragment_container, listFragment.get(currentListFragment));
        mapFragment.getMapAsync(this);
        ft.commit();
    }

    private void initializeServiceManager(){
        serviceManager = new ServiceManager(listOfServices, new ServiceManager.ServiceManagerListener() {
            @Override
            public void onError(Service service, String message) {
                Log.d(TAG, "onError : " + message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
                try {
                    queue.put(BlockingQueueData.recordFailure(service, message));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDataRetrieved(Service service) {
                Log.d(TAG, "onDataReceivedThread : " + Thread.currentThread().getId());
                try {
                    Log.d(TAG, "OnSuccess : putting "+service+" in queue (Thread" + Thread.currentThread().getId());
                    queue.put(BlockingQueueData.recordSucces(service));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        for (Service service : listOfServices) {
            Log.d(TAG, service.toString() + " " + service.getType());
        }
        Log.d(TAG, "MainThread : " + Thread.currentThread().getId());
        serviceManager.initializeServices();
    }


    /*******************************
     *        Map functions        *
     *******************************/


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
        Log.d(TAG, "onMapReady : " + Thread.currentThread().getId());
        mMap = googleMap;
        initializeMap();
        handleDataRetrieving();
    }

    private void initializeMap(){
        double longitude = -0.566741222966319;
        double latitude = 44.8384053932397;
        float zoom = 11.8f;

        LatLng reference = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(reference));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));
    }

    public void displayPointsOnMap(Service service) {
        List<Model> points = getDataListToDisplay(service);
        for (Model openDataPoint : points){
            LatLng point = openDataPoint.getLatLng();
            mMap.addMarker(new MarkerOptions().position(point)
                    .title(service.getType().toString()));
        }
    }

    private void handleDataRetrieving(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Démarrage du thread "+Thread.currentThread().getId());
                BlockingQueueData data;
                try {
                    while ((data = queue.poll(5, TimeUnit.MINUTES)) != null){
                        final BlockingQueueData finalData = data;
                        if (data.isSucces()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG, "doSomeUpdate on thread " + Thread.currentThread().getId());
                                    displayPointsOnMap(finalData.getService());
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MapsActivity.this,
                                            "Erreur à la récupération des "+finalData.getService().getName(),
                                            Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Log.d(TAG, "interruptionDuThread "+Thread.currentThread().getId());
                    Thread.currentThread().interrupt();
                    Log.d(TAG, "threadInterrompu");
                }
            }
        });
        thread.start();
    }




    /*******************************
     *  Listeners implementations  *
     *******************************/


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
    public List<Model> getDataListToDisplay(Service service) {
        return serviceManager.getContainer(service).getModels();
    }

    @Override
    public void onNextPressed() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (currentListFragment == listFragment.size()-1)
            currentListFragment = 0;
        else
            currentListFragment++;

        ft.replace(R.id.fragment_container, listFragment.get(currentListFragment));
        ft.commit();
    }

    @Override
    public void onPreviousPressed() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (currentListFragment == 0)
            currentListFragment = listFragment.size()-1;
        else
            currentListFragment--;

        ft.replace(R.id.fragment_container, listFragment.get(currentListFragment));
        ft.commit();
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


    /*******************************
     *       Other functions       *
     *******************************/

    private void putFragmentInContainer(Fragment fragment, int fragment_container) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }*/
}