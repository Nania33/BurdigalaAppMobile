package com.enseirb.gl.burdigalaapp.model.data;

import com.google.android.gms.maps.model.LatLng;

public class Garden implements Model {
    private String name;
    private String parcType;
    private String use;
    private String gestionType;
    private String label;
    private LatLng point;

    public Garden(String name, String type, String use, String gestionType, String label, LatLng point){
        this.name = name;
        this.point = point;
        this.parcType = type;
        this.use = use;
        this.gestionType = gestionType;
        this.label = label;
    }

    @Override
    public String toString(){
        return name;
    }

    public String getParcType() {
        return parcType;
    }

    public String getName() {
        return name;
    }

    public String getUse() {
        return use;
    }

    public String getGestionType() {
        return gestionType;
    }

    public String getLabel() {
        return label;
    }

    public LatLng getLatLng() {
        return point;
    }
}
