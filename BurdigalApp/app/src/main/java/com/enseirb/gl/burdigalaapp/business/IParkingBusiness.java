package com.enseirb.gl.burdigalaapp.business;

import com.enseirb.gl.burdigalaapp.business.listener.IParkingBusinessListener;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IParkingBusiness {
    void retrieveParkingPlaces(final IParkingBusinessListener listener);
}
