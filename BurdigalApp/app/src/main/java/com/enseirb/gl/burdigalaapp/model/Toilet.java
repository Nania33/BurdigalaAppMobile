package com.enseirb.gl.burdigalaapp.model;

import com.enseirb.gl.burdigalaapp.dto.PointS;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Nania on 17/11/2015.
 */
public class Toilet implements Model{
    private LatLng Point;
    private String Address;
    private String Neighbourhood;
    private String ToiletType;

    public Toilet(String address, String neighbourhood, String toiletType, LatLng point){
        this.Point = point;
        this.Address = address;
        this.Neighbourhood = neighbourhood;
        this.ToiletType = toiletType;
    }

    @Override
    public String toString(){
        return  Point.toString() +
                "Address: " + Address + "\n" +
                "Neighbourood: " + Neighbourhood + "\n" +
                "Toilet Type: " + ToiletType + "\n\n";

    }
    @Override
    public LatLng getPosition(){return null;}
}
