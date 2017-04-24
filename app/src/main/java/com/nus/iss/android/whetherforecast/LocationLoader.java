package com.nus.iss.android.whetherforecast;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;
import java.util.TreeMap;

/**
 * Created by Ritesh on 4/23/2017.
 */

public class LocationLoader extends AsyncTaskLoader<TreeMap<String,List<String>>> {

    //private String murl="https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json";
    private String murl;

    public LocationLoader(Context context, String url) {
        super(context);
        this.murl = url;
    }

    @Override
    public TreeMap<String, List<String>> loadInBackground() {
        TreeMap<String, List<String>>  listOfcities = QueryUtils.extractCityMap(murl);
        return listOfcities;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
