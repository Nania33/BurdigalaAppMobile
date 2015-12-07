package com.enseirb.gl.burdigalaapp.asynctask;

/**
 * Created by rchabot on 17/11/15.
 */
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;
import com.enseirb.gl.burdigalaapp.dto.ToiletDTO;
import com.enseirb.gl.burdigalaapp.parser.KmlToiletParser;
import com.enseirb.gl.burdigalaapp.web.http.request.HttpGetServiceRequest;
import com.enseirb.gl.burdigalaapp.web.http.request.TypeOfService;
import com.enseirb.gl.burdigalaapp.web.http.response.WebResponse;

import java.util.ArrayList;
import java.util.List;

public class AsyncTaskGetToilet extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_GET_TOILET";

    private IToiletDAOListener listener;

    public AsyncTaskGetToilet(final IToiletDAOListener listener){
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get toilets");
        List<ToiletDTO> toiletDTO = new ArrayList<>();

        try {
            toiletDTO.addAll(startGetToiletTask());
            listener.onSuccess(toiletDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get toilets");
        return null;
    }

    private List<ToiletDTO> startGetToiletTask(){
        Log.d(TAG, "[startGetToiletTask] start");
        HttpGetServiceRequest request = new HttpGetServiceRequest(TypeOfService.SIGSANITAIRE);
        WebResponse response = request.executeRequest();
        List<ToiletDTO> dtoList = KmlToiletParser.parse(response.getData());
        Log.d(TAG, "[startGetToiletTask] end");
        return dtoList;
    }

}
