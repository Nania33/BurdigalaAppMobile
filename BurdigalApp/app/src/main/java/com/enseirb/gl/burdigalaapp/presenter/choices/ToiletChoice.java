package com.enseirb.gl.burdigalaapp.presenter.choices;

import android.graphics.Color;

import com.enseirb.gl.burdigalaapp.R;

/**
 * Created by Nania on 23/11/2015.
 */
public class ToiletChoice implements Choice{
    private static String name = "Toilettes";
    private static String description = "Toilettes publiques";
    private static int color = R.color.green;

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
