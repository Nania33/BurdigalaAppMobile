package com.enseirb.gl.burdigalaapp.presenter;

import com.enseirb.gl.burdigalaapp.presenter.service.Service;

/**
 * Created by rchabot on 05/12/15.
 */
public class BlockingQueueData {
    private Service service;
    private boolean succes;
    private String message;


    private BlockingQueueData(Service service, boolean success, String message) {
        this.service = service;
        this.succes = success;
        this.message = message;
    }

    public static BlockingQueueData recordSucces(Service service){
        return new BlockingQueueData(service, true, "Récupération "+service.getType());
    }

    public static BlockingQueueData recordFailure(Service service, String message){
        return new BlockingQueueData(service, false, message);
    }

    public Service getService() {
        return service;
    }

    public boolean isSucces() {
        return succes;
    }

    public String getMessage() {
        return message;
    }
}
