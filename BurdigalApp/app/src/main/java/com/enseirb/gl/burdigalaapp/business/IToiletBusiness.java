package com.enseirb.gl.burdigalaapp.business;

import com.enseirb.gl.burdigalaapp.business.listener.IToiletBusinessListener;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;
import com.enseirb.gl.burdigalaapp.dao.retriever.OpenDataRetriever;

/**
 * Created by Nania on 17/11/2015.
 */
public interface IToiletBusiness {
    void retrieveToiletPlaces(OpenDataRetriever retriever, final IToiletBusinessListener listener);

    ToiletContainer filterToilets(ToiletContainer container);
}
