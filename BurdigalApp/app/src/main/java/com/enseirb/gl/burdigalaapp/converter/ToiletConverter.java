package com.enseirb.gl.burdigalaapp.converter;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.converter.listener.IToiletConverterListener;
import com.enseirb.gl.burdigalaapp.dao.IToiletDAO;
import com.enseirb.gl.burdigalaapp.dao.OpenDataToiletDAO;
import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;
import com.enseirb.gl.burdigalaapp.dto.ToiletDTO;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rchabot on 03/12/15.
 */
public class ToiletConverter implements IToiletConverter {
    public static final String TAG = "ToiletConverter";

    private IToiletDAO gardenDAO;

    public ToiletConverter() {
        this.gardenDAO = new OpenDataToiletDAO();
    }

    @Override
    public void retrieveToiletPlaces(OpenDataRetriever retriever, final IToiletConverterListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        gardenDAO.retrieveToiletPlaces(retriever, new IToiletDAOListener() {
            @Override
            public void onSuccess(List<ToiletDTO> gardenDTO) {
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
    public Toilet convert(ToiletDTO toiletDTO) {
        return new Toilet(toiletDTO.getAddress(), toiletDTO.getNeighbourhood(), toiletDTO.getToiletType(), toiletDTO.getPoint().toLatLng());
    }

    public ToiletContainer convertToContainer(List<ToiletDTO> dtoList) {
        List<Toilet> convertedList = new ArrayList<>();
        for (ToiletDTO dto : dtoList){
            convertedList.add(convert(dto));
        }
        return new ToiletContainer(convertedList);
    }
}
