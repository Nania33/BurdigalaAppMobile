package com.enseirb.gl.burdigalaapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rchabot on 17/11/15.
 */
public class MapsActivityPreferences implements IActivityPreferences{
    final private String NB_POINTS = "nbPoints";

    private Context context;

    private Map<String, String> filters;
    int nbPoints;

    public MapsActivityPreferences(Context context){
        filters = new HashMap<>();
        this.context = context;
    }

    public void addFilter(String key, String filterName){
        filters.put(key,filterName);
    }

    public void deleteFilter(String filterName){
        filters.remove(filterName);
    }

    public void changeNbPoints(int nbPoints){
        this.nbPoints = nbPoints;
    }

    @Override
    public void savePreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        for(String key : filters.keySet()){
            editor.putString(key,filters.get(key));
        }
        editor.putInt(NB_POINTS,nbPoints);
        editor.commit();
    }

    @Override
    public void loadPreferences() {
        Map<String, ?> keys = PreferenceManager.getDefaultSharedPreferences(context).getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            if(entry.getValue().getClass().equals(Integer.class)){
                changeNbPoints((Integer) entry.getValue());
                continue;
            }
            addFilter(entry.getKey(),(String)entry.getValue());
        }
    }

    public Map getFilters(){
        return filters;
    }

    public int getNbPoints(){
        return nbPoints;
    }
}
