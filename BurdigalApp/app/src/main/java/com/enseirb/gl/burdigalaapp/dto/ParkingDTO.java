package com.enseirb.gl.burdigalaapp.dto;

/**
 * Created by Nania on 17/11/2015.
 */
public class ParkingDTO{
    private String Name;
    private PointS Point;
    private String ParkingSpotNumber;
    private String ParkingType;

    public ParkingDTO(String name, String parkingSpotNumber, String parkingType, PointS point){
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
}