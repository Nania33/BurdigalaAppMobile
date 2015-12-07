package com.enseirb.gl.burdigalaapp.dao;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.asynctask.AsyncTaskGetToilet;
import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;

/**
 * Created by rchabot on 03/12/15.
 */
public class OpenDataToiletDAO implements IToiletDAO {
    public static final String TAG = "OpenDataToiletDAO";

    @Override
    public void retrieveToiletPlaces(final IToiletDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskGetToilet asyncTaskGetToilet = new AsyncTaskGetToilet(listener);
        asyncTaskGetToilet.execute();
        Log.d(TAG, "[retrievePlaces()] - end");
    }

}
