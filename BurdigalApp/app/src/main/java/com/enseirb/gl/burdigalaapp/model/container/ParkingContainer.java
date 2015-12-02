package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Parking;

import java.util.List;

/**
 * Created by Alex on 11/30/2015.
 */
public class ParkingContainer implements IModelContainer{
    private List<Parking> parkings;

    public ParkingContainer(List<Parking> parkings){
        this.parkings = parkings;
    }

    @Override
    public ParkingContainer getSubContainer(Filter filter) {
        return filter.filterModels(this);
    }

    @Override
    public List getModels() {
        return parkings;
    }
}
