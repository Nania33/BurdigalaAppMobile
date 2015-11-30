package com.enseirb.gl.burdigalaapp.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nania on 17/11/2015.
 */
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
        return  point.toString() +
                "address: " + address + "\n" +
                "Neighbourood: " + neighbourhood + "\n" +
                "Toilet Type: " + toiletType + "\n\n";

    }

    @Override
    public LatLng getPoint() {
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
