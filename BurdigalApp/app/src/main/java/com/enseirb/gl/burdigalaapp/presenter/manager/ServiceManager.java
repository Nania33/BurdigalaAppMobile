package com.enseirb.gl.burdigalaapp.presenter.manager;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.exceptions.UnknownDataException;
import com.enseirb.gl.burdigalaapp.exceptions.UnknownServiceException;
import com.enseirb.gl.burdigalaapp.file.FileManager;
import com.enseirb.gl.burdigalaapp.business.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.container.ContainerFactory;
import com.enseirb.gl.burdigalaapp.model.container.IModelContainer;
import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.model.service.Service;
import com.enseirb.gl.burdigalaapp.model.service.ServiceType;
import com.enseirb.gl.burdigalaapp.presenter.visitor.ConcreteBusinessVisitor;
import com.enseirb.gl.burdigalaapp.presenter.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.FileRetriever;
import com.enseirb.gl.burdigalaapp.dao.retriever.WebRetriever;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rchabot on 02/12/15.
 */
public class ServiceManager {
    private static final String TAG = "ServiceManager";

    private static final double bordeauxCenterLat = 44.836758;
    private static final double bordeauxCenterLong = -0.578746;

    private Map<Service, IModelContainer> myServices;
    private ServiceManagerListener mListener;
    private FileManager fileManager;
    private Context context;

    public ServiceManager(Context context, ArrayList<Service> services, ServiceManagerListener listener){
        this.context = context;
        this.fileManager = new FileManager(context);
        mListener = listener;
        myServices = new HashMap<>();
        for (Service service : services) {
            try {
                myServices.put(service, ContainerFactory.makeContainer(service.getType()));
                Log.d(TAG, "New service : "+service);
            } catch (UnknownDataException e) {
                e.printStackTrace();
            }
        }
    }

    public void initializeServices(Filter filter){
        for (final Service service : myServices.keySet()) {
            if (service.isSelected()) {
                IModelContainer container = myServices.get(service);
                IPresenterListener listener = new IPresenterListener() {
                    @Override
                    public void onDataRetreived() {
                        mListener.onDataRetrieved(service);
                    }

                    @Override
                    public void onError(String message) {
                        mListener.onError(service, message);
                    }
                };

                if (fileManager.fileUpdateNeeded(service)) {
                    container.retrievePlaces(new ConcreteBusinessVisitor(), filter,
                            new WebRetriever(context, service), listener);
                    Log.d(TAG, "RetrieveData for service : " + service);
                } else {
                    container.retrievePlaces(new ConcreteBusinessVisitor(), filter,
                            new FileRetriever(context, service), listener);
                    Log.d(TAG, "RetrieveData from files for service : " + service);
                }
            }
        }
    }

    public List<Model> pointsToDisplayOnMap(Service service, Filter filter){
        return getContainer(service).applyFilter(new ConcreteBusinessVisitor(), filter).getModels();
    }

    public List<Model> pointsToDisplayOnList(Service service, Filter filter){
        return getContainer(service).applyFilter(new ConcreteBusinessVisitor(), filter).getModels();
    }

    public LatLng getLastBestLocation() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
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
            return new LatLng(locationGPS.getLatitude(), locationGPS.getLongitude());
        }

        else if(locationNet != null) {
            return new LatLng(locationNet.getLatitude(), locationNet.getLongitude());
        }

        // hard coded values at the center of Bordeaux if we can't get the location of the user.
        return new LatLng(bordeauxCenterLat, bordeauxCenterLong);

    }


    public IModelContainer getContainer(Service service){
        return myServices.get(service);
    }

    public Service getService(ServiceType serviceType) throws UnknownServiceException {
        for (Service serv : myServices.keySet()){
            if (serv.getType().equals(serviceType)){
                return serv;
            }
        }
        throw new UnknownServiceException("Service " + serviceType.toString() + " inconnu");
    }

    public int getPointIndex (Service service, LatLng position) throws UnknownDataException {
        IModelContainer container = myServices.get(service);
        List<Model> models = container.getModels();
        Model model;
        for (int i=0;i<models.size();i++){
            model = models.get(i);
            if (model.getLatLng().equals(position)){
                return i;
            }
        }
        throw new UnknownDataException("Position " + position.toString() + " inconnue");
    }

    public interface ServiceManagerListener {
        void onError(Service service, String message);
        void onDataRetrieved(Service service);
    }
}