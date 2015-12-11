package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.business.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.presenter.visitor.BusinessVisitor;
import com.enseirb.gl.burdigalaapp.presenter.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

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


    /********************************
     *  Accès à la couche business  *
     ********************************/

    @Override
    public void retrievePlaces(BusinessVisitor businessVisitor, Filter filter, OpenDataRetriever retriever, IPresenterListener listener) {
        businessVisitor.callToBusiness(this, listener, filter, retriever);
    }

    public GardenContainer applyFilter(BusinessVisitor businessVisitor, Filter filter){
        return businessVisitor.applyFilter(this, filter);
    }
}
