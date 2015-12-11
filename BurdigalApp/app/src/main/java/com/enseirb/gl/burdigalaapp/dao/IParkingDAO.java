package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public interface IParkingDAO{
    void retrieveParkingPlaces(OpenDataRetriever retriever, final IParkingDAOListener listener);
}
