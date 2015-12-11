package com.enseirb.gl.burdigalaapp.presenter.visitor;

import com.enseirb.gl.burdigalaapp.business.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.presenter.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public interface BusinessVisitor {
    void callToBusiness(GardenContainer container, IPresenterListener listener, Filter filter, OpenDataRetriever retriever);
    void callToBusiness(CycleParkContainer container, IPresenterListener listener, Filter filter, OpenDataRetriever retriever);
    void callToBusiness(ToiletContainer container, IPresenterListener listener, Filter filter, OpenDataRetriever retriever);
    void callToBusiness(ParkingContainer container, IPresenterListener listener, Filter filter, OpenDataRetriever retriever);

    GardenContainer applyFilter(GardenContainer container, Filter filter);
    CycleParkContainer applyFilter(CycleParkContainer container, Filter filter);
    ToiletContainer applyFilter(ToiletContainer container, Filter filter);
    ParkingContainer applyFilter(ParkingContainer container, Filter filter);
}
