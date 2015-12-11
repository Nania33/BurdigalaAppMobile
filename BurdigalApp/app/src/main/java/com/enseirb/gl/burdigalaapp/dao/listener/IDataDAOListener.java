package com.enseirb.gl.burdigalaapp.dao.listener;

import com.enseirb.gl.burdigalaapp.dto.DataDTO;

import java.util.List;

public interface IDataDAOListener<T extends DataDTO> {
    void onSuccess(List<T> dto);
    void onError(String message);
}
