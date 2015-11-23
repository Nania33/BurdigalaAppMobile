package com.enseirb.gl.burdigalaapp.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rchabot on 17/11/15.
 */
public class Garden implements Model {
    private String name;
    private LatLng coords;


    public Garden(String name, LatLng coords) {
        this.name = name;
        this.coords = coords;
    }

    @Override
    public LatLng getPosition(){
        return coords;
    }
}
