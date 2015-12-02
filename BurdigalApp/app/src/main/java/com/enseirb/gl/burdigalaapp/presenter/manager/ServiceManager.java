package com.enseirb.gl.burdigalaapp.presenter.manager;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.IModelContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;
import com.enseirb.gl.burdigalaapp.presenter.service.ServiceType;
import com.enseirb.gl.burdigalaapp.retriever.WebRetriever;

import java.util.ArrayList;

/**
 * Created by rchabot on 02/12/15.
 */
public class ServiceManager {
    private static final String TAG = "ServiceManager";

    // TODO utilisé une map de container plutôt pour pouvoir en ajouter ?
    // Clé service, value container ?
    public CycleParkContainer cycleParkContainer;
    public GardenContainer gardenContainer;
    public ParkingContainer parkingContainer;
    public ToiletContainer toiletContainer;

    public void initializeServices(ArrayList<Service> services){
        for (Service service : services) {
            if (service.isSelected()) {
                if (service.getType().equals(ServiceType.CYCLEPARK)) {
                    cycleParkContainer = new CycleParkContainer();
                    cycleParkContainer.retrievePlaces(new WebRetriever());
                    Log.d(TAG, "cyclePark initialized");
                }
                if (service.getType().equals(ServiceType.GARDEN)) {
                    gardenContainer = new GardenContainer();
                    gardenContainer.retrievePlaces(new WebRetriever());
                    Log.d(TAG, "gardens initialized");
                }
                if (service.getType().equals(ServiceType.PARKING)) {
                    parkingContainer = new ParkingContainer();
                    parkingContainer.retrievePlaces(new WebRetriever());
                    Log.d(TAG, "parking initialized");
                }
                if (service.getType().equals(ServiceType.TOILET)) {
                    toiletContainer = new ToiletContainer();
                    toiletContainer.retrievePlaces(new WebRetriever());
                    Log.d(TAG, "toilets initialized");
                }
            }
        }
    }

    public void debug(){
        Log.d(TAG, "cyclePark size :" + cycleParkContainer.getModels().size());
        Log.d(TAG, "toilet size :" + toiletContainer.getModels().size());
        Log.d(TAG, "parkingPark size :" + parkingContainer.getModels().size());
        Log.d(TAG, "gardenPark size :" + gardenContainer.getModels().size());
    }

    public IModelContainer getContainer(Service service){
        Log.d(TAG, "getContainer() "+service.getType().toString());
        switch (service.getType()){
            case TOILET:
                return toiletContainer;
            case PARKING:
                return parkingContainer;
            case CYCLEPARK:
                return cycleParkContainer;
            case GARDEN:
                return gardenContainer;
            default:
                return null;
        }
    }


}
