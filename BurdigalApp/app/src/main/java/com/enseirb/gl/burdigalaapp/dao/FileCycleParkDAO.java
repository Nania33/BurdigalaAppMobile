package com.enseirb.gl.burdigalaapp.dao;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.asynctask.AsyncTaskGetCycleParkFromFile;
import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;

/**
 * Created by Alex on 12/6/2015.
 */
public class FileCycleParkDAO implements ICycleParkDAO {
    public static final String TAG = "FileCycleParkDAO";

    @Override
    public void retrieveCycleParkPlaces(ICycleParkDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskGetCycleParkFromFile asyncTaskGetCyclePark = new AsyncTaskGetCycleParkFromFile(listener);
        asyncTaskGetCyclePark.execute();
        Log.d(TAG, "[retrievePlaces()] - end");
    }
}
