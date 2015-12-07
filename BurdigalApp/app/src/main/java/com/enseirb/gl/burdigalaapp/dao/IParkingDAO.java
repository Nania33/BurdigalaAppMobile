package com.enseirb.gl.burdigalaapp.dao;

import com.enseirb.gl.burdigalaapp.dao.listener.IParkingDAOListener;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IParkingDAO {
    void retrieveParkingPlaces(final IParkingDAOListener listener);
}
