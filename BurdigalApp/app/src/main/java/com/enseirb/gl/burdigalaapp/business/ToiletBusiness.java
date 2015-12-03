package com.enseirb.gl.burdigalaapp.business;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.listener.IToiletBusinessListener;
import com.enseirb.gl.burdigalaapp.business.listener.IToiletBusinessListener;
import com.enseirb.gl.burdigalaapp.converter.ToiletConverter;
import com.enseirb.gl.burdigalaapp.converter.IToiletConverter;
import com.enseirb.gl.burdigalaapp.converter.listener.IToiletConverterListener;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;

import java.util.List;

/**
 * Created by rchabot on 03/12/15.
 */
public class ToiletBusiness implements IToiletBusiness {
    private static final String TAG = "ToiletBusiness";
    private IToiletConverter gardenConverter;

    public ToiletBusiness() {
        this.gardenConverter = new ToiletConverter();
    }

    @Override
    public void retrieveToiletPlaces(final IToiletBusinessListener listener) {
        Log.d(TAG, "[retrieveToiletPlaces()] - start");
        gardenConverter.retrieveToiletPlaces(new IToiletConverterListener() {
            @Override
            public void onSuccess(List<Toilet> garden) {
                Log.d(TAG, "[retrieveToiletPlaces()] - onSuccess - start");
                listener.onSuccess(garden);
                Log.d(TAG, "[retrieveToiletPlaces()] - onSuccess - end");
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
    }
}
