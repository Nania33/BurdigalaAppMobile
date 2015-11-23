package com.enseirb.gl.burdigalaapp.asynctask;

/**
 * Created by rchabot on 17/11/15.
 */
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsyncTaskGetGarden extends AsyncTask<String, Void, List<GardenDTO>> {
    private static final String TAG = "ASYNC_GET_GARDEN";

    protected void onPreExecute() {

    }

    @Override
    protected List<GardenDTO> doInBackground(String... params) {
        return getGardenTask();
    }

    protected void onPostExecute(Void v) {

    }

    private List<GardenDTO> getGardenTask() {
        Log.d(TAG, "[startGetWeatherTask] start");
        List<GardenDTO> gardenDTOList = new ArrayList<>(Arrays.asList(
                new GardenDTO("Point1", new LatLng(-51, 159)),
                new GardenDTO("Point2", new LatLng(-60, 180)),
                new GardenDTO("Point3", new LatLng(-40, 140))
        ));
        return gardenDTOList;
    }
}
