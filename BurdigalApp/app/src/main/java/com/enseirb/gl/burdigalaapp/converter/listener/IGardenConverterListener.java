package com.enseirb.gl.burdigalaapp.converter.listener;

import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;

public interface IGardenConverterListener {
    void onSuccess(final GardenContainer garden);
    void onError(String message);
}
