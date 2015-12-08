
package com.enseirb.gl.burdigalaapp.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.filters.NearestPointsFilter;
import com.enseirb.gl.burdigalaapp.filters.NoFilter;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.presenter.BlockingQueueData;
import com.enseirb.gl.burdigalaapp.presenter.fragment.PointListFragment;
import com.enseirb.gl.burdigalaapp.presenter.fragment.detail.CycleParkDetailFragment;
import com.enseirb.gl.burdigalaapp.presenter.fragment.detail.GardenDetailFragment;
import com.enseirb.gl.burdigalaapp.presenter.fragment.detail.ParkingDetailFragment;
import com.enseirb.gl.burdigalaapp.presenter.fragment.detail.ToiletDetailFragment;
import com.enseirb.gl.burdigalaapp.presenter.manager.ServiceManager;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;
import com.enseirb.gl.burdigalaapp.presenter.service.ServiceType;
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
        ToiletDetailFragment.OnFragmentInteractionListener,
        GardenDetailFragment.OnFragmentInteractionListener,
        ParkingDetailFragment.OnFragmentInteractionListener,
        CycleParkDetailFragment.OnFragmentInteractionListener{

    private static final String LIST_OF_SERVICES = "list_of_services";
    private static final String TAG = "MapsActivity";
    private static final double bordeauxCenterLat = 44.836758;
    private static final double bordeauxCenterLong = -0.578746;

    private GoogleMap mMap;
    private Button btnShowList;
    private LatLng userPosition = null;

    private List<PointListFragment> listFragment = new ArrayList<>();
    private int currentListFragment = 0;

    private SupportMapFragment mapFragment;

    private ArrayList<Service> listOfServices = new ArrayList<>();

    private ServiceManager serviceManager;

    private BlockingQueue<BlockingQueueData> queue = new LinkedBlockingQueue<>();

    private int depth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

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
        Log.d(TAG, "initializePhone : " + Thread.currentThread().getId());
        btnShowList = (Button) findViewById(R.id.btn_show_list);

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depth++;
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
        Log.d(TAG, "initializeTablet : " + Thread.currentThread().getId());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container_map, mapFragment);
        mapFragment.getMapAsync(this);
        ft.add(R.id.fragment_container, listFragment.get(currentListFragment));
        ft.commit();
    }

    private void initializeServiceManager(){
        serviceManager = new ServiceManager(listOfServices, new ServiceManager.ServiceManagerListener() {
            @Override
            public void onError(Service service, String message) {
                Log.d(TAG, "onError : " + message);
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
        serviceManager.initializeServices(new NoFilter());
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
        Log.d(TAG, "initializeMap");
        float zoom = 13f;
        LatLng zoomLocation = getLastBestLocation();

        mMap.addMarker(new MarkerOptions().position(zoomLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(zoomLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(zoom));

        initializeOnMarkerClickListener();
    }

    private void initializeOnMarkerClickListener(){
        Log.d(TAG, "initializeOnMarkerClickListener");
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                goToPointDetails(marker.getTitle(), marker.getPosition());
                return true;
            }
        });
    }

    public void displayPointsOnMap(Service service) {
        Log.d(TAG, "displayPointsOnMap");
        List<Model> points = serviceManager.pointsToDisplatOnMap(service, new NearestPointsFilter(20, new LatLng(bordeauxCenterLat, bordeauxCenterLong)));
        for (Model openDataPoint : points) {
            LatLng point = openDataPoint.getLatLng();
            mMap.addMarker(new MarkerOptions().position(point)
                    .title(service.getType().toString())
                    .icon(service.getMarkerIcon()));
        }
    }


    private PointListFragment findListFragment(Service service){
        for (PointListFragment fragment : listFragment){
            if (fragment.getService() != null){
                if (fragment.getService().equals(service)){
                    return fragment;
                }
            } else {
                Log.d(TAG, "Appel à onCreate non effectué");
            }
        }
        return null;
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
                        final Service service = finalData.getService();
                        if (data.isSucces()) {
                            final PointListFragment fragment = findListFragment(service);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d(TAG, "doSomeUpdate on thread " + Thread.currentThread().getId());
                                    displayPointsOnMap(service);
                                    if (fragment != null)
                                        fragment.update();
                                    else Log.d(TAG, "Fragment non trouvé");
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MapsActivity.this,
                                            "Erreur à la récupération des "+service.getName(),
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


    private void goToPointDetails(String serv, LatLng position){
        try {
            Service service = serviceManager.getService(ServiceType.toServiceType(serv));
            int itemPosition = serviceManager.getPointIndex(service, position);
            if (btnShowList != null)
                btnShowList.setVisibility(View.GONE);
            onListItemClick(service, itemPosition);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public LatLng getLastBestLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location locationGPS = null;
        Location locationNet = null;

        try {
            locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locationNet = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        // if found, return the GPS location because it is more precise
        if (locationGPS != null) {
            userPosition = new LatLng(locationGPS.getLatitude(), locationGPS.getLongitude());
            return userPosition;
        }

        else if(locationNet != null) {
            userPosition = new LatLng(locationNet.getLatitude(), locationNet.getLongitude());
            return userPosition;
        }

        // hard coded values at the center of Bordeaux if we can't get the location of the user.
        userPosition = null;
        return new LatLng(bordeauxCenterLat, bordeauxCenterLong);

    }

    public LatLng getUserPosition(){
        return userPosition;
    }


    /*******************************
     *  Listeners implementations  *
     *******************************/



    /*******************************
     *  ListFragment Listener      *
     *******************************/

    @Override
    public void onListItemClick(Service service, int itemPosition) {
        depth++;
        if (service.getType().equals(ServiceType.TOILET)) {
            putFragmentInContainer(ToiletDetailFragment.newInstance(service, itemPosition), R.id.fragment_container);
        } else if (service.getType().equals(ServiceType.GARDEN)){
            putFragmentInContainer(GardenDetailFragment.newInstance(service, itemPosition), R.id.fragment_container);
        } else if (service.getType().equals(ServiceType.PARKING)){
            putFragmentInContainer(ParkingDetailFragment.newInstance(service, itemPosition), R.id.fragment_container);
        } else if (service.getType().equals(ServiceType.CYCLEPARK)){
            putFragmentInContainer(CycleParkDetailFragment.newInstance(service, itemPosition), R.id.fragment_container);
        }
    }

    @Override
    public void onButtonReturnToMapClick() {
        onBackPressed();
    }

    @Override
    public List<Model> getListOfPoints(Service service) {
        return serviceManager.pointsToDisplayOnList(service, new NoFilter());
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



    /*******************************
     *  DetailFragment Listener    *
     *******************************/

    @Override
    public void onButtonReturnClick() {
        onBackPressed();
    }

    @Override
    public void onFocusRequired(Model point) {
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point.getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.f));
        if(depth == 1) {
            onBackPressed();
        } else if (depth == 2) {
            onBackPressed();
            onBackPressed();
        }
    }

    @Override
    public Parking getParking(Service service, int position) {
        return ((List<Parking>) serviceManager.getContainer(service).getModels()).get(position);
    }

    @Override
    public CyclePark getCyclePark(Service service, int position) {
        return ((List<CyclePark>) serviceManager.getContainer(service).getModels()).get(position);
    }

    @Override
    public Garden getGarden(Service service, int position) {
        return ((List<Garden>) serviceManager.getContainer(service).getModels()).get(position);
    }

    @Override
    public Toilet getToilet(Service service, int position) {
        return ((List<Toilet>) serviceManager.getContainer(service).getModels()).get(position);
    }


    /*******************************
     *       Other functions       *
     *******************************/

    public static Intent getIntent(Context ctx, ArrayList<Service> itemsToDisplay){
        Intent i = new Intent(ctx, MapsActivity.class);
        i.putParcelableArrayListExtra(LIST_OF_SERVICES, itemsToDisplay);
        return i;
    }

    private void putFragmentInContainer(Fragment fragment, int fragment_container) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        depth--;
        if (depth == 0 && btnShowList != null)
            btnShowList.setVisibility(View.VISIBLE);
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }*/
}