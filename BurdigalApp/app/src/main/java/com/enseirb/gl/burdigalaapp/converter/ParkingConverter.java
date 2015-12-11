package com.enseirb.gl.burdigalaapp.converter;

import android.util.Log;

import com.enseirb.gl.burdigalaapp.converter.listener.IParkingConverterListener;
import com.enseirb.gl.burdigalaapp.dao.IParkingDAO;
import com.enseirb.gl.burdigalaapp.dao.OpenDataParkingDAO;
import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

public class ParkingConverter implements IParkingConverter {
    public static final String TAG = "ParkingConverter";

    private IParkingDAO parkingDAO;

    public ParkingConverter() {
        this.parkingDAO = new OpenDataParkingDAO();
    }

    @Override
    public void retrieveParkingPlaces(OpenDataRetriever retriever, final IParkingConverterListener listener) {
        Log.d(TAG, "[retrievePlaces()] - start");
        parkingDAO.retrieveParkingPlaces(retriever, new IParkingDAOListener() {
            @Override
            public void onSuccess(List<ParkingDTO> parkingDTO) {
                Log.d(TAG, "[retrievePlaces()] - onSuccess - start");
                listener.onSuccess(convertToContainer(parkingDTO));
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

    public ParkingContainer convertToContainer(List<ParkingDTO> dtoList) {
        List<Parking> convertedList = new ArrayList<>();
        for (ParkingDTO dto : dtoList){
            convertedList.add(convert(dto));
        }
        return new ParkingContainer(convertedList);
    }
}
