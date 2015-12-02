package com.enseirb.gl.burdigalaapp.retriever;

import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;

import java.util.List;

/**
 * Created by rchabot on 02/12/15.
 */
public interface OpenDataRetriever {
    void retrievePlaces(GardenContainer container);
    void retrievePlaces(CycleParkContainer container);
    void retrievePlaces(ToiletContainer container);
    void retrievePlaces(ParkingContainer container);
}
