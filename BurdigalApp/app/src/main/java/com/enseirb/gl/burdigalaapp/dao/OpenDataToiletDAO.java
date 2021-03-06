package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public class OpenDataToiletDAO implements IToiletDAO {
    public static final String TAG = "OpenDataToiletDAO";

    @Override
    public void retrieveToiletPlaces(OpenDataRetriever retriever, final IToiletDAOListener listener) {
        retriever.retrieveToiletPlaces(listener);
    }

}
