package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.presenter.visitor.Visitor;
import com.enseirb.gl.burdigalaapp.presenter.visitor.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11/30/2015.
 */
public class GardenContainer implements IModelContainer<Garden> {
    private List<Garden> gardens;

    public GardenContainer(){
        this.gardens = new ArrayList<>();
    }

    public GardenContainer(List<Garden> gardens){
        this.gardens = gardens;
    }

    public List<Garden> getModels(){
        return gardens;
    }

    @Override
    public void put(List<Garden> data) {
        gardens.addAll(data);
    }

    @Override
    public void retrievePlaces(Visitor visitor, IPresenterListener listener, Filter filter, OpenDataRetriever retriever) {
        visitor.callToBusiness(this, listener, filter, retriever);
    }

    public GardenContainer getSubContainer(Filter filter){
        return filter.filterModels(this);
    }
}
