package com.enseirb.gl.burdigalaapp.converter.listener;

import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;

public interface IToiletConverterListener {
    void onSuccess(final ToiletContainer toilet);
    void onError(String message);
}
