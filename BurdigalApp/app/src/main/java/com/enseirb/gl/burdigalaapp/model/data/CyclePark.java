package com.enseirb.gl.burdigalaapp.model.data;

import com.google.android.gms.maps.model.LatLng;

public class CyclePark implements Model{
    private LatLng point;
    private String fixationType;
    private String parkingSpotNumber;

    public CyclePark(String fixationType, String parkingSpotNumber, LatLng point){
        this.point = point;
        this.fixationType = fixationType;
        this.parkingSpotNumber = parkingSpotNumber;
    }

    @Override
    public String toString(){
        return  fixationType + " (" + parkingSpotNumber + " places)";
    }

    public String getFixationType() {
        return fixationType;
    }

    public LatLng getLatLng() {
        return point;
    }

    public String getParkingSpotNumber() {
        return parkingSpotNumber;
    }
}
