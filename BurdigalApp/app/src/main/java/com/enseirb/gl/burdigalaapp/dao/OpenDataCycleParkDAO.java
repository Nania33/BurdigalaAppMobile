package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

/**
 * Created by rchabot on 05/12/15.
 */
public class OpenDataCycleParkDAO implements ICycleParkDAO {
    public static final String TAG = "OpenDataCycleParkDAO";

    @Override
    public void retrieveCycleParkPlaces(OpenDataRetriever retriever, final ICycleParkDAOListener listener) {
       retriever.retrieveCycleParkPlaces(listener);
    }
}
