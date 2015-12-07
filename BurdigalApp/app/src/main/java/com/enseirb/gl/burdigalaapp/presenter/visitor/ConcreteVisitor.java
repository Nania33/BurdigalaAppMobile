package com.enseirb.gl.burdigalaapp.presenter.visitor;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.business.CycleParkBusiness;
import com.enseirb.gl.burdigalaapp.business.GardenBusiness;
import com.enseirb.gl.burdigalaapp.business.ParkingBusiness;
import com.enseirb.gl.burdigalaapp.business.ToiletBusiness;
import com.enseirb.gl.burdigalaapp.business.listener.ICycleParkBusinessListener;
import com.enseirb.gl.burdigalaapp.business.listener.IGardenBusinessListener;
import com.enseirb.gl.burdigalaapp.business.listener.IParkingBusinessListener;
import com.enseirb.gl.burdigalaapp.business.listener.IToiletBusinessListener;
import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.presenter.visitor.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

import java.util.List;

/**
 * Created by rchabot on 02/12/15.
 */
public class ConcreteVisitor implements Visitor {
    private static final String TAG = "ConcreteVisitor";

    @Override
    public void callToBusiness(final GardenContainer container, final IPresenterListener listener, Filter filter, OpenDataRetriever retriever){
        GardenBusiness gardenBusiness = new GardenBusiness(filter);
        Log.d(TAG, "[retrievePlaces()] - start retrieve gardens");
        gardenBusiness.retrieveGardenPlaces(retriever, new IGardenBusinessListener() {
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
    public void callToBusiness(final CycleParkContainer container, final IPresenterListener listener, Filter filter, OpenDataRetriever retriever) {
        CycleParkBusiness cycleParkBusiness = new CycleParkBusiness(filter);
        Log.d(TAG, "[retrievePlaces()] - start retrieve cycleparks");
        cycleParkBusiness.retrieveCycleParkPlaces(retriever, new ICycleParkBusinessListener() {
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
    public void callToBusiness(final ToiletContainer container, final IPresenterListener listener, Filter filter, OpenDataRetriever retriever) {
        ToiletBusiness toiletBusiness = new ToiletBusiness(filter);
        Log.d(TAG, "[retrievePlaces()] - start retrieve toilets");
        toiletBusiness.retrieveToiletPlaces(retriever, new IToiletBusinessListener() {
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
    public void callToBusiness(final ParkingContainer container, final IPresenterListener listener, Filter filter, OpenDataRetriever retriever) {
        ParkingBusiness parkingBusiness = new ParkingBusiness(filter);
        Log.d(TAG, "[retrievePlaces()] - start retrieve parkings");
        parkingBusiness.retrieveParkingPlaces(retriever, new IParkingBusinessListener() {
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
