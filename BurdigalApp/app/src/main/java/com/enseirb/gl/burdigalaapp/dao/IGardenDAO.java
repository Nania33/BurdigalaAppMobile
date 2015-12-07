package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

/**
 * Created by rchabot on 17/11/15.
 */
public interface IGardenDAO {
    void retrieveGardenPlaces(OpenDataRetriever retriever, final IGardenDAOListener listener);
}
