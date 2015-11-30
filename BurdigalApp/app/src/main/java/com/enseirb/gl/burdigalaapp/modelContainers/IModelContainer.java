package com.enseirb.gl.burdigalaapp.modelContainers;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.Model;

import java.util.List;

/**
 * Created by Nania on 23/11/2015.
 */
public interface IModelContainer {
    public IModelContainer getSubContainer (Filter filter);
    public List<Model> getModels();
}
