package com.enseirb.gl.burdigalaapp.presenter.manager;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.exceptions.UnknownDataException;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.IModelContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;
import com.enseirb.gl.burdigalaapp.presenter.service.ServiceType;
import com.enseirb.gl.burdigalaapp.retriever.WebRetriever;
import com.enseirb.gl.burdigalaapp.retriever.listener.DataRetrieverListener;

import java.util.ArrayList;
import java.util.HashMap;
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

    public interface ServiceManagerListener {
        void onError(Service service, String message);
        void onDataRetrieved(Service service);
    }
}
