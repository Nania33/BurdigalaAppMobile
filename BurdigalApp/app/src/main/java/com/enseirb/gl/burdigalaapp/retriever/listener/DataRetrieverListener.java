package com.enseirb.gl.burdigalaapp.retriever.listener;

/**
 * Created by rchabot on 04/12/15.
 */
public interface DataRetrieverListener {
    void onDataRetreived();
    void onError(String message);
}
