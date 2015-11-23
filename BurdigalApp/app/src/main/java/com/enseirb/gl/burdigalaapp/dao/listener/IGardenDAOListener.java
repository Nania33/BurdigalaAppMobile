package com.enseirb.gl.burdigalaapp.dao.listener;

import com.enseirb.gl.burdigalaapp.dto.GardenDTO;

import java.util.List;

/**
 * Created by rchabot on 17/11/15.
 */
public interface IGardenDAOListener {
    void onSuccess(List<GardenDTO> gardenDTO);

    void onError(String message);
}
