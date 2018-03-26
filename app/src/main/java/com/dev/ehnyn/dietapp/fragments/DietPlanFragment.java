package com.dev.ehnyn.dietapp.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.ehnyn.dietapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

public class DietPlanFragment extends Fragment {
    public static final String LOG_TAG = "Log Tag";
    String nameDefault = "NA";
    private SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        final View rootView = inflater.inflate(R.layout.fragment_diet_plan, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("my_prefs",0);
        final String firstname = sharedPreferences.getString("pass",nameDefault);
        Log.d(LOG_TAG, firstname);

        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        final TextView breakfastField = rootView.findViewById(R.id.tvBreakfast);
        final TextView launchField = rootView.findViewById(R.id.tvLaunch);
        final TextView dinnerField = rootView.findViewById(R.id.tvDinner);
        final TextView tipField = rootView.findViewById(R.id.tvTip);
        final TextView nameField = rootView.findViewById(R.id.tvName);
        //final TextView

        final String url = "https://dietproper.herokuapp.com/inference/diet-plan?firstname=" + firstname;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.e(LOG_TAG, "It got here");
                    JSONObject apiResponse = new JSONObject(response);
                    Log.e(LOG_TAG, "The api response is " + apiResponse);
                    JSONArray data = apiResponse.getJSONArray("data");
                    Log.e(LOG_TAG, "Bmi message is " + data);

                    JSONObject jsonObject = data.optJSONObject(0);
                    Log.e("JSONObject", jsonObject.toString());
                    String id = jsonObject.getString("id");
                    String name = jsonObject.getString("name");
                    String tips = jsonObject.getString("tips");

                    String breakfast = jsonObject.getString("breakfast");
                    String launch = jsonObject.getString("launch");
                    String dinner = jsonObject.getString("dinner");

                    tipField.setText(tips);
                    nameField.setText(name);
                    breakfastField.setText(breakfast);
                    launchField.setText(launch);
                    dinnerField.setText(dinner);

                }catch (Exception e){
                    throw  new RuntimeException("An internal JSON Error occurred, please try again");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(rootView.getContext(), "An error occured", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
        return rootView;
    }
}
