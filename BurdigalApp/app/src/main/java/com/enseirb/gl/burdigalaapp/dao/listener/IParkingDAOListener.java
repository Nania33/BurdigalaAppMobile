package com.enseirb.gl.burdigalaapp.dao.listener;

import com.enseirb.gl.burdigalaapp.dto.ParkingDTO;

import java.util.List;

public interface IParkingDAOListener extends IDataDAOListener<ParkingDTO>{
    void onSuccess(List<ParkingDTO> parkingDTOs);
    void onError(String message);
}
