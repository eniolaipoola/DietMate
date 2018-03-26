package com.dev.ehnyn.dietapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.ehnyn.dietapp.OnDataSentListener;
import com.dev.ehnyn.dietapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class BMIFragment extends Fragment {

    public static final String LOG_TAG = "Log Tag";
    private SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(R.layout.fragment_bmi, container, false);

        final RequestQueue queue = Volley.newRequestQueue(getActivity());

        final EditText nameField = rootView.findViewById(R.id.edtName);
        final EditText weightField = rootView.findViewById(R.id.edtWeight);
        final EditText heightField = rootView.findViewById(R.id.edtHeight);

        final Button submitButton = rootView.findViewById(R.id.submitBMI);
        sharedPreferences = this.getActivity().getSharedPreferences("my_prefs",0);
        editor = sharedPreferences.edit();

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final String url = "https://dietproper.herokuapp.com/bmi/calc-bmi";

                final String firstname = nameField.getText().toString();
                final String weight = weightField.getText().toString();
                final String height = heightField.getText().toString();
                Log.e(LOG_TAG, "firstname " + firstname);
                Log.e(LOG_TAG, "weight " + weight);
                Log.e(LOG_TAG, "height " + height);

               /* Bundle bundle = new Bundle();
                bundle.putString("pass", firstname);
                editor.putString("pass", firstname);
                editor.commit();*/


                if(firstname.isEmpty() || height.isEmpty() || weight.isEmpty()){
                    Toast.makeText(submitButton.getContext(), "Please fill all the fields above", Toast.LENGTH_LONG).show();
                    return;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            Log.e(LOG_TAG, "It got here");
                            JSONObject apiResponse = new JSONObject(response);
                            Log.e(LOG_TAG, "The api response is " + apiResponse);
                            String message = apiResponse.getString("message");
                            Log.e(LOG_TAG, "Bmi message is " + message);
                            double bmiValue = apiResponse.getDouble("data");
                            double bmi = Math.round(bmiValue);
                            Log.e(LOG_TAG, "Value is " + bmi);
                            Toast.makeText(submitButton.getContext(), "Your bmi is " + bmiValue, Toast.LENGTH_LONG).show();
                        }catch (Exception e){
                            throw  new RuntimeException("An internal JSON Error occurred, please try again");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(submitButton.getContext(), "An internet error occurred", Toast.LENGTH_SHORT).show();
                    }
                })
                {
                    //post parameters
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("firstname", firstname);
                        params.put("height", height);
                        params.put("weight", weight);

                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
        return rootView;
    }

}
