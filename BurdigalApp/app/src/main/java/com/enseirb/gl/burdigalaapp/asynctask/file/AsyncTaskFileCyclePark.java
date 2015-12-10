package com.enseirb.gl.burdigalaapp.asynctask.file;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.file.FileManager;
import com.enseirb.gl.burdigalaapp.parser.KmlCycleParkParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rchabot on 09/12/15.
 */
public class AsyncTaskFileCyclePark extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_FILE_GARDEN";

    private ICycleParkDAOListener listener;
    private Context context;

    public AsyncTaskFileCyclePark(Context context, final ICycleParkDAOListener listener){
        this.listener = listener;
        this.context = context;
    }
    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get cycleParks");
        List<CycleParkDTO> cycleParkDTO = new ArrayList<>();

        try {
            cycleParkDTO.addAll(startCycleParkFromFileTask(params[0]));
            listener.onSuccess(cycleParkDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get cycleParks");
        return null;
    }

    private List<CycleParkDTO> startCycleParkFromFileTask(String filename){
        FileManager fileManager = new FileManager(context);
        Log.d(TAG, "[startGetWeatherTask] start get cycleParks");
        String dataToParse = fileManager.readFromFile(filename);
        List<CycleParkDTO> dtoList = KmlCycleParkParser.parse(dataToParse);
        Log.d(TAG, "[startGetWeatherTask] end get cycleParks");
        return dtoList;
    }
}
