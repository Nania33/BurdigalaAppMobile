package com.enseirb.gl.burdigalaapp.presenter.choices;

import com.enseirb.gl.burdigalaapp.R;

/**
 * Created by Nania on 23/11/2015.
 */
public class GardenChoice implements Choice{
    private static String name = "Parcs et jardins";
    private static String description = "Parcs et jardins dans bordeaux";
    private static int color = R.color.blue;

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
