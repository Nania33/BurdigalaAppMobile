package com.enseirb.gl.burdigalaapp.converter.listener;

import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;

public interface ICycleParkConverterListener {
    void onSuccess(final CycleParkContainer cyclePark);
    void onError(String message);
}
