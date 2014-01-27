package com.chanakinllc.thousandmiles;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * Activities containing this fragment MUST implement the {@link MainMenuListFragment.MainMenuItemListener}
 * interface.
 */
public class MainMenuListFragment extends Fragment implements AbsListView.OnItemClickListener {

    private static final String ITEMS_KEY = "listItems";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<ThousandMilesMenuItem> items;
    private String mParam2;

    private MainMenuItemListener mListener;

    private AbsListView mListView;

    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static MainMenuListFragment newInstance(ArrayList<String> items) {
        MainMenuListFragment fragment = new MainMenuListFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ITEMS_KEY, items);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainMenuListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            ArrayList<String> itemStrings = getArguments().getStringArrayList(ITEMS_KEY);
            items = new ArrayList<ThousandMilesMenuItem>();

            for( String text: itemStrings ) {
                items.add(new ThousandMilesMenuItem(text));
            }
        }

        mAdapter = new ArrayAdapter<ThousandMilesMenuItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, items);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainmenuitem, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (MainMenuItemListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement MainMenuItemListener");
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
            mListener.onItemClicked(items.get(position));
        }
    }

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    */
    public interface MainMenuItemListener {
        public void onItemClicked(ThousandMilesMenuItem item);
    }

}
