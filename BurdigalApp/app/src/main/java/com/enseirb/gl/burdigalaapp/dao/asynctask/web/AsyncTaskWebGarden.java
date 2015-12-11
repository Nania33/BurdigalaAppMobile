package com.enseirb.gl.burdigalaapp.dao.asynctask.web;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.file.FileManager;
import com.enseirb.gl.burdigalaapp.dao.parser.KmlGardenParser;
import com.enseirb.gl.burdigalaapp.web.http.request.HttpGetServiceRequest;
import com.enseirb.gl.burdigalaapp.web.http.request.TypeOfService;
import com.enseirb.gl.burdigalaapp.web.http.response.WebResponse;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskWebGarden extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_GET_GARDEN";

    private IGardenDAOListener listener;
    private Context context;

    public AsyncTaskWebGarden(Context context, final IGardenDAOListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get gardens");
        List<GardenDTO> gardenDTO = new ArrayList<>();

        try {
            gardenDTO.addAll(startGetGardenTask(params[0]));
            listener.onSuccess(gardenDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get gardens");
        return null;
    }

    private List<GardenDTO> startGetGardenTask(String filename){
        Log.d(TAG, "[startGetWeatherTask] start get gardens");
        HttpGetServiceRequest request = new HttpGetServiceRequest(TypeOfService.PARCJARDIN);
        WebResponse response = request.executeRequest();
        FileManager fileManager = new FileManager(context);
        fileManager.writeDataToFile(response.getData(), filename);
        List<GardenDTO> dtoList = new KmlGardenParser().parse(response.getData());
        Log.d(TAG, "[startGetWeatherTask] end get gardens");
        return dtoList;
    }

}
