package com.enseirb.gl.burdigalaapp.presenter.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.exceptions.UnknownDataException;
import com.enseirb.gl.burdigalaapp.presenter.adapter.SelectMultipleItemsAdapter;
import com.enseirb.gl.burdigalaapp.presenter.service.ServiceFactory;
import com.enseirb.gl.burdigalaapp.presenter.service.ServiceType;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private AbsListView mListView;
    private ListAdapter mAdapter;
    private Button btnStartMap;

    private ArrayList<Service> mItemsToDiplay;
    private List<Map<String, String>> mListOfChoices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeListOfChoices();

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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Service item = mItemsToDiplay.get(position);
                if (item.isSelected()) {
                    item.unselect();
                    view.setBackgroundResource(android.R.color.background_light);
                } else {
                    item.select();
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
        Intent intent = MapsActivity.getIntent(this, items);
        startActivity(intent);
    }
}
