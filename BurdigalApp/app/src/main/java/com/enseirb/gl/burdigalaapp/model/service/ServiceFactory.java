package com.enseirb.gl.burdigalaapp.model.service;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.exceptions.UnknownDataException;

public class ServiceFactory {
    public static Service makeChoice(ServiceType type) throws UnknownDataException {
        switch (type){
            case TOILET:
                return makeToilet();
            case GARDEN:
                return makeGarden();
            case PARKING:
                return makeParking();
            case CYCLEPARK:
                return makeCycleParkChoice();
            default:
                throw new UnknownDataException("type inconnu");
        }
    }

    public static Service makeCycleParkChoice(){
        return new Service(ServiceType.CYCLEPARK, "Parking deux roues", "Parking pour vélo et mobilette",
                R.color.cycle_park_color, "cycleParkFile");
    }

    public static Service makeGarden(){
        return new Service(ServiceType.GARDEN, "Parcs et jardins", "Parcs et jardins dans bordeaux",
                R.color.garden_color, "gardenFile");
    }

    public static Service makeParking(){
        return new Service(ServiceType.PARKING, "Parking", "Parking pour voitures",
                R.color.parking_color, "parkingFile");
    }

    public static Service makeToilet(){
        return new Service(ServiceType.TOILET, "Toilettes", "Toilettes publiques",
                R.color.toilet_color, "toiletFile");
    }
}
