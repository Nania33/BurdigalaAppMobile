package com.enseirb.gl.burdigalaapp.filters;

import com.enseirb.gl.burdigalaapp.model.CyclePark;
import com.enseirb.gl.burdigalaapp.model.Model;
import com.enseirb.gl.burdigalaapp.model.Parking;
import com.enseirb.gl.burdigalaapp.model.Toilet;
import com.enseirb.gl.burdigalaapp.modelContainers.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.modelContainers.GardenContainer;
import com.enseirb.gl.burdigalaapp.modelContainers.IModelContainer;
import com.enseirb.gl.burdigalaapp.modelContainers.ModelContainer;
import com.enseirb.gl.burdigalaapp.modelContainers.ParkingContainer;
import com.enseirb.gl.burdigalaapp.modelContainers.ToiletContainer;

import java.util.List;

/**
 * Created by Nania on 23/11/2015.
 */
public interface Filter {
    public GardenContainer filterModels(GardenContainer container);
    public CycleParkContainer filterModels(CycleParkContainer container);
    public ParkingContainer filterModels(ParkingContainer container);
    public ToiletContainer filterModels(ToiletContainer container);
}
