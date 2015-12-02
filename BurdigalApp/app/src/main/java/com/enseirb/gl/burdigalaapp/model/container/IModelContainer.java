package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Model;

import java.util.List;

/**
 * Created by Nania on 23/11/2015.
 */
public interface IModelContainer<T extends Model> {
    public IModelContainer getSubContainer (Filter filter);
    public List<T> getModels();
}
