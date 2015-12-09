package com.enseirb.gl.burdigalaapp.business;

import com.enseirb.gl.burdigalaapp.business.listener.IParkingBusinessListener;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IParkingBusiness {
    void retrieveParkingPlaces(OpenDataRetriever retriever, final IParkingBusinessListener listener);

    ParkingContainer filterParkings(ParkingContainer container);
}
