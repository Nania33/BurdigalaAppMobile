package com.enseirb.gl.burdigalaapp.business.listener;

import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Parking;

import java.util.List;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IParkingBusinessListener {
    void onSuccess(final List<Parking> parkings);
    void onError(String message);
}
