package com.enseirb.gl.burdigalaapp.presenter.fragment.detail;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.model.data.Garden;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GardenDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GardenDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GardenDetailFragment extends android.support.v4.app.Fragment {
    private Button btnReturn;
    private TextView textView;

    private static final String SERVICE = "service";
    private static final String POSITION = "position";

    private Garden garden;

    private OnFragmentInteractionListener mListener;

    public static GardenDetailFragment newInstance(Service service, int position) {
        Log.d("DetailGarden", "Les gardentes sont initialisés");
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
            Service service = getArguments().getParcelable(SERVICE);
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

        textView = (TextView) view.findViewById(R.id.tv_view);
        textView.setText(garden.toString());

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

    public interface OnFragmentInteractionListener {
        public void onButtonReturnClick();
        public Garden getGarden(Service service, int position);
    }

}
