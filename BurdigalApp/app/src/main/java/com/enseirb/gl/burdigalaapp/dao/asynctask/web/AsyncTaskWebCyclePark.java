package com.enseirb.gl.burdigalaapp.dao.asynctask.web;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.file.FileManager;
import com.enseirb.gl.burdigalaapp.dao.parser.KmlCycleParkParser;
import com.enseirb.gl.burdigalaapp.web.http.request.HttpGetServiceRequest;
import com.enseirb.gl.burdigalaapp.web.http.request.TypeOfService;
import com.enseirb.gl.burdigalaapp.web.http.response.WebResponse;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskWebCyclePark extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_GET_CYCLEPARK";

    private ICycleParkDAOListener listener;
    private Context context;

    public AsyncTaskWebCyclePark(Context context, final ICycleParkDAOListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get cycleParks");
        List<CycleParkDTO> cycleParkDTO = new ArrayList<>();

        try {
            cycleParkDTO.addAll(startGetCycleParkTask(params[0]));
            listener.onSuccess(cycleParkDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get cycleParks");
        return null;
    }

    private List<CycleParkDTO> startGetCycleParkTask(String filename){
        Log.d(TAG, "[startGetWeatherTask] start get cycleParks");
        HttpGetServiceRequest request = new HttpGetServiceRequest(TypeOfService.SIGSTAVELO);
        WebResponse response = request.executeRequest();
        FileManager fileManager = new FileManager(context);
        fileManager.writeDataToFile(response.getData(), filename);
        List<CycleParkDTO> dtoList = KmlCycleParkParser.parse(response.getData());
        Log.d(TAG, "[startGetWeatherTask] end get cycleParks");
        return dtoList;
    }
}
