package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.business.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.presenter.visitor.BusinessVisitor;
import com.enseirb.gl.burdigalaapp.presenter.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

public class ToiletContainer implements IModelContainer<Toilet> {
    private List<Toilet> toilets;

    public ToiletContainer(){
        toilets = new ArrayList<>();
    }

    public ToiletContainer(List<Toilet> toilets){
        this.toilets = toilets;
    }

    @Override
    public List<Toilet> getModels() {
        return toilets;
    }

    @Override
    public void put(List<Toilet> data) {
        toilets.addAll(data);
    }


    /********************************
     *  Accès à la couche business  *
     ********************************/

    @Override
    public void retrievePlaces(BusinessVisitor businessVisitor, Filter filter, OpenDataRetriever retriever, IPresenterListener listener) {
        businessVisitor.callToBusiness(this, listener, filter, retriever);
    }

    @Override
    public IModelContainer applyFilter(BusinessVisitor businessVisitor, Filter filter) {
        return businessVisitor.applyFilter(this, filter);
    }
}
