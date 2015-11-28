package com.enseirb.gl.burdigalaapp.asynctask;

/**
 * Created by rchabot on 17/11/15.
 */
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.parser.KmlGardenParser;
import com.enseirb.gl.burdigalaapp.web.http.request.HttpGetServiceRequest;
import com.enseirb.gl.burdigalaapp.web.http.request.TypeOfService;
import com.enseirb.gl.burdigalaapp.web.http.response.WebResponse;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AsyncTaskGetGarden extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_GET_GARDEN";

    private IGardenDAOListener listener;

    public AsyncTaskGetGarden(final IGardenDAOListener listener){
        this.listener = listener;
    }

    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start");
        List<GardenDTO> gardenDTO = new ArrayList<>();

        try {
            gardenDTO.addAll(startGetGardenTask());
            if (gardenDTO.isEmpty())
                Log.d(TAG, "Résultat vide");
            else {
                Log.d(TAG, "Has data");
            }
            listener.onSuccess(gardenDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end");
        return null;
    }

    protected void onPostExecute(Void v) {

    }

    private List<GardenDTO> startGetGardenTask(){
        Log.d(TAG, "[startGetWeatherTask] start");
        HttpGetServiceRequest request = new HttpGetServiceRequest(TypeOfService.PARCJARDIN);
        WebResponse response = request.executeRequest();
        List<GardenDTO> dtoList = KmlGardenParser.parse(response.getData());
        Log.d(TAG, "[startGetWeatherTask] end");
        return dtoList;
    }

    private List<GardenDTO> getGardenTask() {
        /*// TODO Faire une HTTPWebRequest + parsing
        Log.d(TAG, "[startGetWeatherTask] start");
        List<GardenDTO> gardenDTOList = new ArrayList<>(Arrays.asList(
                new GardenDTO("Point1", new LatLng(-51, 159)),
                new GardenDTO("Point2", new LatLng(-60, 180)),
                new GardenDTO("Point3", new LatLng(-40, 140))
        ));
        return gardenDTOList;*/
        return  null;
    }

}
