package com.enseirb.gl.burdigalaapp.business.listener;

import com.enseirb.gl.burdigalaapp.model.data.CyclePark;

import java.util.List;

public interface ICycleParkBusinessListener {
    void onSuccess(final List<CyclePark> cycleParks);
    void onError(String message);
}
