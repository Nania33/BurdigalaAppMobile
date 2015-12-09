package com.enseirb.gl.burdigalaapp.presenter.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.opengl.ETC1;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.exceptions.UnknownDataException;
import com.enseirb.gl.burdigalaapp.preferences.HomeActivityPreferences;
import com.enseirb.gl.burdigalaapp.presenter.adapter.SelectMultipleItemsAdapter;
import com.enseirb.gl.burdigalaapp.presenter.conf.Conf;
import com.enseirb.gl.burdigalaapp.presenter.service.ServiceFactory;
import com.enseirb.gl.burdigalaapp.presenter.service.ServiceType;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private HomeActivityPreferences homeActivityPreferences;

    private AbsListView mListView;
    private ListAdapter mAdapter;
    private Button btnStartMap;
    private Button btnNbPoints;

    private RadioGroup radioGroup;

    private EditText edNbPoints;

    private ArrayList<Service> mItemsToDiplay;
    private List<Map<String, String>> mListOfChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeListOfChoices();

        initializeHomeActivityPreferences();

        btnStartMap = (Button) findViewById(R.id.btn_start_map_activity);
        btnStartMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMapsActivity(mItemsToDiplay);
            }
        });

        btnNbPoints = (Button) findViewById(R.id.btn_modify_nb_points);
        btnNbPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        edNbPoints = (EditText) findViewById(R.id.ed_nb_points);
        edNbPoints.setRawInputType(InputType.TYPE_CLASS_NUMBER);

        radioGroup = (RadioGroup) findViewById(R.id.rg_user_filter);

        mAdapter = new SimpleAdapter(this, mListOfChoices , R.layout.item_list_home_activity,
                new String[] {Service.KEY_NAME}, new int[]{R.id.tv_item_name});

        mListView = (AbsListView) findViewById(android.R.id.list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG,"itemClicked");
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.chk_bx_home_choice);
                Service item = mItemsToDiplay.get(position);
                if (item.isSelected()) {
                    Log.d(TAG,"itemUnselected");
                    item.unselect();
                    checkBox.setChecked(false);
                    view.setBackgroundResource(android.R.color.background_light);
                } else {
                    Log.d(TAG,"itemSelected");
                    item.select();
                    checkBox.setChecked(true);
                    view.setBackgroundResource(item.getBackgroundColor());
                }
            }
        });
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage(R.string.error_network_unavailable)
                    .setTitle(R.string.error_network_title);
            builder.create().show();
        }

        loadHomeActivityPreferences(mItemsToDiplay);
    }

    private void initializeRadioGroupe(){

    }

    private void initializeHomeActivityPreferences(){
        homeActivityPreferences = new HomeActivityPreferences(this);
    }

    private void loadHomeActivityPreferences(ArrayList<Service> items) {
        homeActivityPreferences.loadPreferences();
        Map<String,String> preferences = homeActivityPreferences.getServices();
        for(Map.Entry<String,String> preference : preferences.entrySet()){
            Log.d(TAG,"[loadHomeActivityPreferences] - selected - " + preference.getValue());
            for(Service item : items){
                if(preference.getValue().equals(item.getName())){
                    Log.d(TAG, "itemSelectedFromSharedPreferences");
                    setItemSelected(item);
                }
                else{
                    item.unselect();
                }
            }
        }
    }

    private void setItemSelected(Service item){
        for (int position =0; position < mListOfChoices.size(); position++){
            if (mItemsToDiplay.get(position).getType().equals(item.getType())){
                Log.d(TAG, "[setItemSelected] - selected - " + position);
                final int finalPosition = position;
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mListView.performItemClick(
                                mListView.getAdapter().getView(finalPosition, null, null),
                                finalPosition,
                                mListView.getItemIdAtPosition(finalPosition));
                    }
                });

            }
        }
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

        int nbPoints = Integer.valueOf(edNbPoints.getText().toString());
        String filterType;

        int selectedRadioGroup = radioGroup.getCheckedRadioButtonId();
        switch (selectedRadioGroup) {
            case R.id.rb_around:
                filterType = Conf.NEAREST_FILTER;
                break;
            case R.id.rb_bdx_center:
                filterType = Conf.LINEAR_FILTER;
                break;
            default:
                filterType = Conf.NEAREST_FILTER;
                break;
        }
        Intent intent = MapsActivity.getIntent(this, items, filterType, nbPoints);
        startActivity(intent);
    }
}
