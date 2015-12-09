package com.enseirb.gl.burdigalaapp.presenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleAdapter;

import com.enseirb.gl.burdigalaapp.R;

import java.util.List;
import java.util.Map;

/**
 * Created by rchabot on 21/11/15.
 */
public class SelectMultipleItemsAdapter extends SimpleAdapter {
    private LayoutInflater mInflater;

    public SelectMultipleItemsAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_list_home_activity, null);
        }
        return super.getView(position, convertView, parent);
    }
}
