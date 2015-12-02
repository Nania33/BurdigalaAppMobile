package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Garden;

import java.util.List;

/**
 * Created by Alex on 11/30/2015.
 */
public class GardenContainer implements IModelContainer<Garden> {
    private List<Garden> gardens;

    public GardenContainer(List<Garden> gardens){
        this.gardens = gardens;
    }

    public List<Garden> getModels(){
        return gardens;
    }

    public GardenContainer getSubContainer(Filter filter){
        return filter.filterModels(this);
    }
}
