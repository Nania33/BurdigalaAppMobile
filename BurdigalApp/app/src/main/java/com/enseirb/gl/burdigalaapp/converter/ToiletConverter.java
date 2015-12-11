package com.enseirb.gl.burdigalaapp.converter;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.converter.listener.IToiletConverterListener;
import com.enseirb.gl.burdigalaapp.dao.IToiletDAO;
import com.enseirb.gl.burdigalaapp.dao.OpenDataToiletDAO;
import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;
import com.enseirb.gl.burdigalaapp.dto.ToiletDTO;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

public class ToiletConverter implements IToiletConverter {
    public static final String TAG = "ToiletConverter";

    private IToiletDAO toiletDAO;

    public ToiletConverter() {
        this.toiletDAO = new OpenDataToiletDAO();
    }

    @Override
    public void retrieveToiletPlaces(OpenDataRetriever retriever, final IToiletConverterListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        toiletDAO.retrieveToiletPlaces(retriever, new IToiletDAOListener() {
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
