package com.enseirb.gl.burdigalaapp.presenter.manager;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.exceptions.UnknownDataException;
import com.enseirb.gl.burdigalaapp.exceptions.UnknownServiceException;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.IModelContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;
import com.enseirb.gl.burdigalaapp.presenter.service.ServiceType;
import com.enseirb.gl.burdigalaapp.retriever.WebRetriever;
import com.enseirb.gl.burdigalaapp.retriever.listener.DataRetrieverListener;
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

    private Map<Service, IModelContainer> myServices;
    private ServiceManagerListener mListener;

    public ServiceManager(ArrayList<Service> services, ServiceManagerListener listener){
        mListener = listener;
        myServices = new HashMap<>();
        for (Service service : services) {
            try {
                myServices.put(service, makeContainer(service.getType()));
                Log.d(TAG, "New service : "+service);
            } catch (UnknownDataException e) {
                e.printStackTrace();
            }
        }
    }

    public void initializeServices(){
        for (final Service service : myServices.keySet()) {
            if (service.isSelected()) {
                IModelContainer container = myServices.get(service);
                container.retrievePlaces(new WebRetriever(), new DataRetrieverListener() {
                    @Override
                    public void onDataRetreived() {
                        mListener.onDataRetrieved(service);
                    }

                    @Override
                    public void onError(String message) {
                        mListener.onError(service, message);
                    }
                });
                Log.d(TAG, "RetrieveData for service : " + service);
            }
        }
    }

    public IModelContainer getContainer(Service service){
        return myServices.get(service);
    }


    private static IModelContainer makeContainer(ServiceType type) throws UnknownDataException {
        switch (type) {
            case TOILET:
                return new ToiletContainer();
            case CYCLEPARK:
                return new CycleParkContainer();
            case GARDEN:
                return new GardenContainer();
            case PARKING:
                return new ParkingContainer();
            default:
                throw new UnknownDataException("Type " + type + " inconnu");
        }
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
