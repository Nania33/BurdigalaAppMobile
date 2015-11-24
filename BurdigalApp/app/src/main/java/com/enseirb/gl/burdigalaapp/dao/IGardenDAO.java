package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;

/**
 * Created by rchabot on 17/11/15.
 */
public interface IGardenDAO {
    void retrieveGardenPlaces(final IGardenDAOListener listener);
}
