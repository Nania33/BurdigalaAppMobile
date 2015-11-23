package com.enseirb.gl.burdigalaapp.converter;

import com.enseirb.gl.burdigalaapp.converter.listener.IGardenConverterListener;
import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.model.Garden;

/**
 * Created by rchabot on 23/11/15.
 */
public class GardenConverter implements IGardenConverter {
    @Override
    public void retrieveGardenPlaces(IGardenConverterListener listener) {

    }

    @Override
    public Garden convert(GardenDTO gardenDTO) {
        return new Garden(gardenDTO.getName(), gardenDTO.getCoords());
    }
}
