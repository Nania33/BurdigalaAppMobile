package com.enseirb.gl.burdigalaapp.business.filters;

import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class NearestPointsFilter implements Filter {
    private int nbPoints;
    private LatLng currentLocation;
    private ArrayList<Integer> listPositions;
    private ArrayList <Double> listDistances;

    public NearestPointsFilter(int nbPoints, LatLng currentLocation){
        super();
        this.nbPoints = nbPoints;
        this.currentLocation = currentLocation;
    }


    @Override
    public GardenContainer filterModels(GardenContainer container) {
        List<Garden> result = new ArrayList<>();
        nearestPositions(container.getModels(), nbPoints);
        for (int i = 0 ; i<listPositions.size() ; i++){
            result.add(container.getModels().get(listPositions.get(i)));
        }
        return new GardenContainer(result);
    }



    @Override
    public CycleParkContainer filterModels(CycleParkContainer container) {
        List<CyclePark> result = new ArrayList<>();
        nearestPositions(container.getModels(), nbPoints);
        for (int i = 0 ; i<listPositions.size() ; i++){
            result.add(container.getModels().get(listPositions.get(i)));
        }
        return new CycleParkContainer(result);
    }

    @Override
    public ParkingContainer filterModels(ParkingContainer container) {
        List<Parking> result = new ArrayList<>();
        nearestPositions(container.getModels(), nbPoints);
        for (int i = 0 ; i<listPositions.size() ; i++){
            result.add(container.getModels().get(listPositions.get(i)));
        }
        return new ParkingContainer(result);
    }

    @Override
    public ToiletContainer filterModels(ToiletContainer container) {
        List<Toilet> result = new ArrayList<>();
        nearestPositions(container.getModels(), nbPoints);
        for (int i = 0 ; i<listPositions.size() ; i++){
            result.add(container.getModels().get(listPositions.get(i)));
        }
        return new ToiletContainer(result);
    }


    public void nearestPositions(List<? extends Model> models, int numberLocations){
        listPositions = new ArrayList();
        listDistances = new ArrayList();
        int i = 0;
        while (i < models.size() && i < numberLocations){
            insertPosition(models.get(i).getLatLng(),i);
            i ++;
        }
        while (i < models.size()){
            if (computeDistanceLocation(models.get(i).getLatLng()) < listDistances.get(numberLocations - 1)){
                insertPosition(models.get(i).getLatLng(), i);
                listPositions.remove(numberLocations);
                listDistances.remove(numberLocations);
            }
            i ++;
        }
    }


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


}