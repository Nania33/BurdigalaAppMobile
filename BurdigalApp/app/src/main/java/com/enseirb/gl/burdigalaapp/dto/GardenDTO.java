package com.enseirb.gl.burdigalaapp.dto;

/**
 * Created by rchabot on 17/11/15.
 */
public class GardenDTO {
    private String Name;
    private String ParcType;
    private String Use;
    private String GestionType;
    private String Label;
    private PointS Point;

    public GardenDTO(String name, String type, String use, String gestionType, String label, PointS point){
        this.Name = name;
        this.Point = point;
        this.ParcType = type;
        this.Use = use;
        this.GestionType = gestionType;
        this.Label = label;
    }

    @Override
    public String toString(){
        return  "Name: " + Name + "\n" +
                Point.toString() +
                "Parc Type: " + ParcType + "\n" +
                "Use:" + Use + "\n" +
                "Gestion Type: " + GestionType + "\n" +
                "Label: " + Label + "\n\n";
    }
}