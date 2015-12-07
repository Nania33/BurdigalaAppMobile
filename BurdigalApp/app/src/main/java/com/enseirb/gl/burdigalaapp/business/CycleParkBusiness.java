package com.enseirb.gl.burdigalaapp.business;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.listener.ICycleParkBusinessListener;
import com.enseirb.gl.burdigalaapp.converter.CycleParkConverter;
import com.enseirb.gl.burdigalaapp.converter.ICycleParkConverter;
import com.enseirb.gl.burdigalaapp.converter.listener.ICycleParkConverterListener;
import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.filters.LinearFilter;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;

import java.util.List;

/**
 * Created by rchabot on 05/12/15.
 */
public class CycleParkBusiness implements ICycleParkBusiness {
    private static final String TAG = "CycleParkBusiness";
    private ICycleParkConverter cycleParkConverter;
    private Filter filter;

    public CycleParkBusiness() {
        this.cycleParkConverter = new CycleParkConverter();
        this.filter = new LinearFilter(10);
    }

    @Override
    public void retrieveCycleParkPlaces(final ICycleParkBusinessListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        cycleParkConverter.retrieveCycleParkPlaces(new ICycleParkConverterListener() {
            @Override
            public void onSuccess(List<CyclePark> cyclePark) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                // TODO appliquer le filtre
                listener.onSuccess(cyclePark);
                Log.d(TAG, "[retrievePlaces()] - onSuccess - end");
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
    }
}
