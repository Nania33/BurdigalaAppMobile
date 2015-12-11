package com.enseirb.gl.burdigalaapp.business;

import com.enseirb.gl.burdigalaapp.business.listener.IGardenBusinessListener;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public interface IGardenBusiness {
    void retrieveGardenPlaces(OpenDataRetriever retriever, final IGardenBusinessListener listener);

    GardenContainer filterGardens(GardenContainer container);
}
