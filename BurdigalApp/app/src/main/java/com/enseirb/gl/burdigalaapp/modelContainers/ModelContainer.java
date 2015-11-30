package com.enseirb.gl.burdigalaapp.modelContainers;

import com.enseirb.gl.burdigalaapp.filters.Filter;

/**
 * Created by Nania on 23/11/2015.
 */
public interface ModelContainer {
    public ModelContainer getSubContainer (Filter filter);
}
