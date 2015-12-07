package com.enseirb.gl.burdigalaapp.business;

import com.enseirb.gl.burdigalaapp.business.listener.IGardenBusinessListener;
import com.enseirb.gl.burdigalaapp.retriever.OpenDataRetriever;

/**
 * Created by rchabot on 17/11/15.
 */
public interface IGardenBusiness {
    void retrieveGardenPlaces(OpenDataRetriever retriever, final IGardenBusinessListener listener);
}
