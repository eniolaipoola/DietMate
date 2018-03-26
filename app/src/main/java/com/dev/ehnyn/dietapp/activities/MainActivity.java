package com.dev.ehnyn.dietapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.dev.ehnyn.dietapp.R;

public class MainActivity extends AppCompatActivity {
    private static int TIME_OUT = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent formIntent = new Intent(MainActivity.this, UserDetailsActivity.class);
                startActivity(formIntent);
                finish();
            }
        }, TIME_OUT);
    }
}
