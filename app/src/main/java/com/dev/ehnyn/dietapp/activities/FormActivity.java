package com.dev.ehnyn.dietapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.ehnyn.dietapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FormActivity  extends AppCompatActivity {

    public static final String LOG_TAG = "MY_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        //Form fields content
        final EditText emailField = findViewById(R.id.edtEmail);
        final EditText passField = findViewById(R.id.edtPass);
        final Button submitButton = findViewById(R.id.submit);

        final RequestQueue queue = Volley.newRequestQueue(this);


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String url = "https://dietproper.herokuapp.com/user/create";
                final String email = emailField.getText().toString();
                final String password = passField.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(submitButton.getContext(), "Email or Password cannot be empty ", Toast.LENGTH_LONG).show();
                    return;
                }

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject data = new JSONObject(response);
                            Log.e(LOG_TAG, "the response message from api is " + data);

                            String message = data.getString("message");
                            if(message == null){
                                Toast.makeText(submitButton.getContext(), "An error occured, please check your internet connection.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            Toast.makeText(submitButton.getContext(), message, Toast.LENGTH_LONG ).show();
                            Intent detailsIntent = new Intent(FormActivity.this, UserDetailsActivity.class);
                            startActivity(detailsIntent);

                            JSONObject showdata = data.getJSONObject("data");
                            Log.e(LOG_TAG, "Data in the json response from the api is " + showdata);

                        } catch (Exception e){
                            throw new RuntimeException("An internal error occured, please try again later");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(submitButton.getContext(), "An error occured", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    //post parameters
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", email);
                        params.put("password", password);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}