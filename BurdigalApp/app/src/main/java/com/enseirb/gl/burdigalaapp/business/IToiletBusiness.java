package com.enseirb.gl.burdigalaapp.business;

import com.enseirb.gl.burdigalaapp.business.listener.IGardenBusinessListener;
import com.enseirb.gl.burdigalaapp.business.listener.IToiletBusinessListener;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IToiletBusiness {
    void retrieveToiletPlaces(final IToiletBusinessListener listener);
}
