package com.enseirb.gl.burdigalaapp.model;

import com.enseirb.gl.burdigalaapp.dto.PointS;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nania on 17/11/2015.
 */
public class Parking implements Model{
    private String Name;
    private LatLng Point;
    private String ParkingSpotNumber;
    private String ParkingType;

    public Parking(String name, String parkingSpotNumber, String parkingType, LatLng point){
        this.Point = point;
        this.Name = name;
        this.ParkingSpotNumber = parkingSpotNumber;
        this.ParkingType = parkingType;
    }

    @Override
    public String toString(){
        return  "Name: " + Name + "\n" +
                Point.toString() +
                "Parking Spot Number: " + ParkingSpotNumber + "\n" +
                "Parking Type: " + ParkingType + "\n\n";
    }
    @Override
    public LatLng getPosition(){return null;}
}