package com.enseirb.gl.burdigalaapp.presenter.service;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.exceptions.UnknownDataException;

/**
 * Created by rchabot on 02/12/15.
 */
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

    //TODO mettre string dans fichier R.string
    public static Service makeCycleParkChoice(){
        return new Service(ServiceType.CYCLEPARK, "Parking deux roues", "Parking pour vélo et mobilette",
                R.color.orange);
    }

    public static Service makeGarden(){
        return new Service(ServiceType.GARDEN, "Parcs et jardins", "Parcs et jardins dans bordeaux",
                R.color.green);
    }

    public static Service makeParking(){
        return new Service(ServiceType.PARKING, "Parking", "Parking pour voitures",
                R.color.blue);
    }

    public static Service makeToilet(){
        return new Service(ServiceType.TOILET, "Toilettes", "Toilettes publiques",
                R.color.red);
    }
}
