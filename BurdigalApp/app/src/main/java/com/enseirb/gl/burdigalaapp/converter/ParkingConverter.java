package com.enseirb.gl.burdigalaapp.converter;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.converter.listener.IParkingConverterListener;
import com.enseirb.gl.burdigalaapp.dao.IParkingDAO;
import com.enseirb.gl.burdigalaapp.dao.OpenDataParkingDAO;
import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;
import com.enseirb.gl.burdigalaapp.model.data.Parking;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rchabot on 05/12/15.
 */
public class ParkingConverter implements IParkingConverter {
    public static final String TAG = "ParkingConverter";

    private IParkingDAO parkingDAO;

    public ParkingConverter() {
        this.parkingDAO = new OpenDataParkingDAO();
    }

    @Override
    public void retrieveParkingPlaces(final IParkingConverterListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        parkingDAO.retrieveParkingPlaces(new IParkingDAOListener() {
            @Override
            public void onSuccess(List<ParkingDTO> parkingDTO) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                listener.onSuccess(convertToList(parkingDTO));
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
    public Parking convert(ParkingDTO parkingDTO) {
        return new Parking(parkingDTO.getName(), parkingDTO.getParkingSpotNumber(), parkingDTO.getParkingType(),
                parkingDTO.getPoint().toLatLng());
    }

    public List<Parking> convertToList(List<ParkingDTO> dtoList) {
        List<Parking> convertedList = new ArrayList<>();
        for (ParkingDTO dto : dtoList){
            convertedList.add(convert(dto));
        }
        return convertedList;
    }
}
