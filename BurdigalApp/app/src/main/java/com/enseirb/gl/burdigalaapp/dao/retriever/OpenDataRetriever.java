package com.enseirb.gl.burdigalaapp.dao.retriever;

import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;

public interface OpenDataRetriever {
    void retrieveToiletPlaces(final IToiletDAOListener listener);
    void retrieveGardenPlaces(final IGardenDAOListener listener);
    void retrieveCycleParkPlaces(final ICycleParkDAOListener listener);
    void retrieveParkingPlaces(final IParkingDAOListener listener);
}
