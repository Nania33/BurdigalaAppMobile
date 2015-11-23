package com.enseirb.gl.burdigalaapp.business.listener;

import com.enseirb.gl.burdigalaapp.model.Garden;

/**
 * Created by rchabot on 17/11/15.
 */
public interface IGardenBusinessListener {
    void onSuccess(final Garden garden);
    void onError(String message);
}
