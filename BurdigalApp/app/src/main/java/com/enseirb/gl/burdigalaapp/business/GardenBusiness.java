package com.enseirb.gl.burdigalaapp.business;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.listener.IGardenBusinessListener;
import com.enseirb.gl.burdigalaapp.converter.GardenConverter;
import com.enseirb.gl.burdigalaapp.converter.IGardenConverter;
import com.enseirb.gl.burdigalaapp.converter.listener.IGardenConverterListener;
import com.enseirb.gl.burdigalaapp.model.data.Garden;

import java.util.List;

/**
 * Created by rchabot on 23/11/15.
 */
public class GardenBusiness implements IGardenBusiness {
    private static final String TAG = "GardenBusiness";
    private IGardenConverter gardenConverter;

    public GardenBusiness() {
        this.gardenConverter = new GardenConverter();
    }

    @Override
    public void retrieveGardenPlaces(final IGardenBusinessListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        gardenConverter.retrieveGardenPlaces(new IGardenConverterListener() {
            @Override
            public void onSuccess(List<Garden> garden) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                for (Garden grd : garden)
                    Log.d(TAG, grd.toString());
                listener.onSuccess(garden);
                Log.d(TAG, "[retrievePlaces()] - onSuccess - end");
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
    }
}
