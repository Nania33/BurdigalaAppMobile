package com.enseirb.gl.burdigalaapp.retriever;

import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;

/**
 * Created by rchabot on 07/12/15.
 */
public interface OpenDataRetriever {
    void retrieveToiletPlaces(final IToiletDAOListener listener);
    void retrieveGardenPlaces(final IGardenDAOListener listener);
    void retrieveCycleParkPlaces(final ICycleParkDAOListener listener);
    void retrieveParkingPlaces(final IParkingDAOListener listener);
}
