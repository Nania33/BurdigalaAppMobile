package com.enseirb.gl.burdigalaapp.presenter.choices;

import com.enseirb.gl.burdigalaapp.R;

/**
 * Created by Nania on 23/11/2015.
 */
public class CycleParkChoice implements Choice{
    private static String name = "Parking deux roues";
    private static String description = "Parking pour vélo et mobilette";
    private static int color = R.color.orange;

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
