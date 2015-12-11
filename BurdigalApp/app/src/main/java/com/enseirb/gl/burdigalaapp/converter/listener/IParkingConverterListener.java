package com.enseirb.gl.burdigalaapp.converter.listener;

import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;

public interface IParkingConverterListener {
    void onSuccess(final ParkingContainer parking);
    void onError(String message);
}
