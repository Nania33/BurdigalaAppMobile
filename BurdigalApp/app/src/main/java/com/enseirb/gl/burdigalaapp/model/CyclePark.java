package com.enseirb.gl.burdigalaapp.model;

import com.enseirb.gl.burdigalaapp.dto.PointS;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nania on 17/11/2015.
 */
public class CyclePark{
    private PointS Point;
    private String FixationType;
    private String ParkingSpotNumber;

    public CyclePark(String fixationType, String parkingSpotNumber, PointS point){
        this.Point = point;
        this.FixationType = fixationType;
        this.ParkingSpotNumber = parkingSpotNumber;
    }

    @Override
    public String toString(){
        return  Point.toString() +
                "Fixation Type: " + FixationType + "\n" +
                "Parking Spot Number: " + ParkingSpotNumber + "\n\n";
    }
}
