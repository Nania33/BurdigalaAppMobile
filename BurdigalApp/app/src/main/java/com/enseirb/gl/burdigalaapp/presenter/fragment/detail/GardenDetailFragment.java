package com.enseirb.gl.burdigalaapp.presenter.fragment.detail;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.presenter.fragment.detail.listener.InteractionListener;
import com.enseirb.gl.burdigalaapp.model.service.Service;

public class GardenDetailFragment extends android.support.v4.app.Fragment {
    private Button btnReturn;

    private TextView gardenLabel;
    private TextView gardenName;
    private TextView gardenType;
    private TextView gardenGestionType;
    private TextView gardenUse;

    private static final String SERVICE = "service";
    private static final String POSITION = "position";

    private Garden garden;
    private Service service;

    private OnFragmentInteractionListener mListener;
    private LinearLayout titleLayout;

    public static GardenDetailFragment newInstance(Service service, int position) {
        GardenDetailFragment fragment = new GardenDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(SERVICE, service);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public GardenDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            service = getArguments().getParcelable(SERVICE);
            int position = getArguments().getInt(POSITION, 0);
            garden = mListener.getGarden(service, position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_garden_details, container, false);
        btnReturn = (Button) view.findViewById(R.id.btn_return);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonReturnClick();
            }
        });

        gardenName = (TextView) view.findViewById(R.id.tv_garden_name);
        gardenName.setText(garden.getName());

        gardenType = (TextView) view.findViewById(R.id.tv_garden_type);
        gardenType.append(" " + garden.getParcType());

        gardenGestionType = (TextView) view.findViewById(R.id.tv_garden_gestion_type);
        gardenGestionType.append(" " + garden.getGestionType());

        gardenLabel = (TextView) view.findViewById(R.id.tv_garden_label);
        gardenLabel.append(" " + garden.getLabel());

        gardenUse = (TextView) view.findViewById(R.id.tv_garden_use);
        gardenUse.append(" " + garden.getUse());

        titleLayout = (LinearLayout) view.findViewById(R.id.ll_focus);
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFocusRequired(garden, service);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener extends InteractionListener{
        public Garden getGarden(Service service, int position);
    }

}
