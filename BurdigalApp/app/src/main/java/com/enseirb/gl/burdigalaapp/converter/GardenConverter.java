package com.enseirb.gl.burdigalaapp.converter;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.converter.listener.IGardenConverterListener;
import com.enseirb.gl.burdigalaapp.dao.IGardenDAO;
import com.enseirb.gl.burdigalaapp.dao.OpenDataGardenDAO;
import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

public class GardenConverter implements IGardenConverter {
    public static final String TAG = "GardenConverter";

    private IGardenDAO gardenDAO;

    public GardenConverter() {
        this.gardenDAO = new OpenDataGardenDAO();
    }

    @Override
    public void retrieveGardenPlaces(OpenDataRetriever retriever, final IGardenConverterListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        gardenDAO.retrieveGardenPlaces(retriever, new IGardenDAOListener() {
            @Override
            public void onSuccess(List<GardenDTO> gardenDTO) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                listener.onSuccess(convertToContainer(gardenDTO));
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

    public GardenContainer convertToContainer(List<GardenDTO> dtoList) {
        List<Garden> convertedList = new ArrayList<>();
        for (GardenDTO dto : dtoList){
            convertedList.add(convert(dto));
        }
        return new GardenContainer(convertedList);
    }
}
