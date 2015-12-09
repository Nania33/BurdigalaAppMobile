package com.enseirb.gl.burdigalaapp.presenter.fragment.detail;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.model.data.Parking;
import com.enseirb.gl.burdigalaapp.presenter.fragment.detail.listener.InteractionListener;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ParkingDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ParkingDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParkingDetailFragment extends android.support.v4.app.Fragment {
    private Button btnReturn;

    private TextView parkingName;
    private TextView parkingSpotNumber;
    private TextView parkingType;

    private static final String SERVICE = "service";
    private static final String POSITION = "position";

    private Parking parking;
    private Service service;

    private OnFragmentInteractionListener mListener;
    private LinearLayout titleLayout;

    public static ParkingDetailFragment newInstance(Service service, int position) {
        Log.d("DetailParking", "Les parking sont initialisés");
        ParkingDetailFragment fragment = new ParkingDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(SERVICE, service);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public ParkingDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            service = getArguments().getParcelable(SERVICE);
            int position = getArguments().getInt(POSITION, 0);
            parking = mListener.getParking(service, position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parking_details, container, false);
        btnReturn = (Button) view.findViewById(R.id.btn_return);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonReturnClick();
            }
        });

        parkingName = (TextView) view.findViewById(R.id.tv_parking_name);
        parkingName.setText(parking.getName());

        parkingSpotNumber = (TextView) view.findViewById(R.id.tv_parking_spot_number);
        parkingSpotNumber.append(" " + parking.getParkingSpotNumber());

        parkingType = (TextView) view.findViewById(R.id.tv_parking_type);
        parkingType.append(" " + parking.getParkingType());

        titleLayout = (LinearLayout) view.findViewById(R.id.ll_focus);
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFocusRequired(parking, service);
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

    public interface OnFragmentInteractionListener extends InteractionListener {
        public Parking getParking(Service service, int position);
    }

}
