package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;

import java.util.List;

/**
 * Created by Alex on 11/30/2015.
 */
public class ToiletContainer implements IModelContainer {
    private List<Toilet> toilets;

    public ToiletContainer(List<Toilet> toilets){
        this.toilets = toilets;
    }

    @Override
    public IModelContainer getSubContainer(Filter filter) {
        return filter.filterModels(this);
    }

    @Override
    public List getModels() {
        return toilets;
    }
}
