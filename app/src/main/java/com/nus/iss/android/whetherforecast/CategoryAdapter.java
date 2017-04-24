package com.nus.iss.android.whetherforecast;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v13.app.FragmentPagerAdapter;


/**
 * Created by Ritesh on 4/22/2017.
 */

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context context;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Today's";
        }
        else if (position == 1) {
            return "Forecast";
        }else {
            return "Location";
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new CurrentFragment();
        } else if (position == 1) {
            return new ForcastFragment();
        } else {
            return new LocationFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
