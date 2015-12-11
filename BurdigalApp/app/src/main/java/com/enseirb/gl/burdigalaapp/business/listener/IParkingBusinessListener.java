package com.enseirb.gl.burdigalaapp.business.listener;

import com.enseirb.gl.burdigalaapp.model.data.Parking;

import java.util.List;

public interface IParkingBusinessListener {
    void onSuccess(final List<Parking> parkings);
    void onError(String message);
}
