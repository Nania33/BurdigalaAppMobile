package com.enseirb.gl.burdigalaapp.dto;

/**
 * Created by rchabot on 17/11/15.
 */
public class GardenDTO {
    private String name;
    private String parcType;
    private String use;
    private String gestionType;
    private String label;
    private PointS point; // Utilisation d'un LatLng ?

    public GardenDTO(String name, String type, String use, String gestionType, String label, PointS point){
        this.name = name;
        this.point = point;
        this.parcType = type;
        this.use = use;
        this.gestionType = gestionType;
        this.label = label;
    }

    @Override
    public String toString(){
        return  "name: " + name + "\n" +
                point.toString() +
                "Parc Type: " + parcType + "\n" +
                "use:" + use + "\n" +
                "Gestion Type: " + gestionType + "\n" +
                "label: " + label + "\n\n";
    }

    public String getName() {
        return name;
    }

    public String getParcType() {
        return parcType;
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

    public PointS getPoint() {
        return point;
    }
}

