package com.enseirb.gl.burdigalaapp.converter.listener;

import com.enseirb.gl.burdigalaapp.model.data.Garden;

import java.util.List;

/**
 * Created by rchabot on 17/11/15.
 */
public interface IGardenConverterListener {
    void onSuccess(final List<Garden> garden);
    void onError(String message);
}
