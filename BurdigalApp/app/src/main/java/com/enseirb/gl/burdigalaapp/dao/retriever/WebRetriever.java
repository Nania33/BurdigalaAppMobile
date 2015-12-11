package com.enseirb.gl.burdigalaapp.dao.retriever;

import android.content.Context;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.asynctask.web.AsyncTaskWebCyclePark;
import com.enseirb.gl.burdigalaapp.dao.asynctask.web.AsyncTaskWebGarden;
import com.enseirb.gl.burdigalaapp.dao.asynctask.web.AsyncTaskWebParking;
import com.enseirb.gl.burdigalaapp.dao.asynctask.web.AsyncTaskWebToilet;
import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;
import com.enseirb.gl.burdigalaapp.model.service.Service;

/**
 * Created by rchabot on 07/12/15.
 */
public class WebRetriever implements OpenDataRetriever {
    private static final String TAG = "WebRetriever";

    private Context context;
    private Service service;

    public WebRetriever(Context context, Service service) {
        this.context = context;
        this.service = service;
    }

    @Override
    public void retrieveToiletPlaces(IToiletDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskWebToilet asyncTaskWebToilet = new AsyncTaskWebToilet(context, listener);
        asyncTaskWebToilet.execute(service.getFilename());
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrieveGardenPlaces(IGardenDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskWebGarden asyncTaskWebGarden = new AsyncTaskWebGarden(context, listener);
        asyncTaskWebGarden.execute(service.getFilename());
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrieveCycleParkPlaces(ICycleParkDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskWebCyclePark asyncTaskWebCyclePark = new AsyncTaskWebCyclePark(context, listener);
        asyncTaskWebCyclePark.execute(service.getFilename());
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrieveParkingPlaces(IParkingDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskWebParking asyncTaskWebParking = new AsyncTaskWebParking(context, listener);
        asyncTaskWebParking.execute(service.getFilename());
        Log.d(TAG, "[retrievePlaces()] - end");
    }
}
