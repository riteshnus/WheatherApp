package com.nus.iss.android.whetherforecast;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Ritesh on 4/22/2017.
 */

public class LocationFragment extends Fragment implements AdapterView.OnItemSelectedListener,LoaderManager.LoaderCallbacks<TreeMap<String,List<String>>>{

    public LocationFragment(){
    }

    TreeMap<String, List<String>> mapForAll = new TreeMap<>();
    private final String url= "https://raw.githubusercontent.com/David-Haim/CountriesToCitiesJSON/master/countriesToCities.json";
    private String locationWeatherUrl = "http://api.openweathermap.org/data/2.5/weather?appid=95f374f5396a9e255a48c7017c46e675&q=";
    Spinner country,city;
    private TextView mLocation;
    private TextView mdescription;
    private TextView mcondition;
    private TextView mtemp;
    private TextView mtemp_max_min;
    private TextView mpressure;
    private TextView mhumidity;
    private LinearLayout mLinearLayout;
    private FrameLayout mProgressBar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_selection,container,false);
        country = (Spinner)rootView.findViewById(R.id.country_spinner);
        city = (Spinner)rootView.findViewById(R.id.city_spinner);
        mtemp = (TextView) rootView.findViewById(R.id.tv_forcast_temp);
        mcondition = (TextView) rootView.findViewById(R.id.tv_forcast_condition);
        mdescription = (TextView) rootView.findViewById(R.id.tv_forcast_desc);
        mtemp_max_min = (TextView) rootView.findViewById(R.id.tv_forcast_max_min);
        mpressure = (TextView) rootView.findViewById(R.id.tv_forcast_pressure);
        mhumidity = (TextView) rootView.findViewById(R.id.tv_forcast_humidity);
        mLocation = (TextView) rootView.findViewById(R.id.tv_location);
        mLinearLayout = (LinearLayout) rootView.findViewById(R.id.city_layout);
        mProgressBar = (FrameLayout) rootView.findViewById(R.id.loading_indicator);
        getLoaderManager().initLoader(2, null, this);
        return rootView;
    }

    @Override
    public Loader<TreeMap<String, List<String>>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(url);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        return new LocationLoader(getActivity(),uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<TreeMap<String, List<String>>> loader, TreeMap<String, List<String>> data) {
        mapForAll = data;
        List<String> countryKey = new ArrayList<String>(mapForAll.keySet());
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, countryKey);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(aa);
        country.setOnItemSelectedListener(this);
    }

    @Override
    public void onLoaderReset(Loader<TreeMap<String, List<String>>> loader) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String sp1= String.valueOf(country.getSelectedItem());
        //Toast.makeText(getActivity(), sp1, Toast.LENGTH_SHORT).show();

        if(mapForAll.containsKey(sp1)) {
            List<String> list = mapForAll.get(sp1);
            Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
            list.add(0,"");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            city.setAdapter(dataAdapter);
        }
        //String sp2 = String.valueOf(city.getSelectedItem());
        //Toast.makeText(getActivity(), sp2, Toast.LENGTH_SHORT).show();setOnItemSelectedListener
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                String sp2 = String.valueOf(city.getSelectedItem());
                if(!sp2.isEmpty()){
                    //Toast.makeText(getActivity(), sp2, Toast.LENGTH_SHORT).show();
                    mProgressBar.setVisibility(View.VISIBLE);
                    LocationReportAsyncTask locationReportAsyncTask = new LocationReportAsyncTask();
                    //locationWeatherUrl = locationWeatherUrl+sp2;
                    locationReportAsyncTask.execute(locationWeatherUrl+sp2);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Toast.makeText(getActivity(), "Choose Countries :", Toast.LENGTH_SHORT).show();
    }

    private class LocationReportAsyncTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {
            if (params.length < 1 || params[0] == null) {
                return null;
            }
            Weather weathers = QueryUtils.extractWeather(params[0]);
            return weathers;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            if (weather != null) {
                //Toast.makeText(getActivity(), "Choose Countries :", Toast.LENGTH_SHORT).show();
                mProgressBar.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.VISIBLE);
                mdescription.setText(weather.getDescription());
                mtemp.setText(weather.getTemp());
                mhumidity.setText(weather.getHumidity());
                mpressure.setText(weather.getPressure());
                mtemp_max_min.setText(weather.getTempMinMax());
                mcondition.setText(weather.getCondition());
                mLocation.setText(weather.getLocation());
            }else{
                mProgressBar.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
