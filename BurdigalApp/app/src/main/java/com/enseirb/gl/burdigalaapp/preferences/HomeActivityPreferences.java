package com.enseirb.gl.burdigalaapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by rchabot on 17/11/15.
 */
public class HomeActivityPreferences implements IActivityPreferences{

    final private String SERVICE = "services";
    final private int DEFAULT_NBPOINTS = 10;
    final private String NB_POINTS = "nb_points";
    final private String TAG = "HomeActivityPreferences";
    final private String keys[] = {"Toilettes","Parking","Parcs et jardins","Parking deux roues"};
    HashMap<String,String> selectedServices;
    private int nbPoints;
    Context context;

    public HomeActivityPreferences(Context context){
        this.context = context;
        this.selectedServices = new HashMap<>();
        nbPoints = DEFAULT_NBPOINTS;
    }

    public void addServiceToPreferences(String key, String service){
        selectedServices.put(key, service);
    }

    public void removeServiceFromPreferences(String name){
        selectedServices.remove(name);
    }

    public void changeNbPointsFromPreferences(int nbPoints){
        this.nbPoints = nbPoints;
    }

    @Override
    public void savePreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        for(Map.Entry<String,String> entry : selectedServices.entrySet()){
            Log.d(TAG, "[save] -" + entry.getKey());
            editor.putString(entry.getKey(), entry.getValue());
        }
        editor.putInt(NB_POINTS,nbPoints);
        editor.commit();
    }

    @Override
    public void loadPreferences() {
        /*Map<String, ?> keys = PreferenceManager.getDefaultSharedPreferences(context).getAll();
        for(Map.Entry<String,?> entry : keys.entrySet()){
            Log.d(TAG, "[load] -" + entry.getValue().toString());
            addServiceToPreferences(entry.getKey(), entry.getValue().toString());
        }*/
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(context);
        for(int i = 0 ; i < keys.length ; ++i){
            String preference = pref.getString(keys[i],null);
            if(preference != null){
                if(!selectedServices.containsValue(keys[i])){
                    Log.d(TAG,"[load] - add - " + keys[i]);
                    addServiceToPreferences(keys[i], keys[i]);
                }
            }
            else{
                if(selectedServices.containsValue(keys[i])){
                    Log.d(TAG,"[load] - remove - " + keys[i]);
                    addServiceToPreferences(keys[i], keys[i]);
                }
            }
        }
        changeNbPointsFromPreferences(pref.getInt(NB_POINTS,DEFAULT_NBPOINTS));
        Log.d(TAG,Integer.toString(pref.getInt(NB_POINTS,DEFAULT_NBPOINTS)));
    }

    public Map getSelectedServices(){
        return selectedServices;
    }

    public int getNbPoints(){
        return nbPoints;
    }
}
