package com.enseirb.gl.burdigalaapp.business;

import com.enseirb.gl.burdigalaapp.business.listener.IToiletBusinessListener;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

public interface IToiletBusiness {
    void retrieveToiletPlaces(OpenDataRetriever retriever, final IToiletBusinessListener listener);

    ToiletContainer filterToilets(ToiletContainer container);
}
