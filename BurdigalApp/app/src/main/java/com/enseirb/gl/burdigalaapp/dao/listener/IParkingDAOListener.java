package com.enseirb.gl.burdigalaapp.dao.listener;

import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;

import java.util.List;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IParkingDAOListener {
    void onSuccess(List<ParkingDTO> parkingDTOs);

    void onError(String message);
}
