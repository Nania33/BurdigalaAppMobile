package com.enseirb.gl.burdigalaapp.converter.listener;

import com.enseirb.gl.burdigalaapp.model.Garden;

/**
 * Created by rchabot on 17/11/15.
 */
public interface IGardenConverterListener {
    void onSuccess(final Garden garden);
    void onError(String message);
}
