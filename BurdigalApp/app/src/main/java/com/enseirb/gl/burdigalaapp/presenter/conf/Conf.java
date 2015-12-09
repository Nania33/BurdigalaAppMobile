package com.enseirb.gl.burdigalaapp.presenter.conf;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.filters.LinearFilter;
import com.enseirb.gl.burdigalaapp.filters.NearestPointsFilter;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by rchabot on 09/12/15.
 */
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
