package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.presenter.visitor.Visitor;
import com.enseirb.gl.burdigalaapp.presenter.visitor.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11/30/2015.
 */
public class ParkingContainer implements IModelContainer<Parking>{
    private List<Parking> parkings;

    public ParkingContainer(){
        parkings = new ArrayList<>();
    }

    public ParkingContainer(List<Parking> parkings){
        this.parkings = parkings;
    }

    @Override
    public ParkingContainer getSubContainer(Filter filter) {
        return filter.filterModels(this);
    }

    @Override
    public List<Parking> getModels() {
        return parkings;
    }

    @Override
    public void put(List<Parking> data) {
        parkings.addAll(data);
    }

    @Override
    public void retrievePlaces(Visitor businessVisitor, IPresenterListener listener, Filter filter, OpenDataRetriever retriever) {
        businessVisitor.callToBusiness(this, listener, filter, retriever);
    }

}
