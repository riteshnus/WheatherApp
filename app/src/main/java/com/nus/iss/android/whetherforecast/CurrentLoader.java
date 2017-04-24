package com.nus.iss.android.whetherforecast;

import android.content.AsyncTaskLoader;
import android.content.Context;

/**
 * Created by Ritesh on 4/22/2017.
 */

public class CurrentLoader extends AsyncTaskLoader<Weather> {

    //private String murl= "http://api.openweathermap.org/data/2.5/weather?q=London,uk&appid=95f374f5396a9e255a48c7017c46e675";
    private String murl;

    public CurrentLoader(Context context, String url) {
        super(context);
        this.murl = url;
    }

    @Override
    public Weather loadInBackground() {
        Weather weathers = QueryUtils.extractWeather(murl);
        return weathers;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
