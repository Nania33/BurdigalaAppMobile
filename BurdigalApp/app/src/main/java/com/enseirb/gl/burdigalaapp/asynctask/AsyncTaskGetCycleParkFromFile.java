package com.enseirb.gl.burdigalaapp.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.parser.FileParser;
import com.enseirb.gl.burdigalaapp.parser.KmlCycleParkParser;
import com.enseirb.gl.burdigalaapp.web.http.request.HttpGetServiceRequest;
import com.enseirb.gl.burdigalaapp.web.http.request.TypeOfService;
import com.enseirb.gl.burdigalaapp.web.http.response.WebResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 12/6/2015.
 */
public class AsyncTaskGetCycleParkFromFile extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_GET_CPARK_FILE";
    private static final String FILENAME = "";

    private ICycleParkDAOListener listener;


    public AsyncTaskGetCycleParkFromFile(final ICycleParkDAOListener listener){
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
        // Open file get the whole string and parse it with the KML parser
        FileParser fileParser = new FileParser();
        List<CycleParkDTO> dtoList = KmlCycleParkParser.parse(fileParser.retrieveDataFromFile(FILENAME));
        Log.d(TAG, "[startGetWeatherTask] end get cycleParks");
        return dtoList;
    }
}
