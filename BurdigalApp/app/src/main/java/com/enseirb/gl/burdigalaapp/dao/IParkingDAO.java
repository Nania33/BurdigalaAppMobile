package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IParkingDAO {
    void retrieveParkingPlaces(OpenDataRetriever retriever, final IParkingDAOListener listener);
}
