package com.enseirb.gl.burdigalaapp.converter.listener;

import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;

import java.util.List;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IToiletConverterListener {
    void onSuccess(final List<Toilet> toilets);
    void onError(String message);
}
