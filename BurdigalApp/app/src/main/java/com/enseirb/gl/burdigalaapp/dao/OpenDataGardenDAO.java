package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

/**
 * Created by rchabot on 23/11/15.
 */
public class OpenDataGardenDAO implements IGardenDAO {
    public static final String TAG = "OpenDataGardenDAO";

    @Override
    public void retrieveGardenPlaces(OpenDataRetriever retriever, final IGardenDAOListener listener) {
        retriever.retrieveGardenPlaces(listener);
    }

}
