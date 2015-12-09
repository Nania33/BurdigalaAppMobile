package com.enseirb.gl.burdigalaapp.converter.listener;

import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.model.data.Garden;

import java.util.List;

/**
 * Created by Nania on 17/11/2015.
 */
public interface ICycleParkConverterListener {
    void onSuccess(final CycleParkContainer cyclePark);
    void onError(String message);
}
