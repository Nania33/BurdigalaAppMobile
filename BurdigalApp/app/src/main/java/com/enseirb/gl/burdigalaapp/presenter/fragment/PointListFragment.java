package com.enseirb.gl.burdigalaapp.presenter.fragment;

import android.app.Activity;
import android.media.Image;
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
import android.widget.ListAdapter;
import android.widget.TextView;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.model.data.Model;
import com.enseirb.gl.burdigalaapp.presenter.service.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
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

    private AbsListView mListView;
    private ArrayAdapter mAdapter;

    public static PointListFragment newInstance(Service service) {
        Log.d(TAG, "Nouvelle instance: Service " + service);
        PointListFragment fragment = new PointListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SERVICE, service);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PointListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            service = getArguments().getParcelable(ARG_SERVICE);
        }

        if (mListener != null)
            modelList = new ArrayList<>(mListener.getDataListToDisplay(service));
        else
            modelList = new ArrayList<>();

        mAdapter = new ArrayAdapter<Model>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, modelList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onListItemClick(modelList.get(position).toString());
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onListItemClick(String id);
        public void onButtonReturnToMapClick();
        public List<Model> getDataListToDisplay(Service service);

        void onNextPressed();

        void onPreviousPressed();
    }

}
