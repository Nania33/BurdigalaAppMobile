package com.enseirb.gl.burdigalaapp.asynctask.file;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;
import com.enseirb.gl.burdigalaapp.file.FileIO;
import com.enseirb.gl.burdigalaapp.file.FileManager;
import com.enseirb.gl.burdigalaapp.parser.KmlParkingParser;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;
import com.enseirb.gl.burdigalaapp.presenter.service.ServiceFactory;
import com.enseirb.gl.burdigalaapp.web.http.request.TypeOfService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rchabot on 09/12/15.
 */
public class AsyncTaskFileParking extends AsyncTask<String, Void, Void> {
    private static final String TAG = "ASYNC_FILE_GARDEN";

    private IParkingDAOListener listener;
    private Context context;

    public AsyncTaskFileParking(Context context, final IParkingDAOListener listener){
        this.listener = listener;
        this.context = context;
    }
    @Override
    protected Void doInBackground(String... params) {
        Log.d(TAG, "[doInBackground()] - start get parkings");
        List<ParkingDTO> parkingDTO = new ArrayList<>();

        try {
            parkingDTO.addAll(startParkingFromFileTask(params[0]));
            listener.onSuccess(parkingDTO);
        } catch (Exception e){
            listener.onError(e.getMessage());
        }
        Log.d(TAG, "[doInBackground()] - end get parkings");
        return null;
    }

    private List<ParkingDTO> startParkingFromFileTask(String filename){
        Log.d(TAG, "[startGetWeatherTask] start get parkings");
        FileIO fileIO = new FileIO(context);
        String dataToParse = fileIO.readFromFile(filename);
        List<ParkingDTO> dtoList = KmlParkingParser.parse(dataToParse);
        Log.d(TAG, "[startGetWeatherTask] end get parkings");
        return dtoList;
    }
}
