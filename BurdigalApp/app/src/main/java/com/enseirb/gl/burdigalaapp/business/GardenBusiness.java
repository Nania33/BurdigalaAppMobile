package com.enseirb.gl.burdigalaapp.business;

import android.content.Context;
import android.location.LocationManager;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.listener.IGardenBusinessListener;
import com.enseirb.gl.burdigalaapp.converter.GardenConverter;
import com.enseirb.gl.burdigalaapp.converter.IGardenConverter;
import com.enseirb.gl.burdigalaapp.converter.listener.IGardenConverterListener;
import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.filters.LinearFilter;
import com.enseirb.gl.burdigalaapp.filters.NearestPointsFilter;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by rchabot on 23/11/15.
 */
public class GardenBusiness implements IGardenBusiness {
    private static final String TAG = "GardenBusiness";
    private IGardenConverter gardenConverter;
    private Filter filter;

    /*public GardenBusiness() {
        this.gardenConverter = new GardenConverter();
        this.filter = new LinearFilter(10);
    }*/

    public GardenBusiness(Filter filter) {
        this.gardenConverter = new GardenConverter();
        this.filter = filter;
    }

    @Override
    public void retrieveGardenPlaces(final IGardenBusinessListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        gardenConverter.retrieveGardenPlaces(new IGardenConverterListener() {
            @Override
            public void onSuccess(final GardenContainer garden) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                GardenContainer g = filter.filterModels(garden);
                listener.onSuccess(g.getModels());
                Log.d(TAG, "[retrievePlaces()] - onSuccess - end");
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
    }
}
