package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public interface IToiletDAO{
    void retrieveToiletPlaces(OpenDataRetriever retriever, final IToiletDAOListener listener);
}
