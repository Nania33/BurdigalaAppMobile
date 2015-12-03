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
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;

import java.util.List;

/**
 * Created by rchabot on 02/12/15.
 */
public class WebRetriever implements OpenDataRetriever {
    private static final String TAG = "WebRetriever";

    @Override
    public void retrievePlaces(final GardenContainer container) {
        GardenBusiness gardenBusiness = new GardenBusiness();
        Log.d(TAG, "[retrievePlaces()] - start");
        gardenBusiness.retrieveGardenPlaces(new IGardenBusinessListener() {
            @Override
            public void onSuccess(List<Garden> garden) {
                container.put(garden);
            }

            @Override
            public void onError(String message) {
                //TODO afficher boite de dialogue avec message
            }
        });
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrievePlaces(CycleParkContainer container) {

    }

    @Override
    public void retrievePlaces(final ToiletContainer container) {
        ToiletBusiness toiletBusiness = new ToiletBusiness();
        Log.d(TAG, "[retrievePlaces()] - start");
        toiletBusiness.retrieveToiletPlaces(new IToiletBusinessListener() {
            @Override
            public void onSuccess(List<Toilet> toilet) {
                container.put(toilet);
            }

            @Override
            public void onError(String message) {
                //TODO afficher boite de dialogue avec message
            }
        });
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrievePlaces(ParkingContainer container) {

    }


}
