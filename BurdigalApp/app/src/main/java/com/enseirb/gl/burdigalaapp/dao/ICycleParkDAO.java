package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public interface ICycleParkDAO{
    void retrieveCycleParkPlaces(OpenDataRetriever retriever, final ICycleParkDAOListener listener);
}
