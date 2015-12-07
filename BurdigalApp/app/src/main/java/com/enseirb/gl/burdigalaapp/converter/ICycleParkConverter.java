package com.enseirb.gl.burdigalaapp.converter;

import com.enseirb.gl.burdigalaapp.converter.listener.ICycleParkConverterListener;
import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;

/**
 * Created by Nania on 17/11/2015.
 */
public interface ICycleParkConverter {
    void retrieveCycleParkPlaces(final ICycleParkConverterListener listener);
    void retrieveCycleParkPlacesFromFile(final ICycleParkConverterListener listener);
    CyclePark convert(CycleParkDTO gardenDTO);
}
