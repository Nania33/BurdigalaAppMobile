package com.enseirb.gl.burdigalaapp.filters;

import com.enseirb.gl.burdigalaapp.model.Model;
import com.enseirb.gl.burdigalaapp.modelContainers.ModelContainer;

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

    public ModelContainer filterModels(List<Model> models){
        List<Model> result = new ArrayList<Model>();
        int nbPointsRemaining = zero;
        while(nbPointsRemaining != nbPoints){
            System.out.println("Application du Filtre : Ajout point : " + nbPoints);
            result.add(getNextModel(models, result));
            nbPointsRemaining++;
        }
        return new ModelContainer(result);
    }

    private Model getNextModel(List<Model> models, List<Model> result){
        Model toBeAdded;
        toBeAdded = models.get(nbPoints);
        return toBeAdded;
    }

}