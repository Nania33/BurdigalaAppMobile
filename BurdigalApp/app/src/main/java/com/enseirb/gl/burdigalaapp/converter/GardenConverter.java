package com.enseirb.gl.burdigalaapp.converter;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.converter.listener.IGardenConverterListener;
import com.enseirb.gl.burdigalaapp.dao.IGardenDAO;
import com.enseirb.gl.burdigalaapp.dao.OpenDataGardenDAO;
import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.model.data.Garden;

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
        Log.d(TAG, "[retrievePlaces()] - start");
        gardenDAO.retrieveGardenPlaces(new IGardenDAOListener() {
            @Override
            public void onSuccess(List<GardenDTO> gardenDTO) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                listener.onSuccess(convertToList(gardenDTO));
                Log.d(TAG, "[retrievePlaces()] - onSuccess - end");
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
        Log.d(TAG, "[retrievePlaces()] - end");
    }

    @Override
    public Garden convert(GardenDTO gardenDTO) {
        return new Garden(gardenDTO.getName(), gardenDTO.getParcType(), gardenDTO.getUse(), gardenDTO.getGestionType(),
                gardenDTO.getLabel(), gardenDTO.getPoint().toLatLng());
    }

    // return container when they are implemented
    public List<Garden> convertToList(List<GardenDTO> dtoList) {
        List<Garden> convertedList = new ArrayList<>();
        for (GardenDTO dto : dtoList){
            convertedList.add(convert(dto));
        }
        return convertedList;
    }
}
