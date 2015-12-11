package com.enseirb.gl.burdigalaapp.dao.listener;

import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;

import java.util.List;

public interface ICycleParkDAOListener extends IDataDAOListener<CycleParkDTO> {
    void onSuccess(List<CycleParkDTO> cycleParkDTOs);
    void onError(String message);
}
