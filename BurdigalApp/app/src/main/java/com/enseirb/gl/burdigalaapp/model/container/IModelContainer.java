package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.business.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.presenter.visitor.BusinessVisitor;
import com.enseirb.gl.burdigalaapp.presenter.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

import java.util.List;

/**
 * Created by Nania on 23/11/2015.
 */
public interface IModelContainer<T extends Model> {
    List<T> getModels();
    void put(List<T> data);
    void retrievePlaces(BusinessVisitor businessVisitor, Filter filter, OpenDataRetriever retriever, IPresenterListener listener);
    IModelContainer applyFilter(BusinessVisitor businessVisitor, Filter filter);
}
