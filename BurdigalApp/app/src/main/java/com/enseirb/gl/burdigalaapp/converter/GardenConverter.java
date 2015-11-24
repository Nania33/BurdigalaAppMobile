package com.enseirb.gl.burdigalaapp.converter;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.converter.listener.IGardenConverterListener;
import com.enseirb.gl.burdigalaapp.dao.IGardenDAO;
import com.enseirb.gl.burdigalaapp.dao.OpenDataGardenDAO;
import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.model.Garden;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rchabot on 23/11/15.
 */
public class GardenConverter implements IGardenConverter {
    public static final String TAG = "GardenConverter";

    private IGardenDAO gardenDAO;

    public GardenConverter() {
        this.gardenDAO = new OpenDataGardenDAO();
    }

    @Override
    public void retrieveGardenPlaces(final IGardenConverterListener listener) {
        Log.d(TAG, "[retrieveGardenPlaces()] - start");
        gardenDAO.retrieveGardenPlaces(new IGardenDAOListener() {
            @Override
            public void onSuccess(List<GardenDTO> gardenDTO) {
                Log.d(TAG, "[retrieveGardenPlaces()] - onSuccess - start");
                for (GardenDTO dto : gardenDTO)
                    Log.d(TAG, dto.toString());
                listener.onSuccess(convertToList(gardenDTO));
                Log.d(TAG, "[retrieveGardenPlaces()] - onSuccess - end");
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
        Log.d(TAG, "[retrieveGardenPlaces()] - end");
    }

    @Override
    public Garden convert(GardenDTO gardenDTO) {
        return new Garden(gardenDTO.getName(), gardenDTO.getCoords());
    }

    public List<Garden> convertToList(List<GardenDTO> dtoList) {
        List<Garden> convertedList = new ArrayList<>();
        for (GardenDTO dto : dtoList){
            convertedList.add(convert(dto));
        }
        return convertedList;
    }
}
