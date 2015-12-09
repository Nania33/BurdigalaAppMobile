package com.enseirb.gl.burdigalaapp.converter.listener;

import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Parking;

import java.util.List;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IParkingConverterListener {
    void onSuccess(final ParkingContainer parkings);
    void onError(String message);
}
