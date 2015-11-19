package com.enseirb.gl.burdigalapp.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalapp.dto.ParkingDTO;

public class AsyncTaskGetParking extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_GET_WEATHER";

    private final AsyncTaskGetWeatherListener listener;
    private ParkingDTO parkingDTO;
    private boolean canStart;

    public interface AsyncTaskGetWeatherListener {
        boolean canStartTask();

        void showProgressBar();

        void onDataReceived(ParkingDTO parkingDTO);

        void dismissProgressBar();
    }

    public AsyncTaskGetParking(AsyncTaskGetWeatherListener listener) {
        this.listener = listener;
        this.parkingDTO = null;
        this.canStart = false;
    }

    protected void onPreExecute() {
        canStart = listener.canStartTask();
        if (canStart)
            listener.showProgressBar();
    }

    @Override
    protected Void doInBackground(String... params) {
        if (canStart) {
            Log.d(TAG, "(doInBackground) - start task");
            parkingDTO = startGetParkingTask(params[0], params[1]);
        } else {
            Log.d(TAG, "(doInBackground) - task already started");
        }
        return null;
    }

    protected void onPostExecute(Void v) {
        if (canStart) {
            listener.onDataReceived(parkingDTO);
            listener.dismissProgressBar();
        }
    }

    private ParkingDTO startGetParkingTask(String city, String format) {
        Log.d(TAG, "[startGetWeatherTask] start");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //IGardenDAO dao = IGardenDAO.getFromWeb(city, format);
        /*if (dao != null) {
            Log.d(TAG, "[startGetWeatherTask] end successfully");
            return null; //GardenDTO.createFromDAO(dao);
        } else {
            Log.d(TAG, "[startGetWeatherTask] - ERROR - end with no data received");
            return null;
        }*/
        return null;
    }
}
