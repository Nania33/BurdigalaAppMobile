package com.enseirb.gl.burdigalaapp.modelContainers;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.Model;

import java.util.List;

/**
 * Created by Alex on 11/29/2015.
 */
public class ModelContainer implements IModelContainer {
    List<Model> gardens;

    public ModelContainer(List<Model> gardens){
        this.gardens = gardens;
    }

    public List<Model> getModels(){
        return gardens;
    }

    public ModelContainer getSubContainer(Filter filter){
        return filter.filterModels(gardens);
    }
}

