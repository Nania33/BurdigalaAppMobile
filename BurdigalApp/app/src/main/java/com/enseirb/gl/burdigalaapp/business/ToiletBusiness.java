package com.enseirb.gl.burdigalaapp.business;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.listener.IToiletBusinessListener;
import com.enseirb.gl.burdigalaapp.converter.ToiletConverter;
import com.enseirb.gl.burdigalaapp.converter.IToiletConverter;
import com.enseirb.gl.burdigalaapp.converter.listener.IToiletConverterListener;
import com.enseirb.gl.burdigalaapp.business.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public class ToiletBusiness implements IToiletBusiness {
    private static final String TAG = "ToiletBusiness";
    private IToiletConverter gardenConverter;
    private Filter filter;

    public ToiletBusiness(Filter filter) {
        this.gardenConverter = new ToiletConverter();
        this.filter = filter;
    }

    @Override
    public void retrieveToiletPlaces(OpenDataRetriever retriever, final IToiletBusinessListener listener) {
        Log.d(TAG, "[retrieveToiletPlaces()] - start");
        gardenConverter.retrieveToiletPlaces(retriever, new IToiletConverterListener() {
            @Override
            public void onSuccess(final ToiletContainer toilet) {
                Log.d(TAG, "[retrieveToiletPlaces()] - onSuccess - start");
                ToiletContainer t = filter.filterModels(toilet);
                listener.onSuccess(t.getModels());
                Log.d(TAG, "[retrieveToiletPlaces()] - onSuccess - end");
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
    }

    @Override
    public ToiletContainer filterToilets(ToiletContainer container) {
        return filter.filterModels(container);
    }
}
