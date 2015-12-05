package com.enseirb.gl.burdigalaapp.business;

import com.enseirb.gl.burdigalaapp.business.listener.ICycleParkBusinessListener;

/**
 * Created by Nania on 17/11/2015.
 */
public interface ICycleParkBusiness {
    void retrieveCycleParkPlaces(final ICycleParkBusinessListener listener);
}
