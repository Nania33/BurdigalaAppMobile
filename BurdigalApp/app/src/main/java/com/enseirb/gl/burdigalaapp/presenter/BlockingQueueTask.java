package com.enseirb.gl.burdigalaapp.presenter;

import com.enseirb.gl.burdigalaapp.model.service.Service;

public class BlockingQueueTask {
    private Service service;
    private boolean success;
    private String message;


    private BlockingQueueTask(Service service, boolean success, String message) {
        this.service = service;
        this.success = success;
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

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
