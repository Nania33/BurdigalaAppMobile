package com.enseirb.gl.burdigalaapp.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rchabot on 17/11/15.
 */
public class Garden {
    private String name;
    private LatLng coords;


    public Garden(String name, LatLng coords) {
        this.name = name;
        this.coords = coords;
    }
}
