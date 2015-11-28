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
import android.widget.ListAdapter;
import android.widget.TextView;

import com.enseirb.gl.burdigalaapp.R;
import com.enseirb.gl.burdigalaapp.business.GardenBusiness;
import com.enseirb.gl.burdigalaapp.business.IGardenBusiness;
import com.enseirb.gl.burdigalaapp.business.listener.IGardenBusinessListener;
import com.enseirb.gl.burdigalaapp.model.Garden;
import com.enseirb.gl.burdigalaapp.presenter.fragment.dummy.DummyContent;

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

    private Button btnReturnToMap;
    private IGardenBusiness gardenBusiness;
    private List<Garden> gardenList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private AbsListView mListView;
    private ArrayAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static PointListFragment newInstance(String param1, String param2) {
        PointListFragment fragment = new PointListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        initializeBusiness();
        gardenList = new ArrayList<>();
        retrieveGardenPlaces();

        mAdapter = new ArrayAdapter<Garden>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, gardenList);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onListItemClick(gardenList.get(position).toString());
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
        // TODO: Update argument type and name
        public void onListItemClick(String id);
        public void onButtonReturnToMapClick();
    }

    private void initializeBusiness(){
        gardenBusiness = new GardenBusiness();
    }

    private void retrieveGardenPlaces(){
        Log.d(TAG, "[retrieveGardenPlaces()] - start");
        gardenBusiness.retrieveGardenPlaces(new IGardenBusinessListener() {
            @Override
            public void onSuccess(List<Garden> garden) {
                gardenList.addAll(garden);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onError(String message) {
                //TODO afficher boite de dialogue avec message
            }
        });
        Log.d(TAG, "[retrieveGardenPlaces()] - end");
    }

}
