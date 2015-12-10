package com.enseirb.gl.burdigalaapp.retriever;

import android.content.Context;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.asynctask.file.AsyncTaskFileCyclePark;
import com.enseirb.gl.burdigalaapp.asynctask.file.AsyncTaskFileGarden;
import com.enseirb.gl.burdigalaapp.asynctask.file.AsyncTaskFileParking;
import com.enseirb.gl.burdigalaapp.asynctask.file.AsyncTaskFileToilet;
import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;

/**
 * Created by rchabot on 07/12/15.
 */
public class FileRetriever implements OpenDataRetriever {
    private static final String TAG = "FileRetriever";
    
    private Context context;
    private Service service;
    
    public FileRetriever(Context context, Service service) {
        this.context = context;
        this.service = service;
    }


    @Override
    public void retrieveToiletPlaces(IToiletDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskFileToilet asyncTaskFileToilet = new AsyncTaskFileToilet(context, listener);
        asyncTaskFileToilet.execute(service.getFilename());
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrieveGardenPlaces(IGardenDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskFileGarden asyncTaskFileGarden = new AsyncTaskFileGarden(context, listener);
        asyncTaskFileGarden.execute(service.getFilename());
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrieveCycleParkPlaces(ICycleParkDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskFileCyclePark asyncTaskFileCyclePark = new AsyncTaskFileCyclePark(context, listener);
        asyncTaskFileCyclePark.execute(service.getFilename());
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public void retrieveParkingPlaces(IParkingDAOListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        AsyncTaskFileParking asyncTaskFileParking = new AsyncTaskFileParking(context, listener);
        asyncTaskFileParking.execute(service.getFilename());
        Log.d(TAG, "[retrievePlaces()] - end");
    }
}
