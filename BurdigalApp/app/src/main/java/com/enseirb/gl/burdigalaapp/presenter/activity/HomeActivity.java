package com.enseirb.gl.burdigalaapp.presenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.exceptions.UnknownDataException;
import com.enseirb.gl.burdigalaapp.preferences.HomeActivityPreferences;
import com.enseirb.gl.burdigalaapp.presenter.adapter.SelectMultipleItemsAdapter;
import com.enseirb.gl.burdigalaapp.presenter.service.ServiceFactory;
import com.enseirb.gl.burdigalaapp.presenter.service.ServiceType;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";
    private final String blank = "";

    private AbsListView mListView;
    private ListAdapter mAdapter;
    private Button btnStartMap;

    private ArrayList<Service> mItemsToDiplay;
    private List<Map<String, String>> mListOfChoices;
    private HomeActivityPreferences homeActivityPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeListOfChoices();
        initializeHomeActivityPreferences();
        loadHomeActivityPreferences(mItemsToDiplay);

        btnStartMap = (Button) findViewById(R.id.btn_start_map_activity);
        btnStartMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMapsActivity(mItemsToDiplay);
            }
        });

        mAdapter = new SelectMultipleItemsAdapter(this, mListOfChoices , R.layout.item_list_home_activity,
                new String[] {Service.KEY_NAME}, new int[]{R.id.tv_item_name});

        mListView = (AbsListView) findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

    }

    private void initializeHomeActivityPreferences(){
        homeActivityPreferences = new HomeActivityPreferences(this);
    }

    private void initializeListOfChoices(){
        mItemsToDiplay = new ArrayList<>();
        for (ServiceType choice : ServiceType.values()) {
            try {
                mItemsToDiplay.add(ServiceFactory.makeChoice(choice));
            } catch (UnknownDataException ex) {
                System.out.println("Erreur de type : " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        mListOfChoices = new ArrayList<>();
        for (Service item : mItemsToDiplay)
            mListOfChoices.add(item.getMap());
    }

    private void startMapsActivity(ArrayList<Service> items){

        saveHomeActivityPreferences(items);

        Intent intent = MapsActivity.getIntent(this, items);
        startActivity(intent);
    }

    private void loadHomeActivityPreferences(ArrayList<Service> items) {
        homeActivityPreferences.loadPreferences();
        Map<String,String> preferences = homeActivityPreferences.getServices();
        for(Map.Entry<String,String> preference : preferences.entrySet()){
            Log.d(TAG,"[loadHomeActivityPreferences] - selected - " + preference.getValue());
            for(Service item : items){
                if(preference.getValue().equals(item.getName())){
                    item.select();
                }
                else{
                    item.unselect();
                }
            }
        }
    }

    private boolean noCheckBoxSelected(ArrayList<Service> items) {
        for(Service item : items){
            if(item.isSelected()){
                return false;
            }
        }
        return true;
    }

    private void saveHomeActivityPreferences(ArrayList<Service> items) {
        for(Service item : items){
            if(item.isSelected()){
                Log.d(TAG,"[isSelected] - " + item.getName() + " : "+  item.isSelected());
                homeActivityPreferences.addServiceToPreferences(item.getName(),item.getName());
            }
            else{
                homeActivityPreferences.removeServiceFromPreferences(item.getName());
            }
        }
        homeActivityPreferences.savePreferences();
    }

    public void checkBoxHandler(View v) {
        CheckBox checkBox = (CheckBox) v;
        int position = Integer.parseInt(checkBox.getTag().toString());

        View itemView = mListView.getChildAt(position).findViewById(R.id.layout_item_raw);

        Service item = mItemsToDiplay.get(position);

        if (checkBox.isChecked()) {
            item.select();
            itemView.setBackgroundResource(item.getBackgroundColor());
        } else {
            item.unselect();
            itemView.setBackgroundResource(android.R.color.background_light);
        }
    }
}
