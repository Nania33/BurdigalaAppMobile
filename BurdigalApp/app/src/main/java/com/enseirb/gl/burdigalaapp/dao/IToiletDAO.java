package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.IGardenDAOListener;
import com.enseirb.gl.burdigalaapp.dao.listener.IToiletDAOListener;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IToiletDAO {
    void retrieveToiletPlaces(final IToiletDAOListener listener);
}
