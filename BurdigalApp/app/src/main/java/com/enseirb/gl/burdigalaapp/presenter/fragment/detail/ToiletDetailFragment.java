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
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.presenter.fragment.detail.listener.InteractionListener;
import com.enseirb.gl.burdigalaapp.model.service.Service;

public class ToiletDetailFragment extends android.support.v4.app.Fragment {
    private Button btnReturn;

    private TextView toiletAddr;
    private TextView toiletNeighbourhood;
    private TextView toiletType;

    private static final String SERVICE = "service";
    private static final String POSITION = "position";

    private Toilet toilet;
    private Service service;

    private OnFragmentInteractionListener mListener;
    private LinearLayout titleLayout;

    public static ToiletDetailFragment newInstance(Service service, int position) {
        Log.d("DetailToilet", "Les toilettes sont initialisées");
        ToiletDetailFragment fragment = new ToiletDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(SERVICE, service);
        args.putInt(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    public ToiletDetailFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            service = getArguments().getParcelable(SERVICE);
            int position = getArguments().getInt(POSITION, 0);
            toilet = mListener.getToilet(service, position);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toilet_details, container, false);
        btnReturn = (Button) view.findViewById(R.id.btn_return);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onButtonReturnClick();
            }
        });

        toiletAddr = (TextView) view.findViewById(R.id.tv_toilet_address);
        toiletAddr.setText(toilet.getAddress());

        toiletNeighbourhood = (TextView) view.findViewById(R.id.tv_toilet_neighbourhood);
        toiletNeighbourhood.append(" " + toilet.getNeighbourhood());

        toiletType = (TextView) view.findViewById(R.id.tv_toilet_type);
        toiletType.append(" " + toilet.getToiletType());

        titleLayout = (LinearLayout) view.findViewById(R.id.ll_focus);
        titleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFocusRequired(toilet, service);
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
        public Toilet getToilet(Service service, int position);
    }

}
