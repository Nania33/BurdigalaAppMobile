package com.enseirb.gl.burdigalaapp.dao.asynctask.file;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;
import com.enseirb.gl.burdigalaapp.file.FileManager;
import com.enseirb.gl.burdigalaapp.dao.parser.KmlParkingParser;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskFileParking extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_FILE_PARKING";

    private IParkingDAOListener listener;
    private Context context;

    public AsyncTaskFileParking(Context context, final IParkingDAOListener listener){
        this.listener = listener;
        this.context = context;
    }
    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get parkings");
        List<ParkingDTO> parkingDTO = new ArrayList<>();

        try {
            parkingDTO.addAll(startParkingFromFileTask(params[0]));
            listener.onSuccess(parkingDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get parkings");
        return null;
    }

    private List<ParkingDTO> startParkingFromFileTask(String filename){
        FileManager fileManager = new FileManager(context);
        Log.d(TAG, "[startGetWeatherTask] start get parkings");
        String dataToParse = fileManager.readFromFile(filename);
        List<ParkingDTO> dtoList = new KmlParkingParser().parse(dataToParse);
        Log.d(TAG, "[startGetWeatherTask] end get parkings");
        return dtoList;
    }
}
