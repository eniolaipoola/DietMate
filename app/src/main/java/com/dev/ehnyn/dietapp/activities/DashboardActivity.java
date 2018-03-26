package com.dev.ehnyn.dietapp.activities;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.ehnyn.dietapp.R;
import com.dev.ehnyn.dietapp.fragments.BMICalculatorFragment;
import com.dev.ehnyn.dietapp.fragments.DietHistoryFragment;
import com.dev.ehnyn.dietapp.fragments.DietPlanFragment;
import com.dev.ehnyn.dietapp.fragments.HomeFragment;
import com.dev.ehnyn.dietapp.fragments.ProfileFragment;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   /* //declaring variable that identifies the current nav item.
    public static int navItemIndex = 0;

    //toolbar titles given to selected nav menu item
    private String[] activityTitles;

    //flag to load a default home activity when users presses back key
    private boolean loadHomeFragOnBackPressed = true;*/
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        handler = new Handler();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,  R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayView(R.id.nav_plan);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displayView(int viewId){
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch(viewId){
            case R.id.nav_plan:
                //returns diet plan fragment
                fragment = new DietPlanFragment();
                title = getString(R.string.plan);
                break;
            case R.id.nav_history:
                //returns BMI Calculator fragment
                fragment = new DietHistoryFragment();
                title = getString(R.string.history);
                break;
            case R.id.nav_bmi:
                //returns profile fragment
                fragment = new BMICalculatorFragment();
                title = getString(R.string.bmi_calculator);
                break;
            case R.id.nav_profile:
                // returns diet history fragment
                fragment = new ProfileFragment();
                title = getString(R.string.profile);
                break;
            default:
                new HomeFragment();
        }
        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();
        }

        //set the toolbar title
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
