package com.enseirb.gl.burdigalaapp.business.filters;

import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;

public class NoFilter implements Filter {
    @Override
    public GardenContainer filterModels(GardenContainer container) {
        return container;
    }

    @Override
    public CycleParkContainer filterModels(CycleParkContainer container) {
        return container;
    }

    @Override
    public ParkingContainer filterModels(ParkingContainer container) {
        return container;
    }

    @Override
    public ToiletContainer filterModels(ToiletContainer container) {
        return container;
    }
}
