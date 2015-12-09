package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.presenter.visitor.BusinessVisitor;
import com.enseirb.gl.burdigalaapp.presenter.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11/30/2015.
 */
public class CycleParkContainer implements IModelContainer<CyclePark> {
    private List<CyclePark> cycleParks;

    public CycleParkContainer(){
        cycleParks = new ArrayList<>();
    }

    public CycleParkContainer(List<CyclePark> cycleParks){
        this.cycleParks = cycleParks;
    }

    @Override
    public List<CyclePark> getModels() {
        return cycleParks;
    }

    @Override
    public void put(List<CyclePark> data) {
        cycleParks.addAll(data);
    }

    /********************************
     *  Accès à la couche business  *
     ********************************/

    @Override
    public void retrievePlaces(BusinessVisitor businessVisitor, Filter filter, OpenDataRetriever retriever, IPresenterListener listener) {
        businessVisitor.callToBusiness(this, listener, filter, retriever);
    }

    @Override
    public CycleParkContainer applyFilter(BusinessVisitor businessVisitor, Filter filter) {
        return businessVisitor.applyFilter(this, filter);
    }

}
