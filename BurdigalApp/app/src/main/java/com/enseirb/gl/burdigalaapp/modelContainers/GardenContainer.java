package com.enseirb.gl.burdigalaapp.modelContainers;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.Garden;
import com.enseirb.gl.burdigalaapp.model.Model;

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
