package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.ICycleParkDAOListener;

/**
 * Created by Nania on 17/11/2015.
 */
public interface ICycleParkDAO {
    void retrieveCycleParkPlaces(final ICycleParkDAOListener listener);
}
