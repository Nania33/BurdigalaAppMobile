package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.business.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.presenter.visitor.BusinessVisitor;
import com.enseirb.gl.burdigalaapp.presenter.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

public class ParkingContainer implements IModelContainer<Parking>{
    private List<Parking> parkings;

    public ParkingContainer(){
        parkings = new ArrayList<>();
    }

    public ParkingContainer(List<Parking> parkings){
        this.parkings = parkings;
    }

    @Override
    public List<Parking> getModels() {
        return parkings;
    }

    @Override
    public void put(List<Parking> data) {
        parkings.addAll(data);
    }


    /********************************
     *  Accès à la couche business  *
     ********************************/

    @Override
    public void retrievePlaces(BusinessVisitor businessVisitor, Filter filter, OpenDataRetriever retriever, IPresenterListener listener) {
        businessVisitor.callToBusiness(this, listener, filter, retriever);
    }

    @Override
    public ParkingContainer applyFilter(BusinessVisitor businessVisitor, Filter filter) {
        return businessVisitor.applyFilter(this, filter);
    }

}
