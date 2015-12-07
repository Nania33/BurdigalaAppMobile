package com.enseirb.gl.burdigalaapp.converter;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.converter.listener.ICycleParkConverterListener;
import com.enseirb.gl.burdigalaapp.dao.ICycleParkDAO;
import com.enseirb.gl.burdigalaapp.dao.OpenDataCycleParkDAO;
import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rchabot on 05/12/15.
 */
public class CycleParkConverter implements ICycleParkConverter{
    public static final String TAG = "CycleParkConverter";

    private ICycleParkDAO cycleParkDAO;

    public CycleParkConverter() {
        this.cycleParkDAO = new OpenDataCycleParkDAO();
    }

    @Override
    public void retrieveCycleParkPlaces(final ICycleParkConverterListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        cycleParkDAO.retrieveCycleParkPlaces(new ICycleParkDAOListener() {
            @Override
            public void onSuccess(List<CycleParkDTO> cycleParkDTO) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                listener.onSuccess(convertToContainer(cycleParkDTO));
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
    public CyclePark convert(CycleParkDTO cycleParkDTO) {
        return new CyclePark(cycleParkDTO.getFixationType(), cycleParkDTO.getParkingSpotNumber(), cycleParkDTO.getPoint().toLatLng());
    }

    public CycleParkContainer convertToContainer(List<CycleParkDTO> dtoList) {
        List<CyclePark> convertedList = new ArrayList<>();
        for (CycleParkDTO dto : dtoList){
            convertedList.add(convert(dto));
        }
        return new CycleParkContainer(convertedList);
    }
}
