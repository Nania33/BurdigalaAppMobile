package com.enseirb.gl.burdigalaapp.dao.listener;

import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import java.util.List;

public interface IGardenDAOListener extends IDataDAOListener<GardenDTO>{
    void onSuccess(List<GardenDTO> gardenDTO);
    void onError(String message);
}
