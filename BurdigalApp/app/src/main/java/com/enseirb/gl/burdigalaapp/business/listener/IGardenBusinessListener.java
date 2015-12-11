package com.enseirb.gl.burdigalaapp.business.listener;

import com.enseirb.gl.burdigalaapp.model.data.Garden;

import java.util.List;

public interface IGardenBusinessListener {
    void onSuccess(final List<Garden> garden);
    void onError(String message);
}
