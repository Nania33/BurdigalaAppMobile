package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.filters.Filter;
import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.presenter.visitor.Visitor;
import com.enseirb.gl.burdigalaapp.presenter.visitor.listener.IPresenterListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

import java.util.List;

/**
 * Created by Nania on 23/11/2015.
 */
public interface IModelContainer<T extends Model> {
    IModelContainer getSubContainer (Filter filter);
    List<T> getModels();
    void put(List<T> data);
    //void retrievePlaces(Visitor retriever, IPresenterListener listener);
    void retrievePlaces(Visitor businessVisitor, IPresenterListener listener, Filter filter, OpenDataRetriever retriever);
}
