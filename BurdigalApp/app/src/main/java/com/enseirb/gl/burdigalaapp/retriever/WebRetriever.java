package com.enseirb.gl.burdigalaapp.retriever;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.CycleParkBusiness;
import com.enseirb.gl.burdigalaapp.business.GardenBusiness;
import com.enseirb.gl.burdigalaapp.business.ParkingBusiness;
import com.enseirb.gl.burdigalaapp.business.ToiletBusiness;
import com.enseirb.gl.burdigalaapp.business.listener.ICycleParkBusinessListener;
import com.enseirb.gl.burdigalaapp.business.listener.IGardenBusinessListener;
import com.enseirb.gl.burdigalaapp.business.listener.IParkingBusinessListener;
import com.enseirb.gl.burdigalaapp.business.listener.IToiletBusinessListener;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
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
        Log.d(TAG, "[retrievePlaces()] - start retrieve gardens");
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
        Log.d(TAG, "[retrievePlaces()] - end retrieve gardens");
    }

    @Override
    public void retrievePlaces(final CycleParkContainer container, final DataRetrieverListener listener) {
        CycleParkBusiness cycleParkBusiness = new CycleParkBusiness();
        Log.d(TAG, "[retrievePlaces()] - start retrieve cycleparks");
        cycleParkBusiness.retrieveCycleParkPlaces(new ICycleParkBusinessListener() {
            @Override
            public void onSuccess(List<CyclePark> cyclePark) {
                container.put(cyclePark);
                listener.onDataRetreived();
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
        Log.d(TAG, "[retrievePlaces()] - end retrieve cycleparks");
    }

    @Override
    public void retrievePlaces(final ToiletContainer container, final DataRetrieverListener listener) {
        ToiletBusiness toiletBusiness = new ToiletBusiness();
        Log.d(TAG, "[retrievePlaces()] - start retrieve toilets");
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
        Log.d(TAG, "[retrievePlaces()] - end retrieve toilets");
    }

    @Override
    public void retrievePlaces(final ParkingContainer container, final DataRetrieverListener listener) {
        ParkingBusiness parkingBusiness = new ParkingBusiness();
        Log.d(TAG, "[retrievePlaces()] - start retrieve parkings");
        parkingBusiness.retrieveParkingPlaces(new IParkingBusinessListener() {
            @Override
            public void onSuccess(List<Parking> parking) {
                container.put(parking);
                listener.onDataRetreived();
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
        Log.d(TAG, "[retrievePlaces()] - end retrieve parkings");
    }


}
