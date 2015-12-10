package com.enseirb.gl.burdigalaapp.presenter.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.exceptions.UnknownDataException;
import com.enseirb.gl.burdigalaapp.preferences.HomeActivityPreferences;
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

    private ArrayList<Service> mItemsToDisplay;
    private List<Map<String, String>> mListOfChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeListOfChoices();
        initializeHomeActivityPreferences();
        initializeBtnStart();
        initializeBtnNbPoints();
        initializeEdNbPoints();
        radioGroup = (RadioGroup) findViewById(R.id.rg_user_filter);
        initializeListView();

        checkConnectivity();

        loadHomeActivityPreferences(mItemsToDisplay);
    }

    private void initializeEdNbPoints() {
        edNbPoints = (EditText) findViewById(R.id.ed_nb_points);
        edNbPoints.setInputType(InputType.TYPE_NULL);
        edNbPoints.setFocusable(false);
        edNbPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker();
            }
        });
    }

    private void checkConnectivity() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage(R.string.error_network_unavailable)
                    .setTitle(R.string.error_network_title);
            builder.create().show();
        }
    }

    private void initializeListView() {
        mAdapter = new SimpleAdapter(this, mListOfChoices , R.layout.item_list_home_activity,
                new String[] {Service.KEY_NAME}, new int[]{R.id.tv_item_name});

        mListView = (AbsListView) findViewById(android.R.id.list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "itemClicked");
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.chk_bx_home_choice);
                Service item = mItemsToDisplay.get(position);
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
    }

    private void initializeBtnNbPoints() {
        btnNbPoints = (Button) findViewById(R.id.btn_modify_nb_points);
        btnNbPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberPicker();
            }
        });
    }

    private void initializeBtnStart() {
        btnStartMap = (Button) findViewById(R.id.btn_start_map_activity);
        btnStartMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMapsActivity(mItemsToDisplay);
            }
        });
    }

    private void initializeHomeActivityPreferences(){
        homeActivityPreferences = new HomeActivityPreferences(this);
    }

    private void loadHomeActivityPreferences(ArrayList<Service> items) {
        homeActivityPreferences.loadPreferences();

        edNbPoints.setText(String.valueOf(homeActivityPreferences.getNbPoints()));

        Map<String,String> preferences = homeActivityPreferences.getSelectedServices();
        for(Map.Entry<String,String> preference : preferences.entrySet()){
            Log.d(TAG,"[loadHomeActivityPreferences] - selected - " + preference.getValue());
            for(Service item : items){
                if(preference.getValue().equals(item.getName())){
                    setItemSelected(item);
                }
                else{
                    item.unselect();
                }
            }
        }
        radioGroup.check(homeActivityPreferences.getRadioButtonId());
    }

    private void setItemSelected(final Service item){
        for (int position = 0; position < mListOfChoices.size(); position++){
            if (mItemsToDisplay.get(position).getType().equals(item.getType())){
                final int finalPosition = position;
                final int color = item.getBackgroundColor();
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        View itemView = mListView.getChildAt(finalPosition);
                        CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.chk_bx_home_choice);
                        checkBox.setChecked(true);
                        itemView.setBackgroundResource(color);
                        item.select();
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
        homeActivityPreferences.changeNbPointsFromPreferences(Integer.parseInt(edNbPoints.getText().toString()));
        homeActivityPreferences.changeSelectedRbFromPreferences(radioGroup.getCheckedRadioButtonId());
        homeActivityPreferences.savePreferences();
    }

    private void initializeListOfChoices(){
        mItemsToDisplay = new ArrayList<>();
        for (ServiceType choice : ServiceType.values()) {
            try {
                mItemsToDisplay.add(ServiceFactory.makeChoice(choice));
            } catch (UnknownDataException ex) {
                ex.printStackTrace();
            }
        }

        mListOfChoices = new ArrayList<>();
        for (Service item : mItemsToDisplay)
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

    private void showNumberPicker(){
        final Dialog d = new Dialog(this);
        d.setTitle(getResources().getString(R.string.dialog_nb_points_title));
        d.setContentView(R.layout.dialog_number_picker);
        Button positiveButton = (Button) d.findViewById(R.id.positiveButton);
        Button negativeButton = (Button) d.findViewById(R.id.negativeButton);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        np.setMaxValue(100);
        np.setMinValue(0);
        np.setValue(Integer.valueOf(edNbPoints.getText().toString()));

        positiveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                edNbPoints.setText(String.valueOf(np.getValue()));
                d.dismiss();
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();
    }
}
