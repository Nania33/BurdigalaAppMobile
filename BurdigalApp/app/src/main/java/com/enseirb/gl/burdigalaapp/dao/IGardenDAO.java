package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public interface IGardenDAO{
    void retrieveGardenPlaces(OpenDataRetriever retriever, final IGardenDAOListener listener);
}
