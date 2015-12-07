package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.presenter.visitor.Visitor;
import com.enseirb.gl.burdigalaapp.presenter.visitor.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11/30/2015.
 */
public class ToiletContainer implements IModelContainer<Toilet> {
    private List<Toilet> toilets;

    public ToiletContainer(){
        toilets = new ArrayList<>();
    }

    public ToiletContainer(List<Toilet> toilets){
        this.toilets = toilets;
    }

    @Override
    public IModelContainer getSubContainer(Filter filter) {
        return filter.filterModels(this);
    }

    @Override
    public List<Toilet> getModels() {
        return toilets;
    }

    @Override
    public void put(List<Toilet> data) {
        toilets.addAll(data);
    }

    @Override
    public void retrievePlaces(Visitor businessVisitor, IPresenterListener listener, Filter filter, OpenDataRetriever retriever) {
        businessVisitor.callToBusiness(this, listener, filter, retriever);
    }
}
