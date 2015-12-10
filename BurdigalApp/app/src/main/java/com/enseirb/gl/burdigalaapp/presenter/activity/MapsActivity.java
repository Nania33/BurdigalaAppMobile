
package com.enseirb.gl.burdigalaapp.presenter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.filters.NoFilter;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.presenter.BlockingQueueTask;
import com.enseirb.gl.burdigalaapp.presenter.conf.Conf;
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
    private static final String TYPE_OF_FILTER = "type_of_filter";
    private static final String NB_POINTS = "nb_points";

    private static final String DETAIL_FRAGMENT_TAG = "detail";
    private static final String TAG = "MapsActivity";

    private Filter mapFilter;

    private GoogleMap mMap;
    private Button btnShowList;
    private LatLng userLocation = null;

    private List<PointListFragment> listFragment = new ArrayList<>();
    private int currentListFragment = 0;

    private SupportMapFragment mapFragment;

    private ArrayList<Service> listOfServices = new ArrayList<>();

    private ServiceManager serviceManager;

    private BlockingQueue<BlockingQueueTask> queue = new LinkedBlockingQueue<>();

    private ArrayList<Marker> mapMarkers = new ArrayList<>();

    private int depth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        listOfServices = getIntent().getParcelableArrayListExtra(LIST_OF_SERVICES);

        initializeFragments();

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

    private void initializeFragments() {
        for (Service service : listOfServices)
            if (service.isSelected())
                listFragment.add(PointListFragment.newInstance(service));

        mapFragment = SupportMapFragment.newInstance();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        for (PointListFragment fragment : listFragment) {
            ft.add(R.id.fragment_container, fragment);
            ft.hide(fragment);
        }
        ft.commit();
    }


    private void initializePhone() {
        Log.d(TAG, "initializePhone : " + Thread.currentThread().getId());

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, mapFragment);
        mapFragment.getMapAsync(this);
        ft.commit();

        btnShowList = (Button) findViewById(R.id.btn_show_list);

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                depth++;
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.hide(mapFragment);
                btnShowList.setVisibility(View.GONE);
                ft.show(MapsActivity.this.listFragment.get(currentListFragment));
                ft.commit();
            }
        });

    }

    private void initializeTablet() {
        Log.d(TAG, "initializeTablet : " + Thread.currentThread().getId());
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container_map, mapFragment);
        mapFragment.getMapAsync(this);
        ft.show(listFragment.get(currentListFragment));
        ft.commit();
    }

    private void initializeServiceManager(){
        serviceManager = new ServiceManager(this, listOfServices, new ServiceManager.ServiceManagerListener() {
            @Override
            public void onError(Service service, String message) {
                Log.d(TAG, "onError : " + message);
                try {
                    queue.put(BlockingQueueTask.recordFailure(service, message));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onDataRetrieved(Service service) {
                Log.d(TAG, "onDataReceivedThread : " + Thread.currentThread().getId());
                try {
                    Log.d(TAG, "OnSuccess : putting "+service+" in queue (Thread" + Thread.currentThread().getId());
                    queue.put(BlockingQueueTask.recordSucces(service));
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

    private void initializeUserLocation(){
        userLocation = serviceManager.getLastBestLocation();
    }

    private void initializeMap(){
        Log.d(TAG, "initializeMap");
        float zoom = 13f;

        initializeUserLocation();

        mMap.addMarker(new MarkerOptions().position(userLocation));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(userLocation));
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


    private Filter getMapFilter(){
        int nbPoints = getIntent().getIntExtra(NB_POINTS,10);
        String filterType = getIntent().getStringExtra(TYPE_OF_FILTER);
        mapFilter = Conf.makeFilter(filterType, nbPoints, userLocation);
        return mapFilter;
    }

    public void displayPointsOnMap(Service service) {
        Log.d(TAG, "displayPointsOnMap");
        List<Model> points = serviceManager.pointsToDisplayOnMap(service, getMapFilter());
        for (Model openDataPoint : points) {
            LatLng point = openDataPoint.getLatLng();
            Marker marker = mMap.addMarker(new MarkerOptions().position(point)
                    .title(service.getType().toString())
                    .icon(service.getMarkerIcon()));
            mapMarkers.add(marker);
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
                BlockingQueueTask data;
                try {
                    while ((data = queue.poll(5, TimeUnit.MINUTES)) != null){
                        final BlockingQueueTask finalData = data;
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
            displayDetailFragment(service, itemPosition);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }





    /*******************************
     *  Listeners implementations  *
     *******************************/



    /*******************************
     *  ListFragment Listener      *
     *******************************/

    @Override
    public void onListItemClick(Service service, int itemPosition) {
        displayDetailFragment(service, itemPosition);
    }

    @Override
    public void onButtonReturnToMapClick() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(listFragment.get(currentListFragment));
        ft.show(mapFragment);
        ft.commit();
        depth--;
        if (depth == 0 && btnShowList != null)
            btnShowList.setVisibility(View.VISIBLE);
    }

    @Override
    public List<Model> getListOfPoints(Service service) {
        return serviceManager.pointsToDisplayOnList(service, new NoFilter());
    }

    @Override
    public void onNextPressed() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.hide(listFragment.get(currentListFragment));

        if (currentListFragment == listFragment.size()-1)
            currentListFragment = 0;
        else
            currentListFragment++;

        ft.show(listFragment.get(currentListFragment));
        ft.commit();
    }

    @Override
    public void onPreviousPressed() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.hide(listFragment.get(currentListFragment));

        if (currentListFragment == 0)
            currentListFragment = listFragment.size()-1;
        else
            currentListFragment--;

        ft.show(listFragment.get(currentListFragment));
        ft.commit();
    }



    /*******************************
     *  DetailFragment Listener    *
     *******************************/


    @Override
    public void onButtonReturnClick() {
        if (depth == 2){
            replaceDetailFragmentWith(listFragment.get(currentListFragment));
        } else if (depth == 1){
            replaceDetailFragmentWith(mapFragment);
        }
        depth--;
        if (depth == 0 && btnShowList != null)
            btnShowList.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFocusRequired(Model point, Service service) {
        if (!isDisplayedOnMap(point)) {
            Log.d(TAG, "onFocusRequired - new Marker()");
            Marker marker = mMap.addMarker(new MarkerOptions().position(point.getLatLng())
                    .title(service.getType().toString())
                    .icon(service.getMarkerIcon()));
            mapMarkers.add(marker);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(point.getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.f));
        if (depth == 1) {
            onButtonReturnClick();
        } else if (depth == 2) {
            depth--;
            onButtonReturnClick();
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

    public static Intent getIntent(Context ctx, ArrayList<Service> itemsToDisplay, String filterType, int nbPoints){
        Intent i = new Intent(ctx, MapsActivity.class);
        i.putParcelableArrayListExtra(LIST_OF_SERVICES, itemsToDisplay);
        i.putExtra(TYPE_OF_FILTER, filterType);
        i.putExtra(NB_POINTS, nbPoints);
        return i;
    }

    private boolean isDisplayedOnMap(Model point){
        for (Marker marker : mapMarkers){
            if (marker.getPosition().equals(point.getLatLng()))
                return true;
        }
        return false;
    }

    private void displayDetailFragment(Service service, int itemPosition) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (depth == 0)
            ft.hide(mapFragment);
        else if (depth == 1)
            ft.hide(listFragment.get(currentListFragment));

        if (service.getType().equals(ServiceType.TOILET)) {
            ft.add(R.id.fragment_container, ToiletDetailFragment.newInstance(service, itemPosition), DETAIL_FRAGMENT_TAG);
        } else if (service.getType().equals(ServiceType.GARDEN)) {
            ft.add(R.id.fragment_container, GardenDetailFragment.newInstance(service, itemPosition), DETAIL_FRAGMENT_TAG);
        } else if (service.getType().equals(ServiceType.PARKING)){
            ft.add(R.id.fragment_container, ParkingDetailFragment.newInstance(service, itemPosition), DETAIL_FRAGMENT_TAG);
        } else if (service.getType().equals(ServiceType.CYCLEPARK)){
            ft.add(R.id.fragment_container, CycleParkDetailFragment.newInstance(service, itemPosition), DETAIL_FRAGMENT_TAG);
        }
        ft.commit();
        depth++;
    }

    private void replaceDetailFragmentWith(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_TAG));
        ft.show(fragment);
        ft.commit();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }*/
}