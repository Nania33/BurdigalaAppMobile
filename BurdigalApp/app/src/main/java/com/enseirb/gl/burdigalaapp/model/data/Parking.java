package com.enseirb.gl.burdigalaapp.model.data;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nania on 17/11/2015.
 */
public class Parking implements Model{
    private String name;
    private LatLng point;
    private String parkingSpotNumber;
    private String parkingType;

    public Parking(String name, String parkingSpotNumber, String parkingType, LatLng point){
        this.point = point;
        this.name = name;
        this.parkingSpotNumber = parkingSpotNumber;
        this.parkingType = parkingType;
    }

    @Override
    public String toString(){
        return  "name: " + name + "\n" +
                point.toString() +
                "Parking Spot Number: " + parkingSpotNumber + "\n" +
                "Parking Type: " + parkingType + "\n\n";
    }

    public String getName() {
        return name;
    }

    @Override
    public LatLng getLatLng() {
        return point;
    }

    public String getParkingSpotNumber() {
        return parkingSpotNumber;
    }

    public String getParkingType() {
        return parkingType;
    }
}