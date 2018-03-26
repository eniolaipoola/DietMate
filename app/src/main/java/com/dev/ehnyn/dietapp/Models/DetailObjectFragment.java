package com.dev.ehnyn.dietapp.Models;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.ehnyn.dietapp.R;

// Instances of this class are fragments representing a single object in our collection.
public class DetailObjectFragment extends Fragment {
    public static final String ARGS_OBJECT = "object";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collection_object, container, false);

        Bundle args = getArguments();
        ((TextView) rootView.findViewById(android.R.id.text1)).setText(Integer.toString(args.getInt(ARGS_OBJECT)));
        return rootView;
    }
}
