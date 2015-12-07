package com.enseirb.gl.burdigalaapp.dao.listener;

import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.dto.ToiletDTO;

import java.util.List;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IToiletDAOListener {
    void onSuccess(List<ToiletDTO> toiletDTO);

    void onError(String message);
}
