package com.enseirb.gl.burdigalaapp.florian;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;

/**
 * Created by flo on 29/11/15.
 */
public class nearestPointsFilter implements Filter {
    private final int N_NEAREST_LOCATIONS = 10;
    private LatLng currentLocation;
    private LinkedList <Integer> listPositions;
    private LinkedList <Double> listDistances;

    public nearestPointsFilter(LatLng currentLocation){
        super();
        this.currentLocation = currentLocation;
    }

    /*@Override
    public ModelContainer filterModels(List<Model> models) {
        List<Model> selectedModels = new LinkedList<Model>();
        nearestPositions(models, N_NEAREST_LOCATIONS);
        for (int i = 0 ; i<listPositions.size() ; i++){
            selectedModels.add(models.get(listPositions.get(i)));
        }
        return null;
    }*/

/*
    public void nearestPositions(List<Model> models, int numberLocations){
        listPositions = new LinkedList();
        listDistances = new LinkedList();
        int i = 0;
        while (i < models.size() && i < numberLocations){
            insertPosition(models.get(i).getPosition(),i);
            i ++;
        }
        while (i < models.size()){
            if (computeDistanceLocation(models.get(i).getPosition()) < listDistances.get(numberLocations - 1)){
                insertPosition(models.get(i).getPosition(), i);
                listPositions.remove(numberLocations - 1);
                listDistances.remove(numberLocations - 1);
            }
            i ++;
        }
    }
    */

    public void insertPosition(LatLng position, int positionModel){
        int i = 0;
        double distance = computeDistanceLocation(position);
        while (i < listDistances.size() && distance > listDistances.get(i)){
            i++;
        }
        listDistances.add(i,distance);
        listPositions.add(i,positionModel);

    }

    public double computeDistanceLocation(LatLng position){
        double ourLatitude = currentLocation.latitude;
        double ourLongitude = currentLocation.longitude;
        double latitude = position.latitude;
        double longitude = position.longitude;
        double distance = Math.sqrt(Math.pow((latitude - ourLatitude), 2) + Math.pow((longitude - ourLongitude), 2));
        return distance;
    }

    @Override
    public GardenContainer filterModels(GardenContainer container) {
        return null;
    }

    @Override
    public CycleParkContainer filterModels(CycleParkContainer container) {
        return null;
    }

    @Override
    public ParkingContainer filterModels(ParkingContainer container) {
        return null;
    }

    @Override
    public ToiletContainer filterModels(ToiletContainer container) {
        return null;
    }
}
