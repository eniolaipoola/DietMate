package com.dev.ehnyn.dietapp.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.ehnyn.dietapp.R;
import com.dev.ehnyn.dietapp.activities.DashboardActivity;
import com.dev.ehnyn.dietapp.activities.SummaryActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IdealWeightFragment  extends Fragment {

    public static final String LOG_TAG = "Log Tag";
    public int sex_id;

    String nameDefault = "NA";
    private SharedPreferences sharedPreferences ;
    //SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(R.layout.fragment_ideal_weight, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("my_prefs",0);
        final String firstname = sharedPreferences.getString("pass",nameDefault);
        Log.e(LOG_TAG, firstname);

        final EditText ageField = rootView.findViewById(R.id.etAge);

        final Spinner spinner1 = (Spinner) rootView.findViewById(R.id.sp1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource
                (this.getActivity(), R.array.sex, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter);

        String text = spinner1.getSelectedItem().toString();
        if(text == "Male"){
            sex_id = 0;
        } else if (text == "Female"){
            sex_id = 1;
        }


        Spinner spinner2 = (Spinner) rootView.findViewById(R.id.sp2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource
                (this.getActivity(), R.array.health_history, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter2);

        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        final Button nextButton = rootView.findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String age = ageField.getText().toString();
                final String url = "https://dietproper.herokuapp.com/ideal-weight/ideal-weight?firstname=" + firstname;
                Log.e(LOG_TAG, "FIRSTNAME IS"  + firstname);
                Log.e(LOG_TAG, "url value is " + url);
                Log.e(LOG_TAG, "spinner value is " + sex_id );
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.e(LOG_TAG, "It got here");
                            JSONObject apiResponse = new JSONObject(response);
                            Log.e(LOG_TAG, "The api response is " + apiResponse);
                            String data = apiResponse.getString("data");
                            Toast.makeText(nextButton.getContext(), "The ideal body weight for you is " + data, Toast.LENGTH_LONG).show();
                            Intent dashIndex = new Intent(getActivity(), SummaryActivity.class);
                            startActivity(dashIndex);
                        }catch (Exception e){
                            throw  new RuntimeException("A network error occurred, please try again");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(nextButton.getContext(), "An internal error occurred", Toast.LENGTH_LONG).show();
                    }
                })
                {
                    //post parameters
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("sex_id", String.valueOf(sex_id));
                        params.put("age", age);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });

        return rootView;
    }
}