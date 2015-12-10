package com.enseirb.gl.burdigalaapp.asynctask.web;

/**
 * Created by rchabot on 17/11/15.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;
import com.enseirb.gl.burdigalaapp.dto.ToiletDTO;
import com.enseirb.gl.burdigalaapp.file.FileManager;
import com.enseirb.gl.burdigalaapp.parser.KmlToiletParser;
import com.enseirb.gl.burdigalaapp.web.http.request.HttpGetServiceRequest;
import com.enseirb.gl.burdigalaapp.web.http.request.TypeOfService;
import com.enseirb.gl.burdigalaapp.web.http.response.WebResponse;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskWebToilet extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_GET_TOILET";

    private IToiletDAOListener listener;
    private Context context;

    public AsyncTaskWebToilet(Context context, final IToiletDAOListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get toilets");
        List<ToiletDTO> toiletDTO = new ArrayList<>();

        try {
            toiletDTO.addAll(startGetToiletTask(params[0]));
            listener.onSuccess(toiletDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get toilets");
        return null;
    }

    private List<ToiletDTO> startGetToiletTask(String filename){
        Log.d(TAG, "[startGetToiletTask] start");
        HttpGetServiceRequest request = new HttpGetServiceRequest(TypeOfService.SIGSANITAIRE);
        WebResponse response = request.executeRequest();
        FileManager fileManager = new FileManager(context);
        fileManager.writeDataToFile(response.getData(), filename);
        List<ToiletDTO> dtoList = KmlToiletParser.parse(response.getData());
        Log.d(TAG, "[startGetToiletTask] end");
        return dtoList;
    }

}
