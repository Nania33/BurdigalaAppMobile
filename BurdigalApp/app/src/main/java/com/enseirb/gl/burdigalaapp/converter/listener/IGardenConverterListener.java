package com.enseirb.gl.burdigalaapp.converter.listener;

import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;


/**
 * Created by rchabot on 17/11/15.
 */
public interface IGardenConverterListener {
    void onSuccess(final GardenContainer garden);
    void onError(String message);
}
