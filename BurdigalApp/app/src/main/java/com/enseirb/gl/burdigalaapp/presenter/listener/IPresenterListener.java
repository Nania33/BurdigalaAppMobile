package com.enseirb.gl.burdigalaapp.presenter.listener;

/**
 * Created by rchabot on 04/12/15.
 */
public interface IPresenterListener {
    void onDataRetreived();
    void onError(String message);
}