package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public class OpenDataCycleParkDAO implements ICycleParkDAO {
    public static final String TAG = "OpenDataCycleParkDAO";

    @Override
    public void retrieveCycleParkPlaces(OpenDataRetriever retriever, final ICycleParkDAOListener listener) {
       retriever.retrieveCycleParkPlaces(listener);
    }
}
