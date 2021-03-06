package com.enseirb.gl.burdigalaapp.presenter.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.model.service.Service;

import java.util.ArrayList;
import java.util.List;


public class PointListFragment extends android.support.v4.app.Fragment implements AbsListView.OnItemClickListener {
    private static final String TAG = "PointListFragment";
    private static final String ARG_SERVICE = "service";

    private Button btnReturnToMap;
    private ImageButton btnNext;
    private ImageButton btnPrevious;
    private TextView serviceName;

    private List<Model> modelList;
    private Service service;

    private OnFragmentInteractionListener mListener;

    private ListView mListView;
    private ArrayAdapter mAdapter;

    public static PointListFragment newInstance(Service service) {
        Log.d(TAG, "Nouvelle instance: Service " + service);
        PointListFragment fragment = new PointListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SERVICE, service);
        fragment.setArguments(args);
        return fragment;
    }

    public PointListFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate listFragment : (init des données)" + service);


        if (getArguments() != null) {
            service = getArguments().getParcelable(ARG_SERVICE);
        }

        if (mListener != null)
            modelList = new ArrayList<>(mListener.getListOfPoints(service));
        else
            modelList = new ArrayList<>();

        mAdapter = new ArrayAdapter<Model>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, modelList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);

        mListView = (ListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

        btnReturnToMap = (Button) view.findViewById(R.id.btn_return_to_map);
        if (btnReturnToMap != null)
            btnReturnToMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onButtonReturnToMapClick();
                }
            });

        btnNext = (ImageButton) view.findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();;
            }
        });

        btnPrevious = (ImageButton) view.findViewById(R.id.btn_previous);
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previous();
            }
        });

        serviceName = (TextView) view.findViewById(R.id.service_name);
        serviceName.setText(service.getName());

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

    public void next() {
        if (mListener != null) {
            mListener.onNextPressed();
        }
    }

    public void previous() {
        if (mListener != null) {
            mListener.onPreviousPressed();
        }
    }

    public void update(){
        Log.d(TAG, "Update ");
        modelList.clear();
        populateList();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            mListener.onListItemClick(service, position);
        }
    }

    public interface OnFragmentInteractionListener {
        public void onListItemClick(Service service, int position);
        public void onButtonReturnToMapClick();
        public List<Model> getListOfPoints(Service service);

        void onNextPressed();

        void onPreviousPressed();
    }

    private void populateList(){
        if (mListener != null) {
            modelList.clear();
            modelList.addAll(mListener.getListOfPoints(service));
        } else {
            modelList.clear();
        }
    }

    public Service getService(){
        return this.service;
    }


}
