package com.enseirb.gl.burdigalaapp.retriever;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.GardenBusiness;
import com.enseirb.gl.burdigalaapp.business.ToiletBusiness;
import com.enseirb.gl.burdigalaapp.business.listener.IGardenBusinessListener;
import com.enseirb.gl.burdigalaapp.business.listener.IToiletBusinessListener;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.retriever.listener.DataRetrieverListener;

import java.util.List;

/**
 * Created by rchabot on 02/12/15.
 */
public class WebRetriever implements OpenDataRetriever {
    private static final String TAG = "WebRetriever";

    @Override
    public void retrievePlaces(final GardenContainer container, final DataRetrieverListener listener) {
        GardenBusiness gardenBusiness = new GardenBusiness();
        Log.d(TAG, "[retrievePlaces()] - start");
        gardenBusiness.retrieveGardenPlaces(new IGardenBusinessListener() {
            @Override
            public void onSuccess(List<Garden> garden) {
                container.put(garden);
                listener.onDataRetreived();
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrievePlaces(CycleParkContainer container, DataRetrieverListener listener) {

    }

    @Override
    public void retrievePlaces(final ToiletContainer container, final DataRetrieverListener listener) {
        ToiletBusiness toiletBusiness = new ToiletBusiness();
        Log.d(TAG, "[retrievePlaces()] - start");
        toiletBusiness.retrieveToiletPlaces(new IToiletBusinessListener() {
            @Override
            public void onSuccess(List<Toilet> toilet) {
                container.put(toilet);
                listener.onDataRetreived();
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrievePlaces(ParkingContainer container, DataRetrieverListener listener) {

    }


}
