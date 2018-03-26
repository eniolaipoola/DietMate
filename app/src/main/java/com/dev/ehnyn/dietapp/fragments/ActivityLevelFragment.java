package com.dev.ehnyn.dietapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.ehnyn.dietapp.R;

import org.json.JSONObject;


public class ActivityLevelFragment extends Fragment {

    public static final String LOG_TAG = "Log Tag";
    int id;

    String nameDefault = "NA";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(
                R.layout.fragment_activity_level, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("my_prefs",0);
        final String firstname = sharedPreferences.getString("pass",nameDefault);
        Log.d(LOG_TAG, firstname);

        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        final CheckBox sedentaryField = rootView.findViewById(R.id.cbSedentary);
        final  CheckBox moderateField = rootView.findViewById(R.id.cbModerate);
        final  CheckBox vigorousField = rootView.findViewById(R.id.cbVigorous);

        final Button submitButton = rootView.findViewById(R.id.submitAL);
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                switch (view.getId()){
                    case R.id.submitAL:
                        if(sedentaryField.isChecked()){
                            sedentaryField.setChecked(true);
                             id = 1;
                        } else if(moderateField.isChecked()){
                            moderateField.setChecked(true);
                             id = 2;
                        } else if(vigorousField.isChecked()){
                            vigorousField.setChecked(true);
                             id = 3;
                        }
                }
                final String url = "https://dietproper.herokuapp.com/activity-level/activity?firstname=" + firstname + "&id=" + id;
                Log.e(LOG_TAG, "url value is " + url);
                Log.e(LOG_TAG, "checkbox value is " + id);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.e(LOG_TAG, "It got here");
                            JSONObject apiResponse = new JSONObject(response);
                            Log.e(LOG_TAG, "aPI RESPONSE IS " + apiResponse);
                            String value = apiResponse.getString("data");
                            Log.e(LOG_TAG, "Your activity level is " + value);
                            Toast.makeText(submitButton.getContext(), "Your activity level is " + value, Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            throw  new RuntimeException("A network error occurred, please try again");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(submitButton.getContext(), "An internal error occurred", Toast.LENGTH_LONG).show();
                    }
                });
                queue.add(stringRequest);
            }
        });

        return rootView;
    }
}