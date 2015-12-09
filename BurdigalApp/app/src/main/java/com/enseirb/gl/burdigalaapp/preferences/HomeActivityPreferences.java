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
    final private String TAG = "HomeActivityPreferences";
    final private String keys[] = {"Toilettes","Parking","Parcs et jardins","Parking deux roues"};
    HashMap<String,String> services;
    Context context;

    public HomeActivityPreferences(Context context){
        this.context = context;
        this.services = new HashMap<>();
    }

    public void addServiceToPreferences(String key, String service){
        services.put(key, service);
    }

    public void removeServiceFromPreferences(String name){
        services.remove(name);
    }

    @Override
    public void savePreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        for(int i = 0 ; i < keys.length ; ++i){
            editor.remove(keys[i]);
        }
        for(Map.Entry<String,String> entry : services.entrySet()){
            Log.d(TAG, "[save] -" + entry.getKey());
            editor.putString(entry.getKey(), entry.getValue());
        }
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
                if(!services.containsValue(keys[i])){
                    Log.d(TAG,"[load] - add - " + keys[i]);
                    addServiceToPreferences(keys[i], keys[i]);
                }
            }
            else{
                if(services.containsValue(keys[i])){
                    Log.d(TAG,"[load] - remove - " + keys[i]);
                    addServiceToPreferences(keys[i], keys[i]);
                }
            }
        }
    }

    public Map getServices(){
        return services;
    }
}
