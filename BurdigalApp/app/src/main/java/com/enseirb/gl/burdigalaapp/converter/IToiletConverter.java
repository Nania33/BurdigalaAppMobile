package com.enseirb.gl.burdigalaapp.converter;

import com.enseirb.gl.burdigalaapp.converter.listener.IGardenConverterListener;
import com.enseirb.gl.burdigalaapp.converter.listener.IToiletConverterListener;
import com.enseirb.gl.burdigalaapp.dto.GardenDTO;
import com.enseirb.gl.burdigalaapp.dto.ToiletDTO;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IToiletConverter {
    void retrieveToiletPlaces(OpenDataRetriever retriever, final IToiletConverterListener listener);
    Toilet convert(ToiletDTO toiletDTO);
}
