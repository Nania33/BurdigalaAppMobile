package com.enseirb.gl.burdigalaapp.dto;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rchabot on 17/11/15.
 */
public class GardenDTO {
    private String name;
    private LatLng coords;

    public GardenDTO(String name, LatLng coords) {
        this.name = name;
        this.coords = coords;
    }

    public String getName() {
        return name;
    }

    public LatLng getCoords() {
        return coords;
    }

    @Override
    public String toString() {
        return name + " " + coords.toString();
    }
}
