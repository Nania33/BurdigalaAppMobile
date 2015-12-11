package com.enseirb.gl.burdigalaapp.dao.asynctask.web;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;
import com.enseirb.gl.burdigalaapp.file.FileManager;
import com.enseirb.gl.burdigalaapp.dao.parser.KmlParkingParser;
import com.enseirb.gl.burdigalaapp.web.http.request.HttpGetServiceRequest;
import com.enseirb.gl.burdigalaapp.web.http.request.TypeOfService;
import com.enseirb.gl.burdigalaapp.web.http.response.WebResponse;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskWebParking extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_GET_PARKING";

    private IParkingDAOListener listener;
    private Context context;

    public AsyncTaskWebParking(Context context, final IParkingDAOListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get parkings");
        List<ParkingDTO> parkingDTO = new ArrayList<>();
        try {
            parkingDTO.addAll(startGetParkingTask(params[0]));
            listener.onSuccess(parkingDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get parkings");
        return null;
    }

    private List<ParkingDTO> startGetParkingTask(String filename){
        Log.d(TAG, "[startGetWeatherTask] start get parkings");
        HttpGetServiceRequest request = new HttpGetServiceRequest(TypeOfService.SIGPARKPUB);
        WebResponse response = request.executeRequest();
        FileManager fileManager = new FileManager(context);
        fileManager.writeDataToFile(response.getData(), filename);
        List<ParkingDTO> dtoList = new KmlParkingParser().parse(response.getData());
        Log.d(TAG, "[startGetWeatherTask] end get parkings");
        return dtoList;
    }
}
