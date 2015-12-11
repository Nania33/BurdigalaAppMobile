package com.enseirb.gl.burdigalaapp.converter;

import com.enseirb.gl.burdigalaapp.converter.listener.IGardenConverterListener;
import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public interface IGardenConverter {
    void retrieveGardenPlaces(OpenDataRetriever retriever, final IGardenConverterListener listener);
    Garden convert(GardenDTO gardenDTO);
}
