package com.enseirb.gl.burdigalaapp.retriever;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.asynctask.AsyncTaskGetCyclePark;
import com.enseirb.gl.burdigalaapp.asynctask.AsyncTaskGetGarden;
import com.enseirb.gl.burdigalaapp.asynctask.AsyncTaskGetParking;
import com.enseirb.gl.burdigalaapp.asynctask.AsyncTaskGetToilet;
import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;

/**
 * Created by rchabot on 07/12/15.
 */
public class WebRetriever implements OpenDataRetriever {
    private static final String TAG = "WebRetriever";

    @Override
    public void retrieveToiletPlaces(IToiletDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskGetToilet asyncTaskGetToilet = new AsyncTaskGetToilet(listener);
        asyncTaskGetToilet.execute();
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrieveGardenPlaces(IGardenDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskGetGarden asyncTaskGetGarden = new AsyncTaskGetGarden(listener);
        asyncTaskGetGarden.execute();
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrieveCycleParkPlaces(ICycleParkDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskGetCyclePark asyncTaskGetCyclePark = new AsyncTaskGetCyclePark(listener);
        asyncTaskGetCyclePark.execute();
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrieveParkingPlaces(IParkingDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskGetParking asyncTaskGetParking = new AsyncTaskGetParking(listener);
        asyncTaskGetParking.execute();
        Log.d(TAG, "[retrievePlaces()] - end");
    }
}
