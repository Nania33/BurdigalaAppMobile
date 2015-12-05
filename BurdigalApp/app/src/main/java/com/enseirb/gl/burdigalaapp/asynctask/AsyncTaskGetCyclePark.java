package com.enseirb.gl.burdigalaapp.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.parser.KmlCycleParkParser;
import com.enseirb.gl.burdigalaapp.web.http.request.HttpGetServiceRequest;
import com.enseirb.gl.burdigalaapp.web.http.request.TypeOfService;
import com.enseirb.gl.burdigalaapp.web.http.response.WebResponse;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskGetCyclePark extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_GET_CYCLEPARK";

    private ICycleParkDAOListener listener;

    public AsyncTaskGetCyclePark(final ICycleParkDAOListener listener){
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get cycleParks");
        List<CycleParkDTO> cycleParkDTO = new ArrayList<>();

        try {
            cycleParkDTO.addAll(startGetCycleParkTask());
            listener.onSuccess(cycleParkDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get cycleParks");
        return null;
    }

    private List<CycleParkDTO> startGetCycleParkTask(){
        Log.d(TAG, "[startGetWeatherTask] start get cycleParks");
        HttpGetServiceRequest request = new HttpGetServiceRequest(TypeOfService.SIGSTAVELO);
        WebResponse response = request.executeRequest();
        List<CycleParkDTO> dtoList = KmlCycleParkParser.parse(response.getData());
        Log.d(TAG, "[startGetWeatherTask] end get cycleParks");
        return dtoList;
    }
}
