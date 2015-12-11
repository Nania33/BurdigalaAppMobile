package com.enseirb.gl.burdigalaapp.business;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.listener.IParkingBusinessListener;
import com.enseirb.gl.burdigalaapp.converter.ParkingConverter;
import com.enseirb.gl.burdigalaapp.converter.IParkingConverter;
import com.enseirb.gl.burdigalaapp.converter.listener.IParkingConverterListener;
import com.enseirb.gl.burdigalaapp.business.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

/**
 * Created by rchabot on 05/12/15.
 */
public class ParkingBusiness implements IParkingBusiness{
    private static final String TAG = "ParkingBusiness";
    private IParkingConverter parkingConverter;
    private Filter filter;

    public ParkingBusiness(Filter filter) {
        this.parkingConverter = new ParkingConverter();
        this.filter = filter;
    }

    @Override
    public void retrieveParkingPlaces(OpenDataRetriever retriever, final IParkingBusinessListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        parkingConverter.retrieveParkingPlaces(retriever, new IParkingConverterListener() {
            @Override
            public void onSuccess(final ParkingContainer parking) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                // TODO appliquer le filtre
                ParkingContainer p = filter.filterModels(parking);
                listener.onSuccess(p.getModels());
                Log.d(TAG, "[retrievePlaces()] - onSuccess - end");
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
    }

    @Override
    public ParkingContainer filterParkings(ParkingContainer container) {
        return filter.filterModels(container);
    }
}
