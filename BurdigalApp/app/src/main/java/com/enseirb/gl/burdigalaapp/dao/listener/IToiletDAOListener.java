package com.enseirb.gl.burdigalaapp.dao.listener;

import com.enseirb.gl.burdigalaapp.dto.ToiletDTO;

import java.util.List;

public interface IToiletDAOListener extends IDataDAOListener<ToiletDTO> {
    void onSuccess(List<ToiletDTO> toiletDTO);
    void onError(String message);
}
