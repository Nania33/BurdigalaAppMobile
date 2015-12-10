package com.enseirb.gl.burdigalaapp.asynctask.file;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.file.FileManager;
import com.enseirb.gl.burdigalaapp.parser.KmlGardenParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rchabot on 09/12/15.
 */
public class AsyncTaskFileGarden extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_FILE_GARDEN";

    private IGardenDAOListener listener;
    private Context context;

    public AsyncTaskFileGarden(Context context, final IGardenDAOListener listener){
        this.listener = listener;
        this.context = context;
    }
    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get gardens");
        List<GardenDTO> gardenDTO = new ArrayList<>();

        try {
            gardenDTO.addAll(startGardenFromFileTask(params[0]));
            listener.onSuccess(gardenDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get gardens");
        return null;
    }

    private List<GardenDTO> startGardenFromFileTask(String filename){
        FileManager fileManager = new FileManager(context);
        Log.d(TAG, "[startGetWeatherTask] start get gardens");
        String dataToParse = fileManager.readFromFile(filename);
        List<GardenDTO> dtoList = KmlGardenParser.parse(dataToParse);
        Log.d(TAG, "[startGetWeatherTask] end get gardens");
        return dtoList;
    }
}
