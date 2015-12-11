package com.enseirb.gl.burdigalaapp.presenter.conf;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.filters.Filter;
import com.enseirb.gl.burdigalaapp.business.filters.LinearFilter;
import com.enseirb.gl.burdigalaapp.business.filters.NearestPointsFilter;
import com.google.android.gms.maps.model.LatLng;

public class Conf {
    private static final String TAG = "Conf";

    public static final String LINEAR_FILTER = "linear_filter";
    public static final String NEAREST_FILTER = "nearest_filter";


    public static Filter makeFilter(String conf, int nbPoints, LatLng location){
        switch (conf) {
            case LINEAR_FILTER:
                Log.d(TAG, "LinearFilter");
                return new LinearFilter(nbPoints);
            case NEAREST_FILTER:
                Log.d(TAG, "NearestPointFilter " + nbPoints + " at " + location);
                return new NearestPointsFilter(nbPoints, location);
            default:
                return null;
        }
    }

}
