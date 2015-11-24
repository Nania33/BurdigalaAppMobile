package com.enseirb.gl.burdigalaapp.dto;

/**
 * Created by alraffin on 23/11/15.
 */
public class PointS {
    private String x;
    private String y;

    public PointS(String x, String y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "X Coordinate: " + x + "\n" +
                "Y Coordinate: " + y + "\n";
    }
}
