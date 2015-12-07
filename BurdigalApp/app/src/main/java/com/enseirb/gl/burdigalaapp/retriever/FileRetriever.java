package com.enseirb.gl.burdigalaapp.retriever;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.retriever.listener.DataRetrieverListener;

/**
 * Created by rchabot on 02/12/15.
 */
public class FileRetriever implements OpenDataRetriever {


    @Override
    public void retrievePlaces(GardenContainer container, DataRetrieverListener listener, Filter filter) {

    }

    @Override
    public void retrievePlaces(CycleParkContainer container, DataRetrieverListener listener, Filter filter) {

    }

    @Override
    public void retrievePlaces(ToiletContainer container, DataRetrieverListener listener, Filter filter) {

    }

    @Override
    public void retrievePlaces(ParkingContainer container, DataRetrieverListener listener, Filter filter) {

    }
}
