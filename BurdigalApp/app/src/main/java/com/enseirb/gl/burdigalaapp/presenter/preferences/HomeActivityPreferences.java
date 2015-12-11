package com.enseirb.gl.burdigalaapp.presenter.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;


import com.enseirb.gl.burdigalaapp.R;

import java.util.HashMap;
import java.util.Map;

public class HomeActivityPreferences implements IActivityPreferences{
    private static final String TAG = "HomeActivityPreferences";

    private static final int DEFAULT_NBPOINTS = 10;
    private static final int DEFAULT_RB = R.id.rb_bdx_center;

    private static final String NB_POINTS = "nb_points";
    private static final String SELECTED_RB = "radio_button";

    private static final String keys[] = {"Toilettes","Parking","Parcs et jardins","Parking deux roues"};

    private HashMap<String,String> selectedServices;
    private int nbPoints;
    private int radioButtonId;
    private Context context;

    public HomeActivityPreferences(Context context){
        this.context = context;
        this.selectedServices = new HashMap<>();
        this.nbPoints = DEFAULT_NBPOINTS;
        this.radioButtonId = DEFAULT_RB;
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

    public void changeSelectedRbFromPreferences(int radioButtonId){
        Log.d(TAG, "[save radio button] -" + radioButtonId);
        this.radioButtonId = radioButtonId;
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
        editor.putInt(SELECTED_RB, radioButtonId);
        editor.putInt(NB_POINTS, nbPoints);
        editor.commit();
    }

    @Override
    public void loadPreferences() {
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
        changeNbPointsFromPreferences(pref.getInt(NB_POINTS, DEFAULT_NBPOINTS));
        changeSelectedRbFromPreferences(pref.getInt(SELECTED_RB, DEFAULT_RB));
        Log.d(TAG, Integer.toString(pref.getInt(NB_POINTS, DEFAULT_NBPOINTS)));
    }

    public Map<String, String> getSelectedServices(){
        return selectedServices;
    }

    public int getNbPoints(){
        return nbPoints;
    }

    public int getRadioButtonId() {
        return radioButtonId;
    }
}