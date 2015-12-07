package com.enseirb.gl.burdigalaapp.dao;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.asynctask.AsyncTaskGetParking;
import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;

/**
 * Created by rchabot on 05/12/15.
 */
public class OpenDataParkingDAO implements IParkingDAO {
    public static final String TAG = "OpenDataParkingDAO";

    @Override
    public void retrieveParkingPlaces(final IParkingDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskGetParking asyncTaskGetParking = new AsyncTaskGetParking(listener);
        asyncTaskGetParking.execute();
        Log.d(TAG, "[retrievePlaces()] - end");
    }
}
