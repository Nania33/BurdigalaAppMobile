package com.enseirb.gl.burdigalaapp.business.listener;

import com.enseirb.gl.burdigalaapp.model.data.Toilet;

import java.util.List;

public interface IToiletBusinessListener {
    void onSuccess(final List<Toilet> toilets);
    void onError(String message);
}
