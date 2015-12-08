package com.enseirb.gl.burdigalaapp.presenter.fragment.detail.listener;

import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;

/**
 * Created by rchabot on 07/12/15.
 */
public interface InteractionListener {
    public void onButtonReturnClick();
    public void onFocusRequired(Model point, Service service);
}
