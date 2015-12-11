package com.enseirb.gl.burdigalaapp.dao.asynctask.file;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;
import com.enseirb.gl.burdigalaapp.dto.ToiletDTO;
import com.enseirb.gl.burdigalaapp.file.FileManager;
import com.enseirb.gl.burdigalaapp.dao.parser.KmlToiletParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rchabot on 09/12/15.
 */
public class AsyncTaskFileToilet extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_FILE_GARDEN";

    private IToiletDAOListener listener;
    private Context context;

    public AsyncTaskFileToilet(Context context, final IToiletDAOListener listener){
        this.listener = listener;
        this.context = context;
    }
    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get toilets");
        List<ToiletDTO> toiletDTO = new ArrayList<>();

        try {
            toiletDTO.addAll(startToiletFromFileTask(params[0]));
            listener.onSuccess(toiletDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get toilets");
        return null;
    }

    private List<ToiletDTO> startToiletFromFileTask(String filename){
        FileManager fileManager = new FileManager(context);
        Log.d(TAG, "[startGetWeatherTask] start get toilets");
        String dataToParse = fileManager.readFromFile(filename);
        List<ToiletDTO> dtoList = KmlToiletParser.parse(dataToParse);
        Log.d(TAG, "[startGetWeatherTask] end get toilets");
        return dtoList;
    }
}
