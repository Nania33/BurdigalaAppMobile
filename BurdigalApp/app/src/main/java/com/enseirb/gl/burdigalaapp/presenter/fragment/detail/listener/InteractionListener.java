package com.enseirb.gl.burdigalaapp.presenter.fragment.detail.listener;

import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.model.service.Service;

public interface InteractionListener {
    public void onButtonReturnClick();
    public void onFocusRequired(Model point, Service service);
}
