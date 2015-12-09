package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

/**
 * Created by Nania on 17/11/2015.
 */
public interface ICycleParkDAO {
    void retrieveCycleParkPlaces(OpenDataRetriever retriever, final ICycleParkDAOListener listener);
}
