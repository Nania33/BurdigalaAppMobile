package com.enseirb.gl.burdigalaapp.presenter;

import com.enseirb.gl.burdigalaapp.presenter.service.Service;

/**
 * Created by rchabot on 05/12/15.
 */
public class BlockingQueueTask {
    private Service service;
    private boolean succes;
    private String message;


    private BlockingQueueTask(Service service, boolean success, String message) {
        this.service = service;
        this.succes = success;
        this.message = message;
    }

    public static BlockingQueueTask recordSucces(Service service){
        return new BlockingQueueTask(service, true, "Récupération "+service.getType());
    }

    public static BlockingQueueTask recordFailure(Service service, String message){
        return new BlockingQueueTask(service, false, message);
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
