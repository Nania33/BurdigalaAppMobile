package com.enseirb.gl.burdigalaapp.converter;

import com.enseirb.gl.burdigalaapp.converter.listener.ICycleParkConverterListener;
import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

/**
 * Created by Nania on 17/11/2015.
 */
public interface ICycleParkConverter {
    void retrieveCycleParkPlaces(OpenDataRetriever retriever, final ICycleParkConverterListener listener);
    CyclePark convert(CycleParkDTO gardenDTO);
}
