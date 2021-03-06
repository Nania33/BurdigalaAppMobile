package com.enseirb.gl.burdigalaapp.business;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.listener.ICycleParkBusinessListener;
import com.enseirb.gl.burdigalaapp.converter.CycleParkConverter;
import com.enseirb.gl.burdigalaapp.converter.ICycleParkConverter;
import com.enseirb.gl.burdigalaapp.converter.listener.ICycleParkConverterListener;
import com.enseirb.gl.burdigalaapp.business.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public class CycleParkBusiness implements ICycleParkBusiness {
    private static final String TAG = "CycleParkBusiness";
    private ICycleParkConverter cycleParkConverter;
    private Filter filter;

    public CycleParkBusiness(Filter filter) {
        this.cycleParkConverter = new CycleParkConverter();
        this.filter = filter;
    }

    @Override
    public void retrieveCycleParkPlaces(OpenDataRetriever retriever, final ICycleParkBusinessListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        cycleParkConverter.retrieveCycleParkPlaces(retriever, new ICycleParkConverterListener() {
            @Override
            public void onSuccess(final CycleParkContainer cyclePark) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                CycleParkContainer p = filter.filterModels(cyclePark);
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
    public CycleParkContainer filterCycleParks(CycleParkContainer container) {
        return filter.filterModels(container);
    }
}
