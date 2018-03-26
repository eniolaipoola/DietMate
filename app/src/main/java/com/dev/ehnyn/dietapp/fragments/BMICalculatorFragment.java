package com.dev.ehnyn.dietapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.dev.ehnyn.dietapp.R;


public class BMICalculatorFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(R.layout.fragment_bmi_calc, container, false);

        EditText editText1 = rootView.findViewById(R.id.edt1);
        EditText editText2 = rootView.findViewById(R.id.edt2);

        String height = editText1.getText().toString();
        String weight = editText2.getText().toString();


        return rootView;
    }
}
