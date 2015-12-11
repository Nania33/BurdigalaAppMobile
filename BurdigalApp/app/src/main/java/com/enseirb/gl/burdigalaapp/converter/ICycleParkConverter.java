package com.enseirb.gl.burdigalaapp.converter;

import com.enseirb.gl.burdigalaapp.converter.listener.ICycleParkConverterListener;
import com.enseirb.gl.burdigalaapp.dto.CycleParkDTO;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public interface ICycleParkConverter {
    void retrieveCycleParkPlaces(OpenDataRetriever retriever, final ICycleParkConverterListener listener);
    CyclePark convert(CycleParkDTO cycleParkDTO);
}
