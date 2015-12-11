package com.enseirb.gl.burdigalaapp.model.data;

import com.google.android.gms.maps.model.LatLng;

public class Toilet implements Model{
    private LatLng point;
    private String address;
    private String neighbourhood;
    private String toiletType;

    public Toilet(String address, String neighbourhood, String toiletType, LatLng point){
        this.point = point;
        this.address = address;
        this.neighbourhood = neighbourhood;
        this.toiletType = toiletType;
    }

    @Override
    public String toString(){
        return address + " (" + toiletType + ")";

    }

    @Override
    public LatLng getLatLng() {
        return point;
    }

    public String getAddress() {
        return address;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public String getToiletType() {
        return toiletType;
    }
}
