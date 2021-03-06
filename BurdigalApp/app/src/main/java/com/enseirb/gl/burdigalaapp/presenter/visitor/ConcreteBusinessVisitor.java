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
import com.enseirb.gl.burdigalaapp.business.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.presenter.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

import java.util.List;

public class ConcreteBusinessVisitor implements BusinessVisitor {
    private static final String TAG = "ConcreteBusinessVisitor";

    @Override
    public void callToBusiness(final GardenContainer container, final IPresenterListener listener, Filter filter, OpenDataRetriever retriever) {
        GardenBusiness gardenBusiness = new GardenBusiness(filter);
        Log.d(TAG, "[retrievePlaces()] - start retrieve gardens");
        gardenBusiness.retrieveGardenPlaces(retriever, new IGardenBusinessListener() {
            @Override
            public void onSuccess(List<Garden> garden) {
                container.put(garden);
                listener.onDataRetrieved();
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
                listener.onDataRetrieved();
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
                listener.onDataRetrieved();
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
                listener.onDataRetrieved();
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
        Log.d(TAG, "[retrievePlaces()] - end retrieve parkings");
    }


    @Override
    public GardenContainer applyFilter(GardenContainer container, Filter filter) {
        GardenBusiness business = new GardenBusiness(filter);
        return business.filterGardens(container);
    }

    @Override
    public CycleParkContainer applyFilter(CycleParkContainer container, Filter filter) {
        CycleParkBusiness business = new CycleParkBusiness(filter);
        return business.filterCycleParks(container);
    }

    @Override
    public ToiletContainer applyFilter(ToiletContainer container, Filter filter) {
        ToiletBusiness business = new ToiletBusiness(filter);
        return business.filterToilets(container);
    }

    @Override
    public ParkingContainer applyFilter(ParkingContainer container, Filter filter) {
        ParkingBusiness business = new ParkingBusiness(filter);
        return business.filterParkings(container);
    }


}
