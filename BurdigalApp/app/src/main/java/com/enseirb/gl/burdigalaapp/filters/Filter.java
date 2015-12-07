package com.enseirb.gl.burdigalaapp.filters;

import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;

/**
 * Created by Nania on 23/11/2015.
 */
public interface Filter {
    public GardenContainer filterModels(GardenContainer container);
    public CycleParkContainer filterModels(CycleParkContainer container);
    public ParkingContainer filterModels(ParkingContainer container);
    public ToiletContainer filterModels(ToiletContainer container);
}
