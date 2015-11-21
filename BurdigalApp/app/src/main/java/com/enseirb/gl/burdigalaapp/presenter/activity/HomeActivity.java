package com.enseirb.gl.burdigalaapp.presenter.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.presenter.adapter.SelectMultipleItemsAdapter;
import com.enseirb.gl.burdigalaapp.presenter.item.DataItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private AbsListView mListView;
    private ListAdapter mAdapter;
    private Button btnStartMap;

    private List<DataItem> mItemsToDiplay;
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
                new String[] {DataItem.KEY_NAME}, new int[]{R.id.tv_item_name});

        mListView = (AbsListView) findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
    }



    private void initializeListOfChoices(){
        mItemsToDiplay = new ArrayList<>();
        mItemsToDiplay.add(new DataItem("Parcs et jardins", "parcs et jardins dans bordeaux"));
        mItemsToDiplay.add(new DataItem("Toilettes", "Toilettes publiques"));
        mItemsToDiplay.add(new DataItem("Parking deux roues", "Parking pour vélo et mobilette"));
        mItemsToDiplay.add(new DataItem("Parking", "Parking pour voitures"));

        mListOfChoices = new ArrayList<>();
        for (DataItem item : mItemsToDiplay)
            mListOfChoices.add(item.getMap());
    }

    private void startMapsActivity(List<DataItem> items){
        Intent intent = MapsActivity.getIntent(this, items);
        for (DataItem item : items)
            Log.d(TAG, item.toString());
        startActivity(intent);
    }

    public void checkBoxHandler(View v) {
        CheckBox checkBox = (CheckBox) v;
        int position = Integer.parseInt(checkBox.getTag().toString());

        View itemView = mListView.getChildAt(position).findViewById(R.id.layout_item_raw);

        DataItem item = mItemsToDiplay.get(position);

        if (checkBox.isChecked()) {
            item.select();
            switch (position){
                case 0:
                    itemView.setBackgroundResource(R.color.blue);
                    break;
                case 1:

                    itemView.setBackgroundResource(R.color.green);
                    break;
                case 2:
                    itemView.setBackgroundResource(R.color.orange);
                    break;
                case 3:
                    itemView.setBackgroundResource(R.color.red);
                    break;
                default:
                    throw new IndexOutOfBoundsException();
            }
        } else {
            item.unselect();
            itemView.setBackgroundResource(android.R.color.background_light);
        }
    }

}
