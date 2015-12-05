package com.enseirb.gl.burdigalaapp.converter;

import com.enseirb.gl.burdigalaapp.converter.listener.IParkingConverterListener;
import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;
import com.enseirb.gl.burdigalaapp.model.data.Parking;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IParkingConverter {
    void retrieveParkingPlaces(final IParkingConverterListener listener);
    Parking convert(ParkingDTO gardenDTO);
}
