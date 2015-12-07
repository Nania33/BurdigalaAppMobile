package com.enseirb.gl.burdigalaapp.converter;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.converter.listener.ICycleParkConverterListener;
import com.enseirb.gl.burdigalaapp.dao.FileCycleParkDAO;
import com.enseirb.gl.burdigalaapp.dao.ICycleParkDAO;
import com.enseirb.gl.burdigalaapp.dao.OpenDataCycleParkDAO;
import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rchabot on 05/12/15.
 */
public class CycleParkConverter implements ICycleParkConverter{
    public static final String TAG = "CycleParkConverter";

    private ICycleParkDAO cycleParkDAO;
    private ICycleParkDAO fileCycleParkDAO;

    public CycleParkConverter() {
        this.cycleParkDAO = new OpenDataCycleParkDAO();
        this.fileCycleParkDAO = new FileCycleParkDAO();
    }

    @Override
    public void retrieveCycleParkPlaces(final ICycleParkConverterListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        cycleParkDAO.retrieveCycleParkPlaces(new ICycleParkDAOListener() {
            @Override
            public void onSuccess(List<CycleParkDTO> cycleParkDTO) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                listener.onSuccess(convertToList(cycleParkDTO));
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
    public void retrieveCycleParkPlacesFromFile(final ICycleParkConverterListener listener) {
        Log.d(TAG, "[retrievePlacesFromFile()] - start");
        fileCycleParkDAO.retrieveCycleParkPlaces(new ICycleParkDAOListener() {
            @Override
            public void onSuccess(List<CycleParkDTO> cycleParkDTO) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                listener.onSuccess(convertToList(cycleParkDTO));
                Log.d(TAG, "[retrievePlaces()] - onSuccess - end");
            }

            @Override
            public void onError(String message) {
                listener.onError(message);
            }
        });
        Log.d(TAG, "[retrievePlacesFromFile()] - end");
    }

    @Override
    public CyclePark convert(CycleParkDTO cycleParkDTO) {
        return new CyclePark(cycleParkDTO.getFixationType(), cycleParkDTO.getParkingSpotNumber(), cycleParkDTO.getPoint().toLatLng());
    }

    public List<CyclePark> convertToList(List<CycleParkDTO> dtoList) {
        List<CyclePark> convertedList = new ArrayList<>();
        for (CycleParkDTO dto : dtoList){
            convertedList.add(convert(dto));
        }
        return convertedList;
    }
}
