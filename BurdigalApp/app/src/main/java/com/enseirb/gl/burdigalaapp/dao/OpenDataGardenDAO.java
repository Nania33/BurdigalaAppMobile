package com.enseirb.gl.burdigalaapp.dao;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.asynctask.AsyncTaskGetGarden;
import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;

/**
 * Created by rchabot on 23/11/15.
 */
public class OpenDataGardenDAO implements IGardenDAO {
    public static final String TAG = "OpenDataGardenDAO";

    @Override
    public void retrieveGardenPlaces(final IGardenDAOListener listener) {
        Log.d(TAG, "[retrieveGardenPlaces()] - start");
        AsyncTaskGetGarden asyncTaskGetGarden = new AsyncTaskGetGarden(listener);
        asyncTaskGetGarden.execute();
        Log.d(TAG, "[retrieveGardenPlaces()] - end");
    }



}
