package com.enseirb.gl.burdigalaapp.converter;

import com.enseirb.gl.burdigalaapp.converter.listener.IGardenConverterListener;
import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

/**
 * Created by rchabot on 17/11/15.
 */
public interface IGardenConverter {
    void retrieveGardenPlaces(OpenDataRetriever retriever, final IGardenConverterListener listener);
    Garden convert(GardenDTO gardenDTO);
}
