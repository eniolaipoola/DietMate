package com.dev.ehnyn.dietapp.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.ehnyn.dietapp.R;

import org.json.JSONObject;

public class SummaryActivity extends AppCompatActivity {

    public static final String LOG_TAG = "Log Tag";

    String nameDefault = "NA";
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_page);

        final Button homeView = (Button) findViewById(R.id.next2);
        final TextView resultView = (TextView) findViewById(R.id.resultView);
        sharedPreferences = this.getSharedPreferences("my_prefs",0);
        final String firstname = sharedPreferences.getString("pass",nameDefault);
        Log.d(LOG_TAG, firstname);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "https://dietproper.herokuapp.com/inference/inference?firstname=" + firstname;
        Log.e(LOG_TAG, "url value is " + url);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    Log.e(LOG_TAG, "It got here");
                    JSONObject apiResponse = new JSONObject(response);
                    Log.e(LOG_TAG, "aPI RESPONSE IS " + apiResponse);
                    String value = apiResponse.getString("data");
                    resultView.setText(value);
                    Toast.makeText(homeView.getContext(), "Your Details: " + value , Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    throw  new RuntimeException("A network error occurred, please try again");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(homeView.getContext(), "An internal error occurred", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(stringRequest);

        homeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Welcome to dashboard", Toast.LENGTH_SHORT).show();
                Intent homeIntent = new Intent(SummaryActivity.this, DashboardActivity.class);
                startActivity(homeIntent);
            }
        });
    }
}
