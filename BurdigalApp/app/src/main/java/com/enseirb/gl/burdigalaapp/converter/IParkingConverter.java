package com.enseirb.gl.burdigalaapp.converter;

import com.enseirb.gl.burdigalaapp.converter.listener.IParkingConverterListener;
import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public interface IParkingConverter {
    void retrieveParkingPlaces(OpenDataRetriever retriever, final IParkingConverterListener listener);
    Parking convert(ParkingDTO parkingDTO);
}
