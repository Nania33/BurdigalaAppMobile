package com.enseirb.gl.burdigalaapp.model.container;

import com.enseirb.gl.burdigalaapp.exceptions.UnknownDataException;
import com.enseirb.gl.burdigalaapp.model.service.ServiceType;

public class ContainerFactory {
    public static IModelContainer makeContainer(ServiceType type) throws UnknownDataException {
        switch (type) {
            case TOILET:
                return new ToiletContainer();
            case CYCLEPARK:
                return new CycleParkContainer();
            case GARDEN:
                return new GardenContainer();
            case PARKING:
                return new ParkingContainer();
            default:
                throw new UnknownDataException("Type " + type + " inconnu");
        }
    }
}
