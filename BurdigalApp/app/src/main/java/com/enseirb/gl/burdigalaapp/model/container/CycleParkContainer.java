package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11/30/2015.
 */
public class CycleParkContainer implements IModelContainer {
    private List<CyclePark> cycleParks;

    public CycleParkContainer(){
        cycleParks = new ArrayList<>();
    }

    public CycleParkContainer(List<CyclePark> cycleParks){
        this.cycleParks = cycleParks;
    }

    @Override
    public CycleParkContainer getSubContainer(Filter filter) {
        return filter.filterModels(this);
    }

    @Override
    public List<CyclePark> getModels() {
        return cycleParks;
    }

    @Override
    public void put(List data) {

    }

    @Override
    public void retrievePlaces(OpenDataRetriever retriever) {

    }
}
