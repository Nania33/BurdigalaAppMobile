package com.enseirb.gl.burdigalaapp.model.service;

public enum ServiceType {
    TOILET, PARKING, GARDEN, CYCLEPARK;

    public static ServiceType toServiceType(String type){
        if (type.equals("toilet"))
            return TOILET;
        if (type.equals("parking"))
            return PARKING;
        if (type.equals("garden"))
            return GARDEN;
        if (type.equals("cyclepark"))
            return CYCLEPARK;
        return null;
    }

    @Override
    public String toString() {
        switch (this){
            case TOILET:
                return "toilet";
            case PARKING:
                return "parking";
            case GARDEN:
                return "garden";
            case CYCLEPARK:
                return "cyclepark";
            default:
                return "type invalide";
        }
    }
}
