package com.enseirb.gl.burdigalaapp.dto;

/**
 * Created by Nania on 17/11/2015.
 */

public class ToiletDTO{
    private PointS Point;
    private String Address;
    private String Neighbourhood;
    private String ToiletType;

    public ToiletDTO(String address, String neighbourhood, String toiletType, PointS point){
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
}
