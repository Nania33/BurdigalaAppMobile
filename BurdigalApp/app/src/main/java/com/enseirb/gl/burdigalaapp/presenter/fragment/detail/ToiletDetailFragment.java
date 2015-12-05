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
import com.enseirb.gl.burdigalaapp.model.data.Toilet;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ToiletDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ToiletDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToiletDetailFragment extends android.support.v4.app.Fragment {
    private Button btnReturn;
    private TextView textView;

    private static final String SERVICE = "service";
    private static final String POSITION = "position";

    private Toilet toilet;

    private OnFragmentInteractionListener mListener;

    public static ToiletDetailFragment newInstance(Service service, int position) {
        Log.d("DetailToilet", "Les toilettes sont initialisés");
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
            Service service = getArguments().getParcelable(SERVICE);
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

        textView = (TextView) view.findViewById(R.id.tv_view);
        textView.setText(toilet.toString());

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
        public Toilet getToilet(Service service, int position);
    }

}
