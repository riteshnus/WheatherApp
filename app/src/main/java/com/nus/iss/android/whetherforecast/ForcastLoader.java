package com.nus.iss.android.whetherforecast;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Ritesh on 4/22/2017.
 */

public class ForcastLoader extends AsyncTaskLoader<List<Weather>> {

    //private String murl="http://api.openweathermap.org/data/2.5/forecast?q=singapore&appid=95f374f5396a9e255a48c7017c46e675";
    private String murl;

    public ForcastLoader(Context context, String url) {
        super(context);
        this.murl = url;
    }

    @Override
    public List<Weather> loadInBackground() {
        List<Weather> weathers = QueryUtils.extractForcast(murl);
        return weathers;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
