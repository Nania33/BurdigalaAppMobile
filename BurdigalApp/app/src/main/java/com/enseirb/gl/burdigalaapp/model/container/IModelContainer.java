package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;
import com.enseirb.gl.burdigalaapp.retriever.listener.DataRetrieverListener;

import java.util.List;

/**
 * Created by Nania on 23/11/2015.
 */
public interface IModelContainer<T extends Model> {
    IModelContainer getSubContainer (Filter filter);
    List<T> getModels();
    void put(List<T> data);
    void retrievePlaces(OpenDataRetriever retriever, DataRetrieverListener listener);
}
