package com.enseirb.gl.burdigalaapp.business;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.listener.IGardenBusinessListener;
import com.enseirb.gl.burdigalaapp.converter.GardenConverter;
import com.enseirb.gl.burdigalaapp.converter.IGardenConverter;
import com.enseirb.gl.burdigalaapp.converter.listener.IGardenConverterListener;
import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

/**
 * Created by rchabot on 23/11/15.
 */
public class GardenBusiness implements IGardenBusiness {
    private static final String TAG = "GardenBusiness";
    private IGardenConverter gardenConverter;
    private Filter filter;

    public GardenBusiness(Filter filter) {
        this.gardenConverter = new GardenConverter();
        this.filter = filter;
    }

    @Override
    public void retrieveGardenPlaces(OpenDataRetriever retriever, final IGardenBusinessListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        gardenConverter.retrieveGardenPlaces(retriever, new IGardenConverterListener() {
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

    @Override
    public GardenContainer filterGardens(GardenContainer container) {
        return filter.filterModels(container);
    }
}
