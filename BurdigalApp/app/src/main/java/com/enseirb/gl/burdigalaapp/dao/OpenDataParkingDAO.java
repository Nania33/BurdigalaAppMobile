package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

/**
 * Created by rchabot on 05/12/15.
 */
public class OpenDataParkingDAO implements IParkingDAO {
    public static final String TAG = "OpenDataParkingDAO";

    @Override
    public void retrieveParkingPlaces(OpenDataRetriever retriever, final IParkingDAOListener listener) {
        retriever.retrieveParkingPlaces(listener);
    }
}
