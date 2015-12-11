package com.enseirb.gl.burdigalaapp.dto;

import com.google.android.gms.maps.model.LatLng;

public class PointS {
    private String x;
    private String y;

    public PointS(String x, String y){
        this.x = x;
        this.y = y;
    }

    public LatLng toLatLng(){
        double lat = 0;
        double lng = 0;
        try {
            lat = Double.valueOf(x);
            lng = Double.valueOf(y);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new LatLng(lat, lng);
    }

    @Override
    public String toString(){
        return "X Coordinate: " + x + "\n" +
                "Y Coordinate: " + y + "\n";
    }
}
