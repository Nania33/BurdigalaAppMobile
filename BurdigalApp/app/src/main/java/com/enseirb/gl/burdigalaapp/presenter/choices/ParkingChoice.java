package com.enseirb.gl.burdigalaapp.presenter.choices;

import com.enseirb.gl.burdigalaapp.R;

/**
 * Created by Nania on 23/11/2015.
 */
public class ParkingChoice implements Choice{
    private static String name = "Parking";
    private static String description = "Parking pour voitures";
    private static int color = R.color.red;

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getBackgroundColor(){
        return color;
    }
}
