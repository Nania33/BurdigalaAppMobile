package com.enseirb.gl.burdigalaapp.filters;

import com.enseirb.gl.burdigalaapp.model.CyclePark;
import com.enseirb.gl.burdigalaapp.model.Garden;
import com.enseirb.gl.burdigalaapp.model.Model;
import com.enseirb.gl.burdigalaapp.model.Parking;
import com.enseirb.gl.burdigalaapp.model.Toilet;
import com.enseirb.gl.burdigalaapp.modelContainers.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.modelContainers.GardenContainer;
import com.enseirb.gl.burdigalaapp.modelContainers.IModelContainer;
import com.enseirb.gl.burdigalaapp.modelContainers.ModelContainer;
import com.enseirb.gl.burdigalaapp.modelContainers.ParkingContainer;
import com.enseirb.gl.burdigalaapp.modelContainers.ToiletContainer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 11/30/2015.
 */


public class LinearFilter implements Filter{
    final private int zero = 0;
    private int nbPoints;

    public LinearFilter(int nbPoints){
        this.nbPoints= nbPoints;
    }


    private Garden getNextModelGarden(List<Garden> models){
        return models.get(nbPoints);
    }

    private CyclePark getNextModelCyclePark(List<CyclePark> models){
        return models.get(nbPoints);
    }

    private Parking getNextModelParking(List<Parking> models){
        return models.get(nbPoints);
    }

    private Toilet getNextModelToilet(List<Toilet> models) {
        return models.get(nbPoints);
    }

    @Override
    public GardenContainer filterModels(GardenContainer container) {
        List<Garden> result = new ArrayList<>();
        int nbPointsRemaining = zero;
        while(nbPointsRemaining != nbPoints){
            System.out.println("Application du Filtre : Ajout point : " + nbPoints);
            result.add(getNextModelGarden(container.getModels()));
            nbPointsRemaining++;
        }
        return new GardenContainer(result);

    }

    @Override
    public CycleParkContainer filterModels(CycleParkContainer container) {
        List<CyclePark> result = new ArrayList<>();
        int nbPointsRemaining = zero;
        while(nbPointsRemaining != nbPoints){
            System.out.println("Application du Filtre : Ajout point : " + nbPoints);
            result.add(getNextModelCyclePark(container.getModels()));
            nbPointsRemaining++;
        }
        return new CycleParkContainer(result);
    }

    @Override
    public ParkingContainer filterModels(ParkingContainer container) {
        List<Parking> result = new ArrayList<>();
        int nbPointsRemaining = zero;
        while(nbPointsRemaining != nbPoints){
            System.out.println("Application du Filtre : Ajout point : " + nbPoints);
            result.add(getNextModelParking(container.getModels()));
            nbPointsRemaining++;
        }
        return new ParkingContainer(result);
    }

    @Override
    public ToiletContainer filterModels(ToiletContainer container) {
        List<Toilet> result = new ArrayList<>();
        int nbPointsRemaining = zero;
        while(nbPointsRemaining != nbPoints){
            System.out.println("Application du Filtre : Ajout point : " + nbPoints);
            result.add(getNextModelToilet(container.getModels()));
            nbPointsRemaining++;
        }
        return new ToiletContainer(result);
    }

}