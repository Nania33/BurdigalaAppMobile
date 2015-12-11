package com.enseirb.gl.burdigalaapp.presenter.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.enseirb.gl.burdigalaapp.presenter.preferences.HomeActivityPreferences;
import com.enseirb.gl.burdigalaapp.presenter.conf.Conf;
import com.enseirb.gl.burdigalaapp.model.service.ServiceFactory;
import com.enseirb.gl.burdigalaapp.model.service.ServiceType;
import com.enseirb.gl.burdigalaapp.model.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private HomeActivityPreferences homeActivityPreferences;
    private boolean firstTimeRun = true;

    private AbsListView mListView;
    private ListAdapter mAdapter;
    private Button btnStartMap;
    private Button btnNbPoints;

    private RadioGroup radioGroup;

    private EditText edNbPoints;

    private List<Integer> toInitialize;

    private ArrayList<Service> mItemsToDisplay;
    private List<Map<String, String>> mListOfChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toInitialize = new ArrayList<>();

        initializeHomeActivityPreferences();

        initializeListOfChoices();
        initializeBtnStart();
        initializeBtnNbPoints();
        initializeEdNbPoints();
        radioGroup = (RadioGroup) findViewById(R.id.rg_user_filter);

        loadHomeActivityPreferences();

        initializeListView();

        checkConnectivity();

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
        mAdapter = new SelectMultipleItemsAdapter(toInitialize, this, mListOfChoices , R.layout.item_list_home_activity,
                new String[] {Service.KEY_NAME}, new int[]{R.id.tv_item_name});

        mListView = (AbsListView) findViewById(android.R.id.list);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckBox checkBox = (CheckBox) view.findViewById(R.id.chk_bx_home_choice);
                Service item = mItemsToDisplay.get(position);
                if (item.isSelected()) {
                    item.unselect();
                    checkBox.setChecked(false);
                    view.setBackgroundResource(android.R.color.background_light);
                } else {
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

    private void loadHomeActivityPreferences() {
        homeActivityPreferences.loadPreferences();

        edNbPoints.setText(String.valueOf(homeActivityPreferences.getNbPoints()));

        Map<String,String> preferences = homeActivityPreferences.getSelectedServices();
        for(Map.Entry<String,String> preference : preferences.entrySet()){
            Log.d(TAG,"[loadHomeActivityPreferences] - selected - " + preference.getValue());

            for (int i=0; i < mItemsToDisplay.size() ; i++) {
                Service item = mItemsToDisplay.get(i);
                if(preference.getValue().equals(item.getName())){
                    item.select();
                    toInitialize.add(i);
                }
            }
        }
        radioGroup.check(homeActivityPreferences.getRadioButtonId());
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
                filterType = Conf.LINEAR_FILTER;
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

    public class SelectMultipleItemsAdapter extends SimpleAdapter {
        private LayoutInflater mInflater;
        private List<Integer> needToBeSelected;

        public SelectMultipleItemsAdapter(List<Integer> toSelect, Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            mInflater = LayoutInflater.from(context);
            this.needToBeSelected = new ArrayList<>(toSelect);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = mInflater.inflate(R.layout.item_list_home_activity, null);
                CheckBox cb = (CheckBox) convertView.findViewById(R.id.chk_bx_home_choice);

                for (Integer pos : needToBeSelected)
                    if (pos == position){
                        Service item = mItemsToDisplay.get(position);
                        if (item.isSelected()) {
                            Log.d(TAG, "initialize");
                            convertView.setBackgroundResource(item.getBackgroundColor());
                            cb.setChecked(true);
                        }
                    }
            }
            return super.getView(position, convertView, parent);
        }
    }
}
