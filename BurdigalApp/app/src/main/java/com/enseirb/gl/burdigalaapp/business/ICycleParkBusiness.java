package com.enseirb.gl.burdigalaapp.business;

import com.enseirb.gl.burdigalaapp.business.listener.ICycleParkBusinessListener;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

/**
 * Created by Nania on 17/11/2015.
 */
public interface ICycleParkBusiness {
    void retrieveCycleParkPlaces(OpenDataRetriever retriever, final ICycleParkBusinessListener listener);

    CycleParkContainer filterCycleParks(CycleParkContainer container);
}
