package com.enseirb.gl.burdigalaapp.dao;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.asynctask.AsyncTaskGetCyclePark;
import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;

/**
 * Created by rchabot on 05/12/15.
 */
public class OpenDataCycleParkDAO implements ICycleParkDAO {
    public static final String TAG = "OpenDataCycleParkDAO";

    @Override
    public void retrieveCycleParkPlaces(final ICycleParkDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskGetCyclePark asyncTaskGetCyclePark = new AsyncTaskGetCyclePark(listener);
        asyncTaskGetCyclePark.execute();
        Log.d(TAG, "[retrievePlaces()] - end");
    }
}
