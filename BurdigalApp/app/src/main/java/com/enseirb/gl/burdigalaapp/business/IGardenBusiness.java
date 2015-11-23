package com.enseirb.gl.burdigalaapp.business;

import com.enseirb.gl.burdigalaapp.business.listener.IGardenBusinessListener;

/**
 * Created by rchabot on 17/11/15.
 */
public interface IGardenBusiness {
    void retrieveGardenPlaces(final IGardenBusinessListener listener);
}
