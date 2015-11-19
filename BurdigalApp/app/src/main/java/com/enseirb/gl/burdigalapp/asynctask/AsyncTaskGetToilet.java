package com.enseirb.gl.burdigalapp.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalapp.dto.ToiletDTO;

public class AsyncTaskGetToilet extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_GET_WEATHER";

    private final AsyncTaskGetWeatherListener listener;
    private ToiletDTO toiletDTO;
    private boolean canStart;

    public interface AsyncTaskGetWeatherListener {
        boolean canStartTask();

        void showProgressBar();

        void onDataReceived(ToiletDTO toiletDTO);

        void dismissProgressBar();
    }

    public AsyncTaskGetToilet(AsyncTaskGetWeatherListener listener) {
        this.listener = listener;
        this.toiletDTO = null;
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
            toiletDTO = startGetToiletTask(params[0], params[1]);
        } else {
            Log.d(TAG, "(doInBackground) - task already started");
        }
        return null;
    }

    protected void onPostExecute(Void v) {
        if (canStart) {
            listener.onDataReceived(toiletDTO);
            listener.dismissProgressBar();
        }
    }

    private ToiletDTO startGetToiletTask(String city, String format) {
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
