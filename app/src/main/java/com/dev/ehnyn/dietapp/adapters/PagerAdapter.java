package com.dev.ehnyn.dietapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.dev.ehnyn.dietapp.fragments.BMIFragment;
import com.dev.ehnyn.dietapp.fragments.ActivityLevelFragment;
import com.dev.ehnyn.dietapp.fragments.IdealWeightFragment;

// Since this is an object collection, use a FragmentStatePagerAdapter,
// and NOT a FragmentPagerAdapter.
public class PagerAdapter extends FragmentStatePagerAdapter {

    int noOfFrag;
    public PagerAdapter(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfFrag  = noOfTabs;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                BMIFragment tab1 = new BMIFragment();
                return tab1;
            case 1:
                ActivityLevelFragment tab2 = new ActivityLevelFragment();
                return tab2;
            case 2:
                IdealWeightFragment tab4 = new IdealWeightFragment();
                return  tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return  noOfFrag;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}


