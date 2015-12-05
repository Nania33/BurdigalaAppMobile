package com.enseirb.gl.burdigalaapp.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;
import com.enseirb.gl.burdigalaapp.parser.KmlParkingParser;
import com.enseirb.gl.burdigalaapp.web.http.request.HttpGetServiceRequest;
import com.enseirb.gl.burdigalaapp.web.http.request.TypeOfService;
import com.enseirb.gl.burdigalaapp.web.http.response.WebResponse;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskGetParking extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_GET_PARKING";

    private IParkingDAOListener listener;

    public AsyncTaskGetParking(final IParkingDAOListener listener){
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get parkings");
        List<ParkingDTO> parkingDTO = new ArrayList<>();
        try {
            parkingDTO.addAll(startGetParkingTask());
            listener.onSuccess(parkingDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get parkings");
        return null;
    }

    private List<ParkingDTO> startGetParkingTask(){
        Log.d(TAG, "[startGetWeatherTask] start get parkings");
        HttpGetServiceRequest request = new HttpGetServiceRequest(TypeOfService.SIGPARKPUB);
        WebResponse response = request.executeRequest();
        List<ParkingDTO> dtoList = KmlParkingParser.parse(response.getData());
        Log.d(TAG, "[startGetWeatherTask] end get parkings");
        return dtoList;
    }
}
