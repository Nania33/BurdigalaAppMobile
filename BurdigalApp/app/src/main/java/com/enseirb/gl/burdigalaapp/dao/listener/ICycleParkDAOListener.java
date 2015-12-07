package com.enseirb.gl.burdigalaapp.dao.listener;

import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;

import java.util.List;

/**
 * Created by Nania on 17/11/2015.
 */
public interface ICycleParkDAOListener {
    void onSuccess(List<CycleParkDTO> cycleParkDTOs);

    void onError(String message);
}
