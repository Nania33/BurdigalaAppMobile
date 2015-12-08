package com.enseirb.gl.burdigalaapp.filters;

import com.enseirb.gl.burdigalaapp.model.data.CyclePark;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.model.container.CycleParkContainer;
import com.enseirb.gl.burdigalaapp.model.container.GardenContainer;
import com.enseirb.gl.burdigalaapp.model.container.ParkingContainer;
import com.enseirb.gl.burdigalaapp.model.container.ToiletContainer;

import java.util.ArrayList;
import java.util.List;



public class LinearFilter implements Filter{
    final private int zero = 0;
    private int nbPoints;

    public LinearFilter(int nbPoints){
        this.nbPoints= nbPoints;
    }


    @Override
    public GardenContainer filterModels(GardenContainer container) {
        List<Garden> result = new ArrayList<>();
        int nbPointsRemaining = zero;
        while(nbPointsRemaining != nbPoints){
            System.out.println("Application du Filtre : Ajout point : " + nbPoints);
            result.add(container.getModels().get(nbPointsRemaining));
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
            result.add(container.getModels().get(nbPointsRemaining));
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
            result.add(container.getModels().get(nbPointsRemaining));
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
            result.add(container.getModels().get(nbPointsRemaining));
            nbPointsRemaining++;
        }
        return new ToiletContainer(result);
    }

}