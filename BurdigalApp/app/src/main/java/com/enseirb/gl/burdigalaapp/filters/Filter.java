package com.enseirb.gl.burdigalaapp.filters;

import com.enseirb.gl.burdigalaapp.model.Model;
import com.enseirb.gl.burdigalaapp.modelContainers.ModelContainer;

import java.util.List;

/**
 * Created by Nania on 23/11/2015.
 */
public interface Filter {
    public ModelContainer filterModels(List<Model> models);
}
